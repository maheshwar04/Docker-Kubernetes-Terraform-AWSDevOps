package com.cicdaws.product;

public class Product {
  private String name;

  // Constructors
  public Product() {
  }

  public Product(String name) {
    this.name = name;
  }

  // Getter & Setter
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
