//package idv.tia201.g2.web.cart.dao.impl;
//
//import idv.tia201.g2.web.cart.dao.CartDao;
//import org.springframework.stereotype.Repository;
//
//
//因取用資料來自product 及 DAO 暫時 保留此DAO，未來可針對Cart操作，若無，可移除
//public class CartDaoImpl implements CartDao {
//
//    @Override
//    public Product selectByProductId(Integer id) {
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        return session.get(Product.class, id);
//    }
//}
