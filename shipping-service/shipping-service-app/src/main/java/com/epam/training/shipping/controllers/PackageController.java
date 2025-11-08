package com.epam.training.shipping.controllers;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.epam.training.shipping.api.ShippingApi;
import com.epam.training.shipping.apimapper.PackageModelApiMapper;
import com.epam.training.shipping.apimodel.PackageModel;
import com.epam.training.shipping.data.entity.Package;
import com.epam.training.shipping.data.repository.PackageRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
public class PackageController implements ShippingApi {
    private final PackageRepository packageRepository;
    private final PackageModelApiMapper apiMapper;

    @Override
    @Secured("ROLE_USER")
    public ResponseEntity<PackageModel> getPackage(@PathVariable("id") Long id) {
        log.info("getMethod has been called, package id: {}", id);
        Package packageEntity = packageRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Package does not exist with id: " + id));

        PackageModel packageModel = apiMapper.map(packageEntity);
        return new ResponseEntity<>(packageModel, HttpStatus.OK);
    }

}
