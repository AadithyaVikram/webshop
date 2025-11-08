package com.epam.training.order.apimapper;

import com.epam.training.order.apimodel.OrderAndPackageModel;
import com.epam.training.order.apimodel.OrderModel;
import com.epam.training.order.apimodel.PackageModel;
import com.epam.training.order.data.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderModelApiMapper {
    public OrderModel map(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.id(order.getId());
        orderModel.setDescription(order.getDescription());
        orderModel.setPrice(order.getPrice().doubleValue());
        return orderModel;
    }
    public OrderAndPackageModel map(OrderModel order, PackageModel packageModel) {
        OrderAndPackageModel model = new OrderAndPackageModel();
        model.setOrder(order);
        model.setPackage(packageModel);
        return model;
    }
}
