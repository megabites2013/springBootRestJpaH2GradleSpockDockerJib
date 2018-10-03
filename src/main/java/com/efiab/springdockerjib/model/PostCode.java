package com.efiab.springdockerjib.model;

import com.opencsv.bean.CsvBindByName;
import io.vertx.core.json.JsonObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PostCode {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @CsvBindByName
  @Column(name = "postcode")
  private String postcode;

  @CsvBindByName
  @Column(name = "latitude")
  private double latitude;

  @CsvBindByName
  @Column(name = "longitude")
  private double longitude;

  public PostCode() {}

  public PostCode(Integer id, String postcode, double latitude, double longitude) {
    this.id = id;
    this.postcode = postcode;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * To json obj json object.
   *
   * @return the json object
   */
  public JsonObject toJsonObj() {
    return new JsonObject()
        .put("postcode", postcode)
        .put("latitude", latitude)
        .put("longitude", longitude)
        .put("id", id);
  }

  @Override
  public String toString() {
    return "" + id + ", " + postcode + ", " + latitude + ", " + longitude;
  }
}
