package com.homevision.exercise.model;

public class House {
  //TODO: Reflect all the model from API for further use cases.
  private Long id;
  private String address;
  private String photoURL;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getPhotoURL() {
    return photoURL;
  }
  public void setPhotoURL(String photoURL) {
    this.photoURL = photoURL;
  }
}
