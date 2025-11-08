package com.epam.training.order.apimodel;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * A package
 */

@Schema(name = "PackageModel", description = "A package")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-19T17:50:50.966293+05:30[Asia/Calcutta]")
public class PackageModel {

  private Long id;

  private String status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate expectedShippingDate;

  public PackageModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * The identifier of the package.
   * @return id
  */
  
  @Schema(name = "id", description = "The identifier of the package.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PackageModel status(String status) {
    this.status = status;
    return this;
  }

  /**
   * The status of the package.
   * @return status
  */
  
  @Schema(name = "status", description = "The status of the package.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public PackageModel expectedShippingDate(LocalDate expectedShippingDate) {
    this.expectedShippingDate = expectedShippingDate;
    return this;
  }

  /**
   * Expected shipping date of the package.
   * @return expectedShippingDate
  */
  @Valid 
  @Schema(name = "expectedShippingDate", description = "Expected shipping date of the package.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expectedShippingDate")
  public LocalDate getExpectedShippingDate() {
    return expectedShippingDate;
  }

  public void setExpectedShippingDate(LocalDate expectedShippingDate) {
    this.expectedShippingDate = expectedShippingDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackageModel packageModel = (PackageModel) o;
    return Objects.equals(this.id, packageModel.id) &&
        Objects.equals(this.status, packageModel.status) &&
        Objects.equals(this.expectedShippingDate, packageModel.expectedShippingDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, expectedShippingDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PackageModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    expectedShippingDate: ").append(toIndentedString(expectedShippingDate)).append("\n");
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

