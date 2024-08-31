package idv.tia201.g2.web.product.controller;


import idv.tia201.g2.web.product.service.ProductCategoryService;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;

@RestController
public class ProductCategoryController {

   @Autowired
   private ProductCategoryService productCategoryService;
    //處理：允許添加、刪除或更新商品分類，以及顯示某一分類下的所有商品。





}
