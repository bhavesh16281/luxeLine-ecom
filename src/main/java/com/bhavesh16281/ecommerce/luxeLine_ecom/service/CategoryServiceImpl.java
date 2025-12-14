package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.APIException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.ResourceNotFoundException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Category;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("No category created till now.");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with the name "+category.getCategoryName()+ " already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) {
        Category deleteCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",id));
        categoryRepository.delete(deleteCategory);
        return "Category with id " +id+" removed successfully";

    }

    @Override
    public Category updateCategory(Category category, Long id) {

        Category saveCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",id));
        category.setCategoryId(id);
        saveCategory = categoryRepository.save(category);
        return saveCategory;
    }

}
