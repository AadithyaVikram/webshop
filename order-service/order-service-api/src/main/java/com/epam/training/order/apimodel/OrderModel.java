package com.epam.training.order.apimodel;

import java.net.URI;
import java.util.Objects;
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
 * An order
 */

@Schema(name = "OrderModel", description = "An order")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-19T17:50:50.966293+05:30[Asia/Calcutta]")
public class OrderModel {

  private Long id;

  private String description;

  private Double price;

  public OrderModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * The identifier of the order
   * @return id
  */
  
  @Schema(name = "id", description = "The identifier of the order", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrderModel description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the order
   * @return description
  */
  
  @Schema(name = "description", description = "The description of the order", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OrderModel price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * The price of the order
   * @return price
  */
  
  @Schema(name = "price", description = "The price of the order", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderModel orderModel = (OrderModel) o;
    return Objects.equals(this.id, orderModel.id) &&
        Objects.equals(this.description, orderModel.description) &&
        Objects.equals(this.price, orderModel.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

