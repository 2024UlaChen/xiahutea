package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.dto.ProductDTO;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private StoreDao storeDao;


    //找出全部的產品
    @Override
    public List<Product> getAllProducts() {

        return productDao.findAll();
    }

    //用分類id以及商品名稱查找
    @Override
    public List<Product> searchProducts(Integer categoryId, String productName) {
        return productDao.findByProductCategoryIdAndProductNameContaining(categoryId, productName);
    }

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productDao.findAll(pageable);
    }


    //獲取產品名字
    public List<Product> getProductsByProductName(String productName) {
        return productDao.findByProductNameContaining(productName);

    }

    //新增
    public boolean addProduct(ProductDTO productDTO) {
        // 创建 Product 实体对象
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setSize(productDTO.getSize());
        product.setProductStatus(productDTO.isProductStatus());
        product.setProductStoreId(productDTO.getProductStoreId());
        product.setProductCategoryId(productDTO.getProductCategoryId());
        product.setProductStoreId(1);
        product.setNormalIce(productDTO.isNormalIce());
        product.setLessIce(productDTO.isLessIce());
        product.setLightIce(productDTO.isLightIce());
        product.setIceFree(productDTO.isIceFree());
        product.setRoomTemperature(productDTO.isRoomTemperature());
        product.setHot(productDTO.isHot());
        product.setFullSugar(productDTO.isFullSugar());
        product.setLessSugar(productDTO.isLessSugar());
        product.setHalfSugar(productDTO.isHalfSugar());
        product.setQuarterSugar(productDTO.isQuarterSugar());
        product.setNoSugar(productDTO.isNoSugar());
        product.setPearl(productDTO.isPearl());
        product.setPudding(productDTO.isPudding());
        product.setCoconutJelly(productDTO.isCoconutJelly());
        product.setTaro(productDTO.isTaro());
        product.setHerbalJelly(productDTO.isHerbalJelly());




            productDao.save(product);


        // 保存商品到数据库

        return true;
    }

    public boolean updateProduct(ProductDTO productDTO) {
        try {
            // 查找现有产品
            Product existingProduct = productDao.findById(productDTO.getProductId()).orElse(null);

            if (existingProduct == null) {
                // 如果产品不存在，返回失败
                return false;
            }

            // 更新产品的属性
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setProductPrice(productDTO.getProductPrice());
            existingProduct.setSize(productDTO.getSize());
            existingProduct.setProductStatus(productDTO.isProductStatus());
            existingProduct.setProductStoreId(productDTO.getProductStoreId());
            existingProduct.setProductCategoryId(productDTO.getProductCategoryId());
            existingProduct.setNormalIce(productDTO.isNormalIce());
            existingProduct.setLessIce(productDTO.isLessIce());
            existingProduct.setLightIce(productDTO.isLightIce());
            existingProduct.setIceFree(productDTO.isIceFree());
            existingProduct.setRoomTemperature(productDTO.isRoomTemperature());
            existingProduct.setHot(productDTO.isHot());
            existingProduct.setFullSugar(productDTO.isFullSugar());
            existingProduct.setLessSugar(productDTO.isLessSugar());
            existingProduct.setHalfSugar(productDTO.isHalfSugar());
            existingProduct.setQuarterSugar(productDTO.isQuarterSugar());
            existingProduct.setNoSugar(productDTO.isNoSugar());
            existingProduct.setPearl(productDTO.isPearl());
            existingProduct.setPudding(productDTO.isPudding());
            existingProduct.setCoconutJelly(productDTO.isCoconutJelly());
            existingProduct.setTaro(productDTO.isTaro());
            existingProduct.setHerbalJelly(productDTO.isHerbalJelly());

            // 保存更新后的产品
            productDao.save(existingProduct);

            return true;
        } catch (Exception e) {
            // 捕捉并记录异常
            e.printStackTrace();
            return false;
        }
    }
    //刪除
    @Override
    public boolean deleteProduct(Integer productId) {
       if (productDao.existsById(productId)) {
            productDao.deleteById(productId);
            return true; // 表示删除成功
        } else {

           return false; // 表示找不到紀錄
       }

    }
}