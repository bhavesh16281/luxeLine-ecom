package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.AddressDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.User;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDto, User user);
}
