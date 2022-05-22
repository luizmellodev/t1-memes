package com.br.ages.orientacaobucalbackend.Services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.ages.orientacaobucalbackend.Config.AmazonClient;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.CategoryRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Category;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    private final CategoryService categoryService;
    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(CategoryService categoryService,ContentRepository contentRepository) {
        this.categoryService = categoryService;
        this.contentRepository = contentRepository;
    }

    public List<Content> list() {
        return contentRepository.findAll();
    }

    public Optional<Content> findById(Long id) {
        return contentRepository.findById(id);
    }

    public Content getById(Long id){
        Content content = this.findById(id).get();
        content.setCategories_ids(new ArrayList<>());
        for (Category category: content.getCategories()) {
            content.getCategories_ids().add(category.getId());
        }
        return content;
    }
    public void deleteCategory(Long content_id){
        contentRepository.deleteCategoryContent(content_id);
    }
    public void deleteAll() {
        contentRepository.deleteAllCategoryContent();
        contentRepository.deleteAll();
    }

    public boolean deleteById(Long id) {
        Optional<Content> content = this.findById(id);
        deleteCategory(id);
        if (content.isPresent()) {
            contentRepository.delete(content.get());
            return true;
        } else {
            return false;
        }
    }

    public void save(Content content) {

        contentRepository.save(content);
        if (content.getCategories_ids().size() > 0){
            for (int i = 1; i <= content.getCategories_ids().size(); i++) {
                Optional<Category> aux = categoryService.findById(content.getCategories_ids().get(i-1));
                if(aux.isPresent()) {
                    aux.get().getContents().add(content);
                    categoryService.update(aux.get().getId(), aux.get());
                }
            }
        }
    }

    public boolean update(Long id, Content newContent) {
        Optional<Content> oldContent = this.findById(id);
        deleteCategory(id);
        if (oldContent.isPresent()) {
            Content content = oldContent.get();
            content.setTitle(newContent.getTitle());
            content.setTextUrl(newContent.getTextUrl());
            content.setPanfletoUrl(newContent.getPanfletoUrl());
            content.setVideoUrl(newContent.getVideoUrl());
            content.setCategories_ids(newContent.getCategories_ids());
            this.save(content);
            return true;
        } else {
            throw new NullPointerException("this content doesn't exist.");
        }
    }
}