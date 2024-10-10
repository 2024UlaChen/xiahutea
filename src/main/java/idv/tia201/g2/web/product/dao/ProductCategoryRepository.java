package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.ProductCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>,ProductCategoryOperation {

    ProductCategory findByCategorySort(Integer categorySort);
    // 根據分類排序和店家 ID 查詢商品分類

    @Query(value = "SELECT * FROM product_category WHERE category_sort = ?1 AND product_store_id = ?2", nativeQuery = true)
    ProductCategory findByCategorySortAndProductStoreId(Integer categorySort, Integer productStoreId);

    void deleteAllByCategoryIdIn(List<Integer> ids);
//   分業處理
    Page<ProductCategory> findAll(Pageable pageable);
// 自定義查詢檢查分類排序是否重複
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ProductCategory c WHERE c.categorySort = :categorySort AND c.categoryId != :categoryId")
    boolean existsByCategorySortAndCategoryIdNot(@Param("categorySort") Integer categorySort,
                                                 @Param("categoryId") Integer categoryId);
     ProductCategory findByCategoryId(Integer categoryId);
//用分類名作模糊查詢
    List<ProductCategory> findByCategoryNameContaining(String categoryName);
    List<ProductCategory> findAllByOrderByCategorySortAsc();
   List<ProductCategory> findByCategoryNameAndProductStoreId(String categoryName, Integer productStoreId);
    Page<ProductCategory> findByProductStoreId(Integer productStoreId, Pageable pageable);
   List<ProductCategory> findByProductStoreId(Integer productStoreId);
    List<ProductCategory> findByProductStoreIdOrderByCategorySortAsc(Integer productStoreId);

}
