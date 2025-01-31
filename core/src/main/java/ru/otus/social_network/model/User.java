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

import java.time.LocalDate;
import java.util.Objects;

/**
 * User
 */
@JsonPropertyOrder({
  User.JSON_PROPERTY_ID,
  User.JSON_PROPERTY_FIRST_NAME,
  User.JSON_PROPERTY_SECOND_NAME,
  User.JSON_PROPERTY_BIRTHDATE,
  User.JSON_PROPERTY_BIOGRAPHY,
  User.JSON_PROPERTY_CITY
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-01-29T19:14:42.241620900+03:00[Europe/Moscow]", comments = "Generator version: 7.11.0")
public class User {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nullable
  private String id;

  public static final String JSON_PROPERTY_FIRST_NAME = "first_name";
  @jakarta.annotation.Nullable
  private String firstName;

  public static final String JSON_PROPERTY_SECOND_NAME = "second_name";
  @jakarta.annotation.Nullable
  private String secondName;

  public static final String JSON_PROPERTY_BIRTHDATE = "birthdate";
  @jakarta.annotation.Nullable
  private LocalDate birthdate;

  public static final String JSON_PROPERTY_BIOGRAPHY = "biography";
  @jakarta.annotation.Nullable
  private String biography;

  public static final String JSON_PROPERTY_CITY = "city";
  @jakarta.annotation.Nullable
  private String city;

  public User() {
  }

  public User id(@jakarta.annotation.Nullable String id) {
    
    this.id = id;
    return this;
  }

  /**
   * Идентификатор пользователя
   * @return id
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@jakarta.annotation.Nullable String id) {
    this.id = id;
  }

  public User firstName(@jakarta.annotation.Nullable String firstName) {
    
    this.firstName = firstName;
    return this;
  }

  /**
   * Имя
   * @return firstName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FIRST_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFirstName() {
    return firstName;
  }


  @JsonProperty(JSON_PROPERTY_FIRST_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFirstName(@jakarta.annotation.Nullable String firstName) {
    this.firstName = firstName;
  }

  public User secondName(@jakarta.annotation.Nullable String secondName) {
    
    this.secondName = secondName;
    return this;
  }

  /**
   * Фамилия
   * @return secondName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SECOND_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSecondName() {
    return secondName;
  }


  @JsonProperty(JSON_PROPERTY_SECOND_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSecondName(@jakarta.annotation.Nullable String secondName) {
    this.secondName = secondName;
  }

  public User birthdate(@jakarta.annotation.Nullable LocalDate birthdate) {
    
    this.birthdate = birthdate;
    return this;
  }

  /**
   * Дата рождения
   * @return birthdate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BIRTHDATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getBirthdate() {
    return birthdate;
  }


  @JsonProperty(JSON_PROPERTY_BIRTHDATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBirthdate(@jakarta.annotation.Nullable LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public User biography(@jakarta.annotation.Nullable String biography) {
    
    this.biography = biography;
    return this;
  }

  /**
   * Интересы
   * @return biography
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BIOGRAPHY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getBiography() {
    return biography;
  }


  @JsonProperty(JSON_PROPERTY_BIOGRAPHY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBiography(@jakarta.annotation.Nullable String biography) {
    this.biography = biography;
  }

  public User city(@jakarta.annotation.Nullable String city) {
    
    this.city = city;
    return this;
  }

  /**
   * Город
   * @return city
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCity() {
    return city;
  }


  @JsonProperty(JSON_PROPERTY_CITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCity(@jakarta.annotation.Nullable String city) {
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.secondName, user.secondName) &&
        Objects.equals(this.birthdate, user.birthdate) &&
        Objects.equals(this.biography, user.biography) &&
        Objects.equals(this.city, user.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, secondName, birthdate, biography, city);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    secondName: ").append(toIndentedString(secondName)).append("\n");
    sb.append("    birthdate: ").append(toIndentedString(birthdate)).append("\n");
    sb.append("    biography: ").append(toIndentedString(biography)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
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

