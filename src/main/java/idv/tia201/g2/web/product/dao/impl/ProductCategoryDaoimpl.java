package idv.tia201.g2.web.product.dao.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.vo.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ProductCategoryDaoimpl implements ProductCategoryDao {
    //連線
    @PersistenceContext
    private EntityManager em;


    @Override
    public <S extends ProductCategory> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ProductCategory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ProductCategory> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<ProductCategory> findAll() {

        return null;
    }

    @Override
    public List<ProductCategory> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(ProductCategory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends ProductCategory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ProductCategory> findByProductCategory(String productCategory) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ProductCategory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ProductCategory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ProductCategory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ProductCategory getOne(Integer integer) {
        return null;
    }

    @Override
    public ProductCategory getById(Integer integer) {
        return null;
    }

    @Override
    public ProductCategory getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends ProductCategory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ProductCategory> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ProductCategory> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ProductCategory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ProductCategory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ProductCategory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ProductCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<ProductCategory> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ProductCategory> findAll(Pageable pageable) {
        return null;
    }

//    @Override
//    public ProductCategory findById(Integer id) {
//
//        return null;
//    }
//
//    @Override
//    public int insert(ProductCategory productCategory) {
//
//        return 1;
//    }
//
//    @Override
//    public int update(ProductCategory productCategory) {
//        return 1;
//    }
//
//
//    @Override
//    public int deleteCategoryById(Integer id) {
//
//    return 1;
//    }
}
