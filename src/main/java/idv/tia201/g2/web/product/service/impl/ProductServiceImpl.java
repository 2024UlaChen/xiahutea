package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //獲取產品名字
    public List<Product> getProductsByProductName(String productName) {
        return productDao.findByProductNameContaining(productName);

    }

    //新增
    @Override
    public Product addProduct(Integer storeId, Product product) {
        Store store = storeDao.findById(storeId).orElse(null);
        if (store != null) {
            // 商品名稱字數限制檢查
            if (product.getProductName() != null && product.getProductName().length() <= 30) {
                product.setProductStoreId(storeId);
                return productDao.save(product);
            } else {
                // 商品名稱超過限制，返回 null 或拋出異常
                return null;
            }
        } else {
            return null;
        }
    }

    ;

    @Override
    public Product editProduct(Integer storeId, Integer productId, Product updatedProduct) {
        // 根據 storeId 直接查詢店家
        Store store = storeDao.findById(storeId).orElse(null);
        if (store == null) {
            // 店家不存在，返回 null
            return null;
        }

        // 根據 productId 直接查詢商品
        Product existingProduct = productDao.findById(productId).orElse(null);
        if (existingProduct == null) {
            // 商品不存在，返回 null
            return null;
        }

        // 檢查商品名稱是否為空，防止無商品名稱送出表單
        if (updatedProduct.getProductName() == null || updatedProduct.getProductName().trim().isEmpty()) {
            // 商品名稱為空，返回 null
            return null;
        }

        // 更新商品資訊
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setProductPrice(updatedProduct.getProductPrice());
        existingProduct.setProductStatus(updatedProduct.isProductStatus());
        existingProduct.setProductPicture(updatedProduct.getProductPicture());
        existingProduct.setProductCategoryId(updatedProduct.getProductCategoryId());

        // 其他屬性也可以根據需要更新
        existingProduct.setNormalIce(updatedProduct.isNormalIce());
        existingProduct.setLessIce(updatedProduct.isLessIce());
        existingProduct.setIceFree(updatedProduct.isIceFree());
        existingProduct.setLightIce(updatedProduct.isLightIce());
        existingProduct.setRoomTemperature(updatedProduct.isRoomTemperature());
        existingProduct.setHot(updatedProduct.isHot());
        existingProduct.setFullSugar(updatedProduct.isFullSugar());
        existingProduct.setLessSugar(updatedProduct.isLessSugar());
        existingProduct.setHalfSugar(updatedProduct.isHalfSugar());
        existingProduct.setQuarterSugar(updatedProduct.isQuarterSugar());
        existingProduct.setNoSugar(updatedProduct.isNoSugar());
        existingProduct.setPearl(updatedProduct.isPearl());
        existingProduct.setPudding(updatedProduct.isPudding());
        existingProduct.setCoconutJelly(updatedProduct.isCoconutJelly());
        existingProduct.setTaro(updatedProduct.isTaro());
        existingProduct.setHerbalJelly(updatedProduct.isHerbalJelly());
        existingProduct.setSize(updatedProduct.getSize());

        // 保存更新後的商品
        return productDao.save(existingProduct);
    }

    //刪除
    @Override
    public boolean deleteProduct(Integer productId) {
        if (productDao.existsById(productId)) {
            productDao.deleteById(productId);
            return true; // 表示删除成功
        } else {
            //
            return false; // 表示找不到紀錄
        }

    }
}