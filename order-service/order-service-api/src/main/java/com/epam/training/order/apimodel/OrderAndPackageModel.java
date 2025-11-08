package com.epam.training.order.apimodel;

import java.net.URI;
import java.util.Objects;
import com.epam.training.order.apimodel.OrderModel;
import com.epam.training.order.apimodel.PackageModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * the order and the model objects together
 */

@Schema(name = "OrderAndPackageModel", description = "the order and the model objects together")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-19T17:50:50.966293+05:30[Asia/Calcutta]")
public class OrderAndPackageModel {

  private OrderModel order;

  private PackageModel _package;

  public OrderAndPackageModel order(OrderModel order) {
    this.order = order;
    return this;
  }

  /**
   * Get order
   * @return order
  */
  @Valid 
  @Schema(name = "order", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("order")
  public OrderModel getOrder() {
    return order;
  }

  public void setOrder(OrderModel order) {
    this.order = order;
  }

  public OrderAndPackageModel _package(PackageModel _package) {
    this._package = _package;
    return this;
  }

  /**
   * Get _package
   * @return _package
  */
  @Valid 
  @Schema(name = "package", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("package")
  public PackageModel getPackage() {
    return _package;
  }

  public void setPackage(PackageModel _package) {
    this._package = _package;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderAndPackageModel orderAndPackageModel = (OrderAndPackageModel) o;
    return Objects.equals(this.order, orderAndPackageModel.order) &&
        Objects.equals(this._package, orderAndPackageModel._package);
  }

  @Override
  public int hashCode() {
    return Objects.hash(order, _package);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderAndPackageModel {\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    _package: ").append(toIndentedString(_package)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

