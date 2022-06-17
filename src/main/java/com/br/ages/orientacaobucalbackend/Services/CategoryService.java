package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.CategoryRepository;
// import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Category;
// import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    // ContentRepository contentRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Optional<Category> addNewCategory(Category category) throws IllegalArgumentException { 
        if ((category.getImageUrl() != null) && (category.getColor() != null) && (category.getName() != null)) {
            return Optional.of(categoryRepository.save(category));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Category> updateCategory(Long categoryId, Category newCategory) {
        Optional<Category> oldCategory = categoryRepository.findById(categoryId);
        if (oldCategory.isPresent()) {
            Category category = oldCategory.get();
            if (newCategory.getName() != null) {category.setName(newCategory.getName());}
            if (newCategory.getColor() != null) {category.setColor(newCategory.getColor());}
            if (newCategory.getImageUrl() != null) {category.setImageUrl(newCategory.getImageUrl());}
            if (newCategory.getContents() != null) {category.setContents(newCategory.getContents());}
            return Optional.of(categoryRepository.save(category));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Category> deleteCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.deleteCategoryContentByCategoryId(categoryId);
            categoryRepository.deleteById(categoryId);
        }
        return category;
    }

    public List<Long> deleteAllCategories() {
        List<Long> categoryIds = categoryRepository.getAllIds();
        categoryRepository.deleteAll();
        return categoryIds;
    }

    // public void save(Category category, Long content_id) {
    //     Optional<Content> content = contentRepository.findById(content_id);
    //     if (content.isPresent()) {
    //         category.getContents().add(content.get());
    //         System.out.println(category.getContents());
    //         categoryRepository.saveAndFlush(category);
    //     }
    // }
}
