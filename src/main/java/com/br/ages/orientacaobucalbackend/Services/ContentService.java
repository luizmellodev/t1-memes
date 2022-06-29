package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Category;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContentService {

    @Autowired
    CategoryService categoryService;
    @Autowired
    RecommendedSourceService recommendedSourceService;
    @Autowired
    ContentRepository contentRepository;
    private final String URL = "https://saude-velho.s3.us-east-2.amazonaws.com/panfleto/";
    private final String URI = "saude-velho";
    private final String prefix = "panfleto";
    private final String urlIsValid = "^(https?://)?((www.)?youtube.com|youtu.be)/.+$";
    private final S3Service s3Service = new S3Service(this.URI);

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public Optional<Content> getContentById(Long contentId) {
        return contentRepository.findById(contentId);
    }

    public String savePanfleto(Content content) {
        byte[] file = Base64.getDecoder().decode(content.getPanfleto());
        String objectName = content.getTitle() + ".pdf";
        this.s3Service.upload((objectName), this.prefix, file);
        return this.URL + objectName;
    }

    public void updateCategories(Content content) {
        for (Category c : content.getCategories()) {
            Optional<Category> search = categoryService.findCategoryById(c.getId());
            if (search.isPresent()) {
                Category category = search.get();
                category.getContents().add(content);
                categoryService.updateCategory(category.getId(), category);
            }
        }
    }

    public List<Category> generateCategories(ArrayList<Integer> categories_ids) {
        List<Category> categories = new ArrayList<>();
        for (Integer id : categories_ids) {
            Optional<Category> search = categoryService.findCategoryById(id.longValue());
            if (search.isPresent()) {
                Category category = search.get();
                categories.add(category);
            }
        }
        return categories;
    }

    public void createRecommendedSources(ArrayList<Map<String, String>> recommendedSources, Content content) {
        for (Map<String, String> source : recommendedSources) {
            RecommendedSource recommendedSourceObject = new RecommendedSource();
            recommendedSourceObject.setDescription(source.get("description").toString());
            recommendedSourceObject.setLink(source.get("link").toString());
            recommendedSourceObject.setTitle(source.get("title").toString());
            recommendedSourceService.addNewRecommendedSource(recommendedSourceObject, content.getId());
        }
    }

    public Optional<Content> addNewContent(Map<String, Object> newContent) throws IllegalArgumentException {
        Content content = new Content();

        if ((newContent.get("title") == null) || (newContent.get("textUrl") == null)) {
            return Optional.empty();
        } else {
            content.setTitle((String) newContent.get("title"));
            content.setTextUrl((String) newContent.get("textUrl"));
        }

        if (newContent.get("videoUrl") != null) {
            Pattern pattern = Pattern.compile(this.urlIsValid);
            Matcher match = pattern.matcher((CharSequence) newContent.get("videoUrl"));
            if (match.matches()) {
                content.setVideoUrl(newContent.get("videoUrl").toString());
            } else {
                return Optional.empty();
            }
        }

        if (newContent.get("categories_ids") != null) {
            ArrayList<Integer> categories_ids = (ArrayList<Integer>) newContent.get("categories_ids");
            content.setCategories(generateCategories(categories_ids));
        }

        Content response = contentRepository.save(content);
        this.updateCategories(response);

        if (newContent.get("recommendedSource") != null) {
            ArrayList<Map<String, String>> recommendedSource = (ArrayList<Map<String, String>>) newContent
                    .get("recommendedSource");
            createRecommendedSources(recommendedSource, response);
        }

        if (newContent.get("panfleto") != null) {
            response.setPanfleto(newContent.get("panfleto").toString());
            String panfletoUrl = this.savePanfleto(response);
            response.setPanfletoUrl(panfletoUrl);
            response = contentRepository.save(response);
        }

        return Optional.of(response);
    }

    public Optional<Content> updateContent(Long contentId, Map<String, Object> newContent) {
        Optional<Content> oldContent = contentRepository.findById(contentId);
        if (oldContent.isPresent()) {
            Content content = oldContent.get();
            if (newContent.get("title") != null) {
                content.setTitle(newContent.get("title").toString());
            }
            if (newContent.get("textUrl") != null) {
                content.setTextUrl(newContent.get("textUrl").toString());
            }
            if (newContent.get("panfleto") != null) {
                content.setPanfleto(newContent.get("panfleto").toString());
                String panfletoUrl = this.savePanfleto(content);
                content.setPanfletoUrl(panfletoUrl);
                content = contentRepository.save(content);
                content.setPanfleto(newContent.get("panfleto").toString());
            }
            if (newContent.get("videoUrl") != null) {
                Pattern pattern = Pattern.compile(this.urlIsValid);
                Matcher match = pattern.matcher((CharSequence) newContent.get("videoUrl"));
                if (match.matches()) {
                    content.setVideoUrl(newContent.get("videoUrl").toString());
                } else {
                    return Optional.empty();
                }
            }
            if (newContent.get("categories_ids") != null) {
                contentRepository.deleteCategoryContentByContentId(contentId);
                ArrayList<Integer> categories_ids = (ArrayList<Integer>) newContent.get("categories_ids");
                content.setCategories(generateCategories(categories_ids));
            }
            if (newContent.get("recommendedSource") != null) {
                contentRepository.deleteAllContentRecommendedSource(contentId);
                ArrayList<Map<String, String>> recommendedSource = (ArrayList<Map<String, String>>) newContent
                        .get("recommendedSource");
                createRecommendedSources(recommendedSource, content);
            }
            Content response = contentRepository.save(content);
            this.updateCategories(response);
            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Content> deleteById(Long contentId) {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isPresent()) {
            contentRepository.deleteCategoryContentByContentId(contentId);
            contentRepository.deleteAllContentRecommendedSource(contentId);
            contentRepository.deleteContent(contentId);
        }
        return content;
    }

    public List<Long> deleteAll() {
        List<Long> contentIds = contentRepository.getAllIds();
        contentRepository.deleteAllCategoryContent();
        contentRepository.deleteAll();
        return contentIds;
    }
}
