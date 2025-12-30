package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.APIException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.ResourceNotFoundException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Category;
import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.CategoryDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.CategoryResponse;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) throws ResourceNotFoundException {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails =  PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Category> allCategories = categoryRepository.findAll(pageDetails);
        List<Category> categories = allCategories.getContent();
        if(categories.isEmpty()){
            throw new APIException("No category created till now.");
        }
        List<CategoryDTO> categoryDto = categories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        CategoryResponse response = new CategoryResponse();
        response.setContent(categoryDto);
        response.setPageNumber(allCategories.getNumber());
        response.setPageSize(allCategories.getSize());
        response.setTotalElements(allCategories.getTotalElements());
        response.setTotalPages(allCategories.getTotalPages());
        response.setLastPage(allCategories.isLast());
        return response;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category category = modelMapper.map(categoryDto,Category.class);
        Category findCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(findCategory != null){
            throw new APIException("Category with the name "+category.getCategoryName()+ " already exists");
        }
        Category savedCategory =  categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category deleteCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",id));
        categoryRepository.delete(deleteCategory);

        return modelMapper.map(deleteCategory, CategoryDTO.class);

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, Long id) {

        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
        Category category =  modelMapper.map(categoryDto,Category.class);
        category.setCategoryId(id);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory,CategoryDTO.class);
    }

}
