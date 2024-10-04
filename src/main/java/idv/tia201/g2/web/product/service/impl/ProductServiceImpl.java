package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;


    //找出全部的產品
    @Override
    public List<Product> getAllProducts() {

        return productDao.findAll();
    }

    //用分類id以及商品名稱查找


    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productDao.findAll(pageable);
    }

    @Override
    public List<Product> searchProducts(Integer productCategoryId, String productName) {
        return productDao.findByProductCategoryIdAndProductNameContaining(productCategoryId, productName);
    }

    @Override
    public List<ProductCategoryDTO> getProductsByCategory() {
        return null;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Integer ProductCategoryId) {
        List<Product> products = productDao.findByProductCategoryId(ProductCategoryId);
        // 轉換實體列表到 DTO 列表
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());

    }



    //獲取產品名字
    public List<Product> getProductsByProductName(String productName) {
        return productDao.findByProductNameContaining(productName);

    }


    //新增
    public boolean addProduct(ProductDTO productDTO,Integer userTypeId,Integer userId) throws IOException {
        // 创建 Product 实体对象
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
       product.setProductPrice(productDTO.getProductPrice());
        product.setSize(productDTO.getSize());
        product.setProductStatus(productDTO.isProductStatus());
        product.setProductCategoryId(productDTO.getProductCategoryId());
        product.setProductStoreId(userId);
        // 设置图片字节
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
        product.setProductPicture(productDTO.getProductPicture().getBytes());




        productDao.save(product);


        // 保存商品到数据库

        return true;
    }



    public void updateProductImage(Integer productId, byte[] imageBytes) {
        Product product = productDao.findByProductId(productId);
        product.setProductPicture(imageBytes);
        productDao.save(product);
    }

    @Override
    public Page<Product> getProductByStoreId(Integer productStoreId, Pageable pageable) {
        return productDao.findByProductStoreId(productStoreId, pageable);
    }

    @Override
    public List<Product> searchProducts(Integer storeId, Integer productCategoryId, String productName) {
        return productDao.findByProductStoreIdAndProductCategoryIdAndProductNameContaining(storeId,productCategoryId,productName);    }

    @Override
    public List<Product> getProductsByCategoryAndStore(Integer storeId, Integer productCategoryId) {
        return productDao.findByProductStoreIdAndProductCategoryId(storeId, productCategoryId);
    }

    @Override
    public Product saveImage(MultipartFile file) throws IOException {
        Product product=new Product();
        product.setProductPicture(file.getBytes());

        return product; // 返回 product 以供后续处理;
    }

    @Override
    public List<Product> findProductsByStoreId(Integer storeId) {
        return productDao.findByProductStoreId(storeId);
    }

    @Override
    public byte[] getProductImage(Integer productId) {
        Product product = productDao.findByProductId(productId);
        return (product != null) ? product.getProductPicture() : null; // 返回圖片數據

    }


    public boolean updateProduct(Integer productId, ProductDTO productDTO,Integer userTypeId, Integer userId) {
        try {
            // 查找现有产品
            Product existingProduct = productDao.findByProductId(productId);
            if (userTypeId == 1 && !existingProduct.getProductStoreId().equals(userId)) {
                throw new IllegalAccessException("您無權操作該產品，因為它不屬於您的商店。");
            };

            Product existingProductName = productDao.findByProductName(productDTO.getProductName());

            if (existingProduct == null) {
                // 如果产品不存在，返回失败
                return false;
            }

            if (existingProductName !=null) {
                // 如果产品不存在，返回失败
                throw new IllegalArgumentException("產品名稱已經存在，請使用不同的名稱");

            };

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

    @Override
    public ProductDTO getProductById(Integer productId) {
        Product product = productDao.findByProductId(productId);
        if (product != null) {
            return convertToProductDTO(product); // 假设有一个转换方法
        }
        return null;
    }


    private ProductDTO convertToProductDTO(Product product) {
        // 转换 Product 实体为 ProductDTO
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductStatus(product.isProductStatus());
        dto.setProductStoreId(product.getProductStoreId());
        dto.setProductCategoryId(product.getProductCategoryId());
        dto.setSize(product.getSize());
        dto.setNormalIce(product.isNormalIce());
        dto.setLessIce(product.isLessIce());
        dto.setIceFree(product.isIceFree());
        dto.setLightIce(product.isLightIce());
        dto.setRoomTemperature(product.isRoomTemperature());
        dto.setHot(product.isHot());
        dto.setFullSugar(product.isFullSugar());
        dto.setLessSugar(product.isLessSugar());
        dto.setHalfSugar(product.isHalfSugar());
        dto.setQuarterSugar(product.isQuarterSugar());
        dto.setNoSugar(product.isNoSugar());
        dto.setPearl(product.isPearl());
        dto.setPudding(product.isPudding());
        dto.setCoconutJelly(product.isCoconutJelly());
        dto.setTaro(product.isTaro());
        dto.setHerbalJelly(product.isHerbalJelly());
        return dto;
    }


}