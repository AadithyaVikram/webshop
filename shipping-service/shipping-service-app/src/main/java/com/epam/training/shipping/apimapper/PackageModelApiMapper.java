package com.epam.training.shipping.apimapper;

import org.springframework.stereotype.Component;

import com.epam.training.shipping.apimodel.PackageModel;
import com.epam.training.shipping.data.entity.Package;

@Component
public class PackageModelApiMapper {
    public PackageModel map(Package packageToMap) {
        PackageModel packageModel = new PackageModel();
        packageModel.id(packageToMap.getId());
        packageModel.setStatus(packageToMap.getStatus());
        packageModel.setExpectedShippingDate(packageToMap.getExpectedShippingDate());
        return packageModel;
    }
}
