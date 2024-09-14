package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.ProductCategory;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

    ProductCategory findByCategorySort(Integer categorySort);
    // 根據分類排序和店家 ID 查詢商品分類
    ProductCategory findByCategorySortAndStoreId(Integer categorySort, Integer storeId);

    void deleteAllBy(List<Integer> ids);

}
