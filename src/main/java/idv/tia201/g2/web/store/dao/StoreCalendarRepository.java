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
                "s.storeId = sc.store.storeId " +
                "WHERE sc.storeHoliday = :date")
        List<Store> findByStoreHoliday(@Param("date") Date date);

        @Query("SELECT s FROM Store s JOIN StoreCalendar sc ON s.storeId = sc.storeId")
        List<Store> findAllByStore();


        @Query("SELECT s FROM Store s JOIN StoreCalendar sc ON s.storeId = sc.storeId WHERE s.storeId = :sotreId")
        List<Store> findStoreByStoreId(@Param("sotreId") Integer storeId);

        @Query("SELECT sc.storeHoliday FROM StoreCalendar sc WHERE sc.storeId = :sotreId AND sc.storeHoliday >= current date ")
        List<Date> findStoreCalendarsByStoreId(@Param("sotreId") Integer storeId);


        @Query("SELECT s FROM Store s LEFT JOIN StoreCalendar sc ON s.storeId = sc.storeId WHERE s.storeId NOT IN(SELECT sc2.storeId FROM StoreCalendar sc2 WHERE sc2.storeHoliday = CURRENT DATE )  AND s.storeStatus = 1")
        List<Store> findByStoreHolidayAndStoreStatus();



    }
