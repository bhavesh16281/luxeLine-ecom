package com.bhavesh16281.ecommerce.luxeLine_ecom.controller;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.AddressDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Address;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.User;
import com.bhavesh16281.ecommerce.luxeLine_ecom.service.AddressService;
import com.bhavesh16281.ecommerce.luxeLine_ecom.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AddressService addressService;
    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid
                                                         @RequestBody AddressDTO addressDto) {

        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDto = addressService.createAddress(addressDto, user);
        return ResponseEntity.ok().body(savedAddressDto);
    }
}
