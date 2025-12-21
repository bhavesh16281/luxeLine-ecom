package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.payload.CategoryDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.payload.CategoryResponse;


public interface CategoryService {

    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO CategoryDto);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(CategoryDTO CategoryDto, Long id);
}
