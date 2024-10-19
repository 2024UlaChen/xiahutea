package idv.tia201.g2.web.order.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.InvoiceService;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Transactional
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // -------- FINISH ---------------------------------
    // 前台 新增資料

    @PostMapping("addOrder")
    public OrderDto addNewOrder(@RequestBody OrderDto orderDto) {
        OrderDto rtnOrderDto = orderService.addOrder(orderDto.getOrders(), orderDto.getOrderDetails());
        return rtnOrderDto;
    }

    // 前台 訂單列表 顯示
    @GetMapping("member/{customerId}")
    public Page<OrderDto> memberOrder(
            @PathVariable Integer customerId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateStart, // 開始日期
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateEnd, // 結束日期
            HttpSession httpSession
    ) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        if (totalUserDTO.getUserTypeId() != 0) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderCreateDatetime"));
        // 判斷是否有日期範圍
        if (dateStart != null && dateEnd != null) {
            Timestamp startTimestamp = Timestamp.valueOf(dateStart.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(dateEnd.plusDays(1).atStartOfDay());
            // 篩選在日期範圍內的
            return orderService.findByCustomerIdAndDateRange(customerId, startTimestamp, endTimestamp, pageable);
        }
        // 查詢全部
        return orderService.findByCustomerId(customerId, pageable);
    }

    // 前台 訂單明細 顯示
    @GetMapping("member/detail/{orderId}")
    public OrderDto memberDetail(@PathVariable Integer orderId, HttpSession httpSession) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        OrderDto order = orderService.findByMemberOrderId(orderId);
        if (totalUserDTO.getUserTypeId() != 0 || !(totalUserDTO.getUserId().equals(order.getOrders().getCustomerId()))) {
            OrderDto orderDto = new OrderDto();
            return orderDto;
        }
        return order;
    }

    // 前台 訂單明細 新增評分
    @PutMapping("member/star/{orderId}")
    public Orders addNewStar( @PathVariable Integer orderId, @RequestBody Orders reqOrders) {
        reqOrders.setOrderId(orderId);
        return orderService.addStar(reqOrders);
    }

    // 後台 訂單列表 顯示 & 判斷權限
    @GetMapping("manage")
    public Page<Orders> findAllByPageable(
            HttpSession httpSession,
            @RequestParam(required = false) Integer orderId,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String memberNickname,
            @RequestParam(required = false) Integer orderStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateStart, // 開始日期
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateEnd, // 結束日期
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        Integer userTypeId = totalUserDTO.getUserTypeId();

        // 判斷權限：如果不是店家或管理者權限，不可檢視
        if (userTypeId != 3 && userTypeId != 1) {
            return Page.empty();
        }
        // 判斷權限：如果是admin可看全部訂單，如果是store只能看storeId的訂單
        Integer userId = totalUserDTO.getUserId();
        Integer storeId = (userTypeId == 1) ? userId : null;

        return orderService.findByCriteria(
                orderId, storeId, storeName, memberNickname,
                orderStatus, dateStart, dateEnd, page, size
        );
    }

    // 後台 訂單明細 顯示
    @GetMapping("manage/{orderId}")
    public List<OrderDetail> detail(@PathVariable Integer orderId, HttpSession httpSession) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        // 判斷權限：只有管理者權限可檢視全部，店家只可檢視該店家的訂單
        Integer userTypeId = totalUserDTO.getUserTypeId();
        if(userTypeId == 3){
            return orderService.findByOrderId(orderId);
        }else if(userTypeId == 1){
            Integer storeId = orderService.findByMemberOrderId(orderId).getOrders().getStoreId();
            if(!storeId.equals(totalUserDTO.getUserId())){
                return null;
            }
            return orderService.findByOrderId(orderId);
        }
        return null;
    }

    // 後台 訂單明細 修改
    @PutMapping("manage/{orderId}")
    public Orders save( @PathVariable Integer orderId, @RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.updateStatus(reqOrders);
    }
}