package com.bhavesh16281.ecommerce.luxeLine_ecom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @Schema(description = "Category ID",example = "1")
    private Long categoryId;
    @Schema(description = "Name of the category",example = "Electronics")
    private String categoryName;
}
