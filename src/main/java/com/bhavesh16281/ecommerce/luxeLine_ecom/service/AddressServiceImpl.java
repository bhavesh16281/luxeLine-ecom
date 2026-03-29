package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.AddressDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.ResourceNotFoundException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Address;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.User;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.AddressRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDto, User user) {

        Address address = modelMapper.map(addressDto, Address.class);

        List<Address> addresseList = user.getAddresses();
        addresseList.add(address);
        user.setAddresses(addresseList);

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {

        List<Address>  addressList = addressRepository.findAll();
        return addressList.stream().map(address->modelMapper.map(address,AddressDTO.class)).
                toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {

        Address address =  addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","id",addressId));

        return modelMapper.map(address,AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {

        List<Address>  addressList = user.getAddresses();
        return addressList.stream().map(address->modelMapper.map(address,AddressDTO.class)).
                toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","id",addressId));

        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setPincode(addressDTO.getPincode());
        address.setStreet(addressDTO.getStreet());
        address.setState(addressDTO.getState());
        address.setBuildingName(addressDTO.getBuildingName());

        Address  updatedAddress = addressRepository.save(address);
        User user = address.getUser();
        user.getAddresses().removeIf(a->a.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress,AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","id",addressId));

        User user = address.getUser();
        user.getAddresses().removeIf(a->a.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(address);

        return "Address deleted successfully with addressId: "+addressId;
    }
}
