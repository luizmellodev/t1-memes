package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Category;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    private final CategoryService categoryService;
    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(CategoryService categoryService, ContentRepository contentRepository) {
        this.categoryService = categoryService;
        this.contentRepository = contentRepository;
    }

    public List<Content> list() {
        return contentRepository.findAll();
    }

    public Optional<Content> findById(Long id) {
        return contentRepository.findById(id);
    }

    public Content getById(Long id) {
        Content content = this.findById(id).get();
        content.setCategories_ids(new ArrayList<>());
        for (Category category : content.getCategories()) {
            content.getCategories_ids().add(category.getId());
        }
        return content;
    }

    public void deleteCategory(Long content_id) {
        contentRepository.deleteCategoryContent(content_id);
    }

    public void deleteAll() {
        contentRepository.deleteAllCategoryContent();
        contentRepository.deleteAll();
    }

    public void deleteContent(Long content_id) {
        contentRepository.deleteAllContentRecommendedSource(content_id);
        contentRepository.deleteContent(content_id);
    }

    public Content deleteById(Long id) throws IOException {
        Optional<Content> content = this.findById(id);
        if (content.isPresent()) {
            Content contentToBeDeleted = content.get();
            deleteCategory(id);
            deleteContent(id);
            return contentToBeDeleted;
        } else {
            throw new NullPointerException("this content doesn't exist.");
        }
    }

    public void save(Content content) throws IOException {
        if (content.getPanfleto() != null) {
            S3Service s3 = new S3Service("saude-velho");
            byte[] file = Base64.getDecoder().decode(content.getPanfleto());
            s3.upload(content.getTitle() + ".pdf", "panfleto", file);
            content.setPanfletoUrl(
                    "https://saude-velho.s3.us-east-2.amazonaws.com/panfleto/" + content.getTitle() + ".pdf");
        }
        contentRepository.save(content);

        if (content.getCategories_ids().size() > 0) {
            for (int i = 1; i <= content.getCategories_ids().size(); i++) {
                Optional<Category> aux = categoryService.findById(content.getCategories_ids().get(i - 1));
                if (aux.isPresent()) {
                    aux.get().getContents().add(content);
                    categoryService.update(aux.get().getId(), aux.get());
                }
            }
        }
    }

    public Content update(Long id, Content newContent) throws IOException {
        Optional<Content> oldContent = this.findById(id);
        deleteCategory(id);
        if (oldContent.isPresent()) {
            Content content = oldContent.get();
            content.setTitle(newContent.getTitle());
            content.setTextUrl(newContent.getTextUrl());
            content.setPanfletoUrl(newContent.getPanfletoUrl());
            content.setPanfleto(newContent.getPanfleto());
            content.setVideoUrl(newContent.getVideoUrl());
            content.setCategories_ids(newContent.getCategories_ids());
            this.save(content);
            return content;
        } else {
            throw new NullPointerException("this content doesn't exist.");
        }
    }
}
