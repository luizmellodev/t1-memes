package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Common.CategoryBadRequest;
import com.br.ages.orientacaobucalbackend.Common.EmptyJson;
import com.br.ages.orientacaobucalbackend.Entity.Category;
import com.br.ages.orientacaobucalbackend.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> response = categoryService.getAllCategories();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> response = categoryService.findCategoryById(categoryId);
        if (response.isPresent()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewCategory(@RequestBody Category newCategory) {
        try {
            Optional<Category> category = categoryService.addNewCategory(newCategory);
            if (category.isPresent()) {
                return new ResponseEntity<>(category.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CategoryBadRequest(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category newCategory) {
        Optional<Category> updatedCategory = categoryService.updateCategory(categoryId, newCategory);
        if (updatedCategory.isPresent()) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long categoryId) {
        Optional <Category> deletedCategory = categoryService.deleteCategoryById(categoryId);
        if (deletedCategory.isPresent()) {
            return new ResponseEntity<>(deletedCategory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllCategories() {
        List<Long> categoryIds = categoryService.deleteAllCategories();
        return new ResponseEntity<>(categoryIds, HttpStatus.OK);
    }
}
