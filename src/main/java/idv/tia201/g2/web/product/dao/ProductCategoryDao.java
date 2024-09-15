package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.ProductCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

    ProductCategory findByCategorySort(Integer categorySort);
    // 根據分類排序和店家 ID 查詢商品分類

    @Query(value = "SELECT * FROM product_category WHERE category_sort = ?1 AND product_store_id = ?2", nativeQuery = true)
    ProductCategory findByCategorySortAndProductStoreId(Integer categorySort, Integer productStoreId);

    void deleteAllByCategoryIdIn(List<Integer> ids);
    Page<ProductCategory> findAll(Pageable pageable);


}
