    package idv.tia201.g2.web.store.dao;

    import idv.tia201.g2.web.store.vo.Store;
    import idv.tia201.g2.web.store.vo.StoreCalendar;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import java.util.Date;
    import java.util.List;

    @Repository
    public interface StoreCalendarRepository extends JpaRepository<StoreCalendar, Integer> {


        @Query("FROM Store s " +
                "JOIN StoreCalendar sc ON " +
                "s.storeId = sc.storeId " +
                "WHERE YEAR(sc.storeHoliday) = YEAR(:date)" +
                "AND MONTH(sc.storeHoliday) = MONTH(:date)" +
                "AND DAY(sc.storeHoliday) = DAY(:date)")
        List<Store> findByStoreHoliday(@Param("date") Date date);

        @Query("SELECT s FROM Store s JOIN StoreCalendar sc ON s.storeId = sc.storeId")
        List<Store> findAllByStore();

        @Query("select s FROM Store s " +
                "JOIN StoreCalendar sc ON " +
                "s.storeId = sc.storeId " +
                "WHERE sc.storeHoliday = '2024-06-25'")
        List<Store> selectOneDayForTest();

        @Query("SELECT s FROM Store s JOIN StoreCalendar sc ON s.storeId = sc.storeId WHERE s.storeId = :sotreId")
        List<Store> findStoreByStoreId(@Param("sotreId") Integer storeId);

    }
