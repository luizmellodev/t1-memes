package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Category;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContentService {

    @Autowired
    CategoryService categoryService;
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
        String objectName = content.getTitle()+".pdf";
        this.s3Service.upload((objectName), this.prefix, file);
        return this.URL + objectName;
    }

    public void updateCategories(Content content) {
        for (Category c: content.getCategories()) {
            Optional<Category> search = categoryService.findCategoryById(c.getId());
            if (search.isPresent()) {
                Category category = search.get();
                category.getContents().add(content);
                categoryService.updateCategory(category.getId(), category);
            }
        }
    }

    public Optional<Content> addNewContent(Content newContent) throws IllegalArgumentException {
        if ((newContent.getTitle() == null) || (newContent.getTextUrl() == null)) {
            return Optional.empty();
        }
        if (newContent.getPanfleto() != null) {
            String panfletoUrl = this.savePanfleto(newContent);
            newContent.setPanfletoUrl(panfletoUrl);
        }
        if (newContent.getVideoUrl() != null) {
            Pattern pattern = Pattern.compile(this.urlIsValid);
            Matcher match = pattern.matcher(newContent.getVideoUrl());
            if (match.matches()){
                newContent.setVideoUrl(newContent.getVideoUrl());
            } else {
                return Optional.empty();
            }
        }
        Content content = contentRepository.save(newContent);
        if (newContent.getCategories() != null) {
            updateCategories(content);
        }
        return Optional.of(content);
    }

    public Optional<Content> updateContent(Long contentId, Content newContent) {
        Optional<Content> oldContent = contentRepository.findById(contentId);
        if (oldContent.isPresent()) {
            Content content = oldContent.get();
            if (newContent.getTitle() != null) {content.setTitle(newContent.getTitle());}
            if (newContent.getTextUrl() != null) {content.setTextUrl(newContent.getTextUrl());}
            if (newContent.getPanfletoUrl() != null) {content.setPanfletoUrl(newContent.getPanfletoUrl());}
            if (newContent.getPanfleto() != null) {content.setPanfleto(newContent.getPanfleto());}
            if (newContent.getVideoUrl() != null) {content.setVideoUrl(newContent.getVideoUrl());}
            if (newContent.getCategories() != null) {
                contentRepository.deleteCategoryContentByContentId(contentId);
                content.setCategories(newContent.getCategories());
                updateCategories(content);
            }
            return Optional.of(contentRepository.save(content));
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

    // public Content getById(Long id) {
    //     Content content = this.findById(id).get();
    //     content.setCategories_ids(new ArrayList<>());
    //     for (Category category : content.getCategories()) {
    //         content.getCategories_ids().add(category.getId());
    //     }
    //     return content;
    // }

    // public void updateCategories(Content content) {
    //     for (int i = 1; i <= content.getCategories_ids().size(); i++) {
    //         Optional<Category> aux = categoryService.findCategoryById(content.getCategories_ids().get(i - 1));
    //         if (aux.isPresent()) {
    //             aux.get().getContents().add(content);
    //             categoryService.updateCategory(aux.get().getId(), aux.get());
    //         }
    //     }
    // }

}
