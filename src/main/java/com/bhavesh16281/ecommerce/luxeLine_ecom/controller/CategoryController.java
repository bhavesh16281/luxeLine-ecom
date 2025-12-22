package com.bhavesh16281.ecommerce.luxeLine_ecom.controller;

import com.bhavesh16281.ecommerce.luxeLine_ecom.config.AppConstants;
import com.bhavesh16281.ecommerce.luxeLine_ecom.payload.CategoryDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.payload.CategoryResponse;
import com.bhavesh16281.ecommerce.luxeLine_ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder
    ){
        CategoryResponse allCategories = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(allCategories);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto){
        CategoryDTO createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){

        CategoryDTO deletedCategory = categoryService.deleteCategory(id);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDto, @PathVariable Long id){
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);
    }
}
