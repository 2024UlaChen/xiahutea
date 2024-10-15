package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.advertise.dao.AdsDao;
import idv.tia201.g2.web.advertise.vo.Advertise;
import idv.tia201.g2.web.product.dao.ProductCategoryRepository;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.dao.ProductDtoDao;
import idv.tia201.g2.web.product.dto.ProductDTO;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AdsDao adsDao;

    @Autowired
    private ProductDtoDao productDtoDao;

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
    public Core addProduct(ProductDTO productDTO, Integer userTypeId, Integer userId, byte[] blobImg) throws IOException {

        Core core = new Core();

        try {
            if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
                core.setMessage("請輸入產品名稱");
                return core;
            }

//            Product existingProduct = productDao.findByProductName(productDTO.getProductName());
            Product existingProduct = productDao.findByProductNameAndProductStoreIdAndSizeAndProductCategoryId(productDTO.getProductName(),userId,productDTO.getSize(),productDTO.getProductCategoryId());

            if (existingProduct != null) {  // 如果查询结果不为 null，表示商品名称已经存在
                core.setMessage("商品已存在");
                return core;
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

            Product save = productDao.save(product);// 保存商品到数据库
            core.setData(save);
            core.setSuccessful(true);
            core.setMessage("商品加入成功");
            return core;
        } catch (Exception exp) {
            exp.getStackTrace();
            System.out.println(exp.getMessage());
        };
        return core;
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
        Advertise ads = adsDao.findFirstActiveAdByUserId(adsTotalUserid);
        // 檢查是否找到廣告
        if (ads != null) {
            // 返回第一張圖片的 URL
            return ads.getImgUrl(); // 假設 imageUrl 是存儲圖片的欄位
        }
        return null; // 如果沒有廣告，則返回 null
    }

    @Override
    public Page<ProductDTO> getProductList(ProductDTO productDTO, Integer page) {

        Pageable pageable = PageRequest.of(page, 10,
                Sort.by(Sort.Order.desc("productStoreId"),
                        (Sort.Order.asc("productId"))));

        String storeName = productDTO.getStoreName();
        String productCategoryName = productDTO.getProductCategoryName();
        String productName = productDTO.getProductName();

        // 全部產品
        if (storeName == null && productCategoryName == null && productName == null) {
            Page<ProductDTO> productDTOList = productDtoDao.findProductDTOList(pageable);
            return productDTOList;
        }

        // 依產品名搜尋
        if (storeName == null && productCategoryName == null && productName != null) {
            return productDtoDao.findProductDTOListByProductName(productName, pageable);
        }

        // 依分類搜尋
        if (storeName == null && productCategoryName != null && productName == null) {
            return productDtoDao.findProductDTOListByProductCategoryName(productCategoryName, pageable);
        }

        // 依店家搜尋
        if (storeName != null && productCategoryName == null && productName == null) {
            return productDtoDao.findProductDTOListByStoreName(storeName, pageable);
        }

        // 依店家 & 產品分類搜尋
        if (storeName != null && productCategoryName != null && productName == null) {
            return productDtoDao.findProductDTOListByStoreNameAndProductCategoryName(storeName, productCategoryName, pageable);
        }

        // 依店家 & 產品名稱搜尋
        if (storeName != null && productCategoryName == null && productName != null) {
            return productDtoDao.findProductDTOListByStoreNameAndProductName(storeName, productName, pageable);
        }

        // 依產品分類 & 產品分類搜尋
        if (storeName == null && productCategoryName != null && productName != null) {
            return productDtoDao.findProductDTOListByProductCategoryNameAndProductName(productCategoryName, productName, pageable);
        }

        // 依產品分類 & 產品分類 & 店家名稱搜尋
        return productDtoDao.findProductDTOListByProductCategoryNameAndProductNameAndStoreName(productCategoryName, productName, storeName, pageable);
    }


    public Core updateProduct(Integer productId, ProductDTO productDTO,Integer userTypeId, Integer userId, byte[] blobImg) {

        Core core = new Core();


        try {
            // 查找现有产品
            Product product = productDao.findByProductId(productId);
            Integer storeId = product.getProductStoreId();
            if (userTypeId == 1 && !product.getProductStoreId().equals(userId)) {
                core.setMessage("您無權操作該產品，因為它不屬於您的商店。");
                return core;
            }

            Product repeat = productDao.findByProductNameAndProductStoreIdAndSizeAndProductCategoryId(productDTO.getProductName(), storeId, productDTO.getSize(),productDTO.getProductCategoryId());
            if (repeat != null && !(repeat.getProductId().equals(productId))){
                core.setMessage("商品重複，請重新確認");
                return core;
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
            if(blobImg!=null && blobImg.length>0){
                product.setProductPicture(blobImg);// 圖片資料
            }
            Product save = productDao.save(product);
            core.setMessage("修改成功");
            core.setSuccessful(true);
            core.setData(save);
            return core;
        } catch (Exception e) {
            // 捕捉并记录异常
            e.printStackTrace();
            core.setMessage("請聯繫管理員");
            return core;
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