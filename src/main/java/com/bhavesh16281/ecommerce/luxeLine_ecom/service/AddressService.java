package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.AddressDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.User;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDto, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getUserAddresses(User user);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);
}
