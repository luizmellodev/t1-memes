package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.CategoryRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Category;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {


    private final CategoryRepository  categoryRepository;
    private final ContentRepository contentRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,ContentRepository contentRepository) {
        this.categoryRepository = categoryRepository;
        this.contentRepository = contentRepository;
    }


    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public boolean deleteById(Long id) {
        Optional<Category> category = this.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return true;
        } else {
            return false;
        }
    }

    public void save(Category category, Long content_id) {
        Optional<Content> content = contentRepository.findById(content_id);
        if(content.isPresent()) {
            category.getContents().add(content.get());
            System.out.println(category.getContents());
            categoryRepository.saveAndFlush(category);
        }
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public boolean update(Long id, Category newCategory) {
        Optional<Category> oldCategory= this.findById(id);
        if (oldCategory.isPresent()) {
            Category category = oldCategory.get();
            category.setName(newCategory.getName());
            category.setColor(newCategory.getColor());
            category.setImageUrl(newCategory.getImageUrl());
            category.setContents(newCategory.getContents());

            this.save(category);
            return true;
        } else {
            throw new NullPointerException("this category doesn't exist.");
        }
    }
}