package com.bhavesh16281.ecommerce.luxeLine_ecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private String message;
    private boolean status;
}
