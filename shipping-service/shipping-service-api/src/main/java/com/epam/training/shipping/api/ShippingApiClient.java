package com.epam.training.shipping.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("shipping-service")
public interface ShippingApiClient extends ShippingApi {
}