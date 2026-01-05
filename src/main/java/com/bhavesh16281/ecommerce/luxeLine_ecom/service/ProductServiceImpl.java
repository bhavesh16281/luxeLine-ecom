package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.ProductDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.ProductResponse;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.APIException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.ResourceNotFoundException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Category;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Product;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.CategoryRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        boolean isProductExists = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDto.getProductName())) {
                isProductExists = false;
                break;
            }
        }
        if(isProductExists){
            Product product = modelMapper.map(productDto, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice()
                    - ((product.getDiscount()*0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product addedProduct = productRepository.save(product);

            return modelMapper.map(addedProduct, ProductDTO.class);
        }else{
            throw new APIException("Product already exists!!");
        }

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails =  PageRequest.of(pageNumber-1, pageSize, sortByOrder);
        Page<Product> pageProducts = productRepository.findAll(pageDetails);
        List<Product> products = pageProducts.getContent();
        List<ProductDTO> productDTOs = products.stream()
                .map(product->modelMapper.map(product,ProductDTO.class))
                .toList();
        if(products.isEmpty()){
            throw new APIException("Products does not exist!!");
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setIsLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails =  PageRequest.of(pageNumber-1, pageSize, sortByOrder);
        Page<Product> pageProducts = productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);
        List<Product> products = pageProducts.getContent();
        if(products.isEmpty()){
            throw new APIException(category.getCategoryName()+" category does not have any products");
        }
        List<ProductDTO> productDTOs = products.stream()
                .map(product->modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setIsLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails =  PageRequest.of(pageNumber-1, pageSize, sortByOrder);
        Page<Product> pageProducts = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%",pageDetails);
        List<Product> products = pageProducts.getContent();
//        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");
        List<ProductDTO> productDTOs = products.stream()
                .map(product->modelMapper.map(product,ProductDTO.class))
                .toList();
        if(products.isEmpty()){
            throw new APIException("Product not found with keyword: "+keyword);
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setIsLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDto, Long productId) {

        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        Product product = modelMapper.map(productDto, Product.class);
        foundProduct.setProductName(product.getProductName());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setQuantity(product.getQuantity());
        foundProduct.setDiscount(product.getDiscount());
        foundProduct.setPrice(product.getPrice());
        double specialPrice = product.getPrice()
                - ((product.getDiscount()*0.01) * product.getPrice());
        foundProduct.setSpecialPrice(specialPrice);
        Product updatedProduct = productRepository.save(foundProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductimage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
//        String path = "images/";
        String fileName = fileService.uploadImage(path,image);
        productFromDb.setImage(fileName);
        Product updatedImage =  productRepository.save(productFromDb);
        return modelMapper.map(updatedImage, ProductDTO.class);
    }
}
