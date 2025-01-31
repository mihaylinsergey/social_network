/*
 * OTUS Highload Architect
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.2.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ru.otus.social_network.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * UserRegisterPost200Response
 */
@JsonPropertyOrder({
  UserRegisterPost200Response.JSON_PROPERTY_USER_ID
})
@JsonTypeName("_user_register_post_200_response")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-01-29T19:14:42.241620900+03:00[Europe/Moscow]", comments = "Generator version: 7.11.0")
public class UserRegisterPost200Response {
  public static final String JSON_PROPERTY_USER_ID = "user_id";
  @jakarta.annotation.Nullable
  private String userId;

  public UserRegisterPost200Response() {
  }

  public UserRegisterPost200Response userId(@jakarta.annotation.Nullable String userId) {
    
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserId() {
    return userId;
  }


  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserId(@jakarta.annotation.Nullable String userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRegisterPost200Response userRegisterPost200Response = (UserRegisterPost200Response) o;
    return Objects.equals(this.userId, userRegisterPost200Response.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserRegisterPost200Response {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

