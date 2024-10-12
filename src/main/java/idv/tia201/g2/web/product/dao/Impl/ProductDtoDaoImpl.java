package idv.tia201.g2.web.product.dao.Impl;

import idv.tia201.g2.web.product.dao.ProductDtoDao;
import idv.tia201.g2.web.product.dto.ProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDtoDaoImpl implements ProductDtoDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<ProductDTO> findProductDTOList(Pageable pageable) {
        // 計算總頁數
        String countJpql = "SELECT COUNT(*) FROM product ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql);
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ? ";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByProductName(String productName, Pageable pageable) {
        // 計算總頁數
        String countJpql = "SELECT COUNT(*) FROM product WHERE product_name like ? ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + productName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE product_name LIKE ? " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ? ";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + productName + "%")
                .setParameter(2, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByProductCategoryName(String productCategoryName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) FROM product p " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE pc.product_category LIKE ? ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, productCategoryName);
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE pc.product_category LIKE ? " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ? ";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + productCategoryName + "%")
                .setParameter(2, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByStoreName(String storeName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) FROM product p " +
                        "LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "WHERE s.store_name LIKE ? ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + storeName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE s.store_name LIKE ? " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ? ";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByStoreNameAndProductCategoryName(String storeName, String productCategoryName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) FROM product p " +
                        "LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE s.store_name LIKE ? AND pc.product_category LIKE ? ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + productCategoryName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE s.store_name LIKE ? AND pc.product_category LIKE ?" +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ?";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + productCategoryName + "%")
                .setParameter(3, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByStoreNameAndProductName(String storeName, String productName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) FROM product p " +
                        "LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "WHERE s.store_name LIKE ? AND p.product_name LIKE ?";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + productName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE s.store_name LIKE ? AND p.product_name LIKE ? " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ?";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + productName + "%")
                .setParameter(3, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByProductCategoryNameAndProductName(String productCategoryName, String productName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) FROM product p" +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE pc.product_category LIKE ? AND p.product_name LIKE ? ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + productCategoryName + "%")
                .setParameter(2, "%" + productName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE pc.product_category LIKE ? AND p.product_name LIKE ? " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ?";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + productCategoryName + "%")
                .setParameter(2, "%" + productName + "%")
                .setParameter(3, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductDTO> findProductDTOListByProductCategoryNameAndProductNameAndStoreName(String productCategoryName, String productName, String storeName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE s.store_name LIKE ? AND pc.product_category LIKE ? AND p.product_name LIKE ? ";
        jakarta.persistence.Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + productCategoryName + "%")
                .setParameter(3, "%" + productName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT " +
                        "p.product_store_id, " +
                        "s.store_name, " +
                        "p.product_id, " +
                        "pc.product_category_id, " +
                        "pc.product_category, " +
                        "p.product_name, " +
                        "p.product_price, " +
                        "p.size, " +
                        "p.product_status " +
                        "FROM product p LEFT JOIN store s ON p.product_store_id = s.store_id " +
                        "LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id " +
                        "WHERE s.store_name LIKE ? AND pc.product_category LIKE ? AND p.product_name LIKE ? " +
                        "order by p.product_store_id DESC, p.product_id " +
                        "LIMIT 10 OFFSET ? ";
        jakarta.persistence.Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + productCategoryName + "%")
                .setParameter(3, "%" + productName + "%")
                .setParameter(4, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductDTO> ProductDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductStoreId((Integer) result[0]);
            productDTO.setStoreName((String) result[1]);
            productDTO.setProductId((Integer) result[2]);
            productDTO.setProductCategoryId((Integer) result[3]);
            productDTO.setProductCategoryName((String) result[4]);
            productDTO.setProductName((String) result[5]);
            productDTO.setProductPrice((Integer) result[6]);
            productDTO.setSize((String) result[7]);
            productDTO.setProductStatus((Boolean) result[8]);

            ProductDTOList.add(productDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductDTOList, pageable, totalRecords);
    }
}
