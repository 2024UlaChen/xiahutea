package idv.tia201.g2.web.product.dao.Impl;

import idv.tia201.g2.web.product.dao.ProductCategoryOperation;
import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryRepositoryImpl implements ProductCategoryOperation {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<ProductCategoryDTO> findProductCategoryDTOByStoreId(Integer storeId,Pageable pageable) {
        // 計算總頁數
        String countJpql = "SELECT COUNT(*) FROM product_category WHERE product_store_id = ?";
        Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, storeId);
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT pc.*,s.store_name " +
                "FROM product_category pc JOIN store s ON pc.product_store_id = s.store_id " +
                "where pc.product_store_id = ? " +
                "order by pc.product_category_status DESC, pc.category_sort " +
                "LIMIT ? OFFSET ?";
        Query query = em.createNativeQuery(jpql)
                .setParameter(1, storeId)
                .setParameter(2, pageable.getPageSize())                      // 每頁筆數
                .setParameter(3, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductCategoryDTO> ProductCategoryDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
            productCategoryDTO.setCategoryId((Integer) result[0]);
            productCategoryDTO.setProductStoreId((Integer) result[1]);
            productCategoryDTO.setCategoryName((String) result[2]);
            productCategoryDTO.setCategorySort((Integer) result[3]);
            productCategoryDTO.setCategoryStatus((Boolean) result[4]);
            productCategoryDTO.setStoreName((String) result[5]);

            ProductCategoryDTOList.add(productCategoryDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductCategoryDTOList, pageable, totalRecords);
    }

    public Page<ProductCategoryDTO> findProductCategoryDTO(Pageable pageable) {
        // 計算總頁數
        String countJpql = "SELECT COUNT(*) FROM product_category";
        Query countQuery = em.createNativeQuery(countJpql);
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT pc.*,s.store_name " +
                "FROM product_category pc JOIN store s ON pc.product_store_id = s.store_id " +
                "order by pc.product_store_id ,pc.product_category_status DESC, pc.category_sort " +
                "LIMIT ? OFFSET ? ";
        Query query = em.createNativeQuery(jpql)
                .setParameter(1, pageable.getPageSize())                      // 每頁筆數
                .setParameter(2, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductCategoryDTO> ProductCategoryDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
            productCategoryDTO.setCategoryId((Integer) result[0]);
            productCategoryDTO.setProductStoreId((Integer) result[1]);
            productCategoryDTO.setCategoryName((String) result[2]);
            productCategoryDTO.setCategorySort((Integer) result[3]);
            productCategoryDTO.setCategoryStatus((Boolean) result[4]);
            productCategoryDTO.setStoreName((String) result[5]);

            ProductCategoryDTOList.add(productCategoryDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductCategoryDTOList, pageable, totalRecords);
    }

    public Page<ProductCategoryDTO> findProductCategoryDTOByStoreName(String storeName, Pageable pageable) {
        // 計算總頁數
        String countJpql = "SELECT COUNT(*) FROM product_category pc JOIN store s ON s.store_id = pc.product_store_id WHERE s.store_name LIKE ?";
        Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + storeName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT pc.*,s.store_name " +
                        "FROM product_category pc JOIN store s ON pc.product_store_id = s.store_id " +
                        "WHERE s.store_name LIKE ? " +
                        "order by pc.product_category_status DESC, pc.category_sort " +
                        "LIMIT ? OFFSET ?";
        Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, pageable.getPageSize())                      // 每頁筆數
                .setParameter(3, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductCategoryDTO> ProductCategoryDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
            productCategoryDTO.setCategoryId((Integer) result[0]);
            productCategoryDTO.setProductStoreId((Integer) result[1]);
            productCategoryDTO.setCategoryName((String) result[2]);
            productCategoryDTO.setCategorySort((Integer) result[3]);
            productCategoryDTO.setCategoryStatus((Boolean) result[4]);
            productCategoryDTO.setStoreName((String) result[5]);

            ProductCategoryDTOList.add(productCategoryDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductCategoryDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductCategoryDTO> findProductCategoryDTOByCategoryName(String categoryName, Pageable pageable) {
        // 計算總頁數
        String countJpql = "SELECT COUNT(*) FROM product_category WHERE product_category LIKE ?;";
        Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + categoryName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT pc.*,s.store_name " +
                        "FROM product_category pc JOIN store s ON pc.product_store_id = s.store_id " +
                        "WHERE product_category LIKE ? " +
                        "order by pc.product_category_status DESC, pc.category_sort " +
                        "LIMIT ? OFFSET ?";
        Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + categoryName + "%")
                .setParameter(2, pageable.getPageSize())                      // 每頁筆數
                .setParameter(3, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductCategoryDTO> ProductCategoryDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
            productCategoryDTO.setCategoryId((Integer) result[0]);
            productCategoryDTO.setProductStoreId((Integer) result[1]);
            productCategoryDTO.setCategoryName((String) result[2]);
            productCategoryDTO.setCategorySort((Integer) result[3]);
            productCategoryDTO.setCategoryStatus((Boolean) result[4]);
            productCategoryDTO.setStoreName((String) result[5]);

            ProductCategoryDTOList.add(productCategoryDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductCategoryDTOList, pageable, totalRecords);
    }

    @Override
    public Page<ProductCategoryDTO> findProductCategoryDTOByCategoryNameAndStoreName(String categoryName, String storeName, Pageable pageable) {
        // 計算總頁數
        String countJpql =
                "SELECT COUNT(*) " +
                        "FROM product_category pc JOIN store s ON pc.product_store_id = s.store_id " +
                        "WHERE s.store_name LIKE ? AND pc.product_category LIKE ?;";
        Query countQuery = em.createNativeQuery(countJpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + categoryName + "%");
        Long totalRecords = ((Number) countQuery.getSingleResult()).longValue();

        // 找到當前頁數內容
        String jpql =
                "SELECT pc.*,s.store_name " +
                        "FROM product_category pc JOIN store s ON pc.product_store_id = s.store_id " +
                        "WHERE s.store_name LIKE ? AND pc.product_category LIKE ? " +
                        "order by pc.product_category_status DESC, pc.category_sort " +
                        "LIMIT ? OFFSET ?";
        Query query = em.createNativeQuery(jpql)
                .setParameter(1, "%" + storeName + "%")
                .setParameter(2, "%" + categoryName + "%")
                .setParameter(3, pageable.getPageSize())    // 每頁筆數
                .setParameter(4, pageable.getOffset());     // 第幾頁
        List<Object[]> results = query.getResultList();

        List<ProductCategoryDTO> ProductCategoryDTOList = new ArrayList<>();

        //把結果轉換成 ProductCategoryDTOList
        for (Object[] result : results) {
            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
            productCategoryDTO.setCategoryId((Integer) result[0]);
            productCategoryDTO.setProductStoreId((Integer) result[1]);
            productCategoryDTO.setCategoryName((String) result[2]);
            productCategoryDTO.setCategorySort((Integer) result[3]);
            productCategoryDTO.setCategoryStatus((Boolean) result[4]);
            productCategoryDTO.setStoreName((String) result[5]);

            ProductCategoryDTOList.add(productCategoryDTO);
        }

        // 將 List 封裝成 Page
        return new PageImpl<>(ProductCategoryDTOList, pageable, totalRecords);
    }
}
