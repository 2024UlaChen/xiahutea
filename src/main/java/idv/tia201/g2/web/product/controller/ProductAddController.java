package idv.tia201.g2.web.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/*
 * 商品資料相關 Controller
 * 商品資料表單新增
 */
@RestController
@RequestMapping("product")
public class ProductAddController {

    @Autowired
    private ProductService productService;


//    @PostMapping("addUpdateProduct")
//    public boolean addUpdateProduct(@RequestBody Product product) {
////        boolean flagAddUpdateProduct = false;
////        if (product.getProductId() != null){
////            flagAddUpdateProduct = productService.addUpdateProduct(product);
////        }
////        // 返 flagAddUpdateProduct 回前端
////        return flagAddUpdateProduct;
////    }
//
//
//    }

/**
 * 查詢 商品資料
 * @param product
 * @return
 */
//@PostMapping("/product/queryProduct")
//public String queryProduct(@RequestBody Product product) {
//
//
//
//
//    List<Product> productsList = new ArrayList<>();
//    if (product.getId() != null){
//      productService.insertAndUpdateProduct(product);
//    }
//    // 在这里处理接收到的产品数据
//    System.out.println("Received product: " + product.getProductName() + ", Price: " + product.getProductPrice());
//
//    String productListJSON;
//    ObjectMapper objectMapper = new ObjectMapper();
//    try {
//        //
//        productListJSON = objectMapper.writeValueAsString(productsList);
//    } catch (JsonProcessingException e) {
//        e.printStackTrace();
//        return null;
//    }
//    // 返 productListJSON 回前端
//    return productListJSON;
//}
//
}