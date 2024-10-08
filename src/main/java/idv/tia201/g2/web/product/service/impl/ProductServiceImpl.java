package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.advertise.dao.AdsDao;
import idv.tia201.g2.web.advertise.vo.Advertise;
import idv.tia201.g2.web.product.dao.ProductCategoryRepository;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.dto.ProductDTO;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

   @Autowired
   private AdsDao adsDao;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;



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


    /**
     *  新增 Product
     * @param productDTO
     * @param userTypeId
     * @param userId
     * @param blobImg
     * @return boolean
     * @throws IOException
     */
    public boolean addProduct(ProductDTO productDTO, Integer userTypeId, Integer userId, byte[] blobImg) throws IOException {
        try {


            if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
                return false;
            }

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
            product.setProductPicture(blobImg);// 圖片資料

            productDao.save(product);// 保存商品到数据库
            return true;
        } catch (Exception exp) {
            exp.getStackTrace();
            System.out.println(exp.getMessage());
        };
        return false;
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
        //product.setProductPicture(file.getBytes());

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

    @Override
    public byte[] getFirstImageByUserId(Long adsTotalUserid) {
        List<Advertise> ads=adsDao.findByAdsTotalUserid(adsTotalUserid);
        // 檢查是否找到廣告
        if (ads != null && !ads.isEmpty()) {
            // 返回第一張圖片的 URL
            return ads.get(0).getImgUrl(); // 假設 imageUrl 是存儲圖片的欄位
        }
        return null; // 如果沒有廣告，則返回 null
    }


    public boolean updateProduct(Integer productId, ProductDTO productDTO,Integer userTypeId, Integer userId, byte[] blobImg) {
        try {
            // 查找现有产品
            Product product = productDao.findByProductId(productId);
            if (userTypeId == 1 && !product.getProductStoreId().equals(userId)) {
                throw new IllegalAccessException("您無權操作該產品，因為它不屬於您的商店。");
            }

            // 更新产品的属性
            // 创建 Product 实体对象
            product.setProductName(productDTO.getProductName());
            product.setProductPrice(productDTO.getProductPrice());
            product.setSize(productDTO.getSize());
            product.setProductStatus(productDTO.isProductStatus());
            product.setProductCategoryId(productDTO.getProductCategoryId());
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
            product.setProductPicture(blobImg);// 圖片資料
            productDao.save(product);
            // 保存更新后的产品
//            if (product != null) {
//
//
//            } else {
//                throw new IllegalArgumentException("產品名稱不存在");
//            }
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
        String base64Image = Base64.getEncoder().encodeToString(product.getProductPicture());
        dto.setProductPicture(base64Image);// 圖片資料
        return dto;
    }


}