package com.cicdaws.product;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final List<Product> products = new ArrayList<>();

  @GetMapping
  public List<Product> getAllProducts() {
    return products;
  }

  @GetMapping("/Message")
  public String getMessage() {
    return "Hello Hii Maheshwar from aws ecr,ecs, code build , code pipeline using github";
  }

  @PostMapping
  public String addProduct(@RequestBody Product product) {
    products.add(product);
    return "Product added: " + product.getName();
  }

  @DeleteMapping("/{name}")
  public String deleteProduct(@PathVariable String name) {
    boolean removed = products.removeIf(p -> p.getName().equalsIgnoreCase(name));
    return removed ? "Product removed: " + name : "Product not found: " + name;
  }
}
