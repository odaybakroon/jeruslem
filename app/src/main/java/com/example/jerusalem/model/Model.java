package com.example.jerusalem.model;

import java.io.Serializable;

public class Model implements Serializable {

   private String imageview;
   private String title;


  public Model() {
  }

  public Model(String imageview, String title) {
    this.imageview = imageview;
    this.title = title;
  }

  public String getImageview() {
    return imageview;
  }

  public void setImageview(String imageview) {
    this.imageview = imageview;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
