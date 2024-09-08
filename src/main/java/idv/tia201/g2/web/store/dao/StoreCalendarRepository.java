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

        @Query("SELECT sc.store FROM StoreCalendar sc WHERE sc.storeHoliday = :date")
        List<Store> findByStoreHoliday(@Param("date") Date date);



    }
