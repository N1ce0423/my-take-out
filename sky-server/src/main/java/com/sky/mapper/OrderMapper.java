package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    @Insert("insert into orders (number, status, user_id, address_book_id, order_time, checkout_time, pay_method, pay_status, amount, remark, phone, address, consignee, user_name, tableware_number, pack_amount) " +
            "values (#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}, #{payMethod}, #{payStatus}, #{amount}, #{remark}, #{phone}, #{address}, #{consignee}, #{userName}, #{tablewareNumber}, #{packAmount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    List<Orders> selectById(Long id);

    @Update("update orders set status = #{status} where id = #{id}")
    void setStatusById(Long id, Integer status);

    @Select("select count(*) from orders where status = #{status}")
    Integer countStatus(Integer status);

    @Update("update orders set rejection_reason = #{rejectionReason} where id = #{id}")
    void setRejectReasonById(Long id, String rejectionReason);

    @Update("update orders set cancel_reason = #{cancelReason} where id = #{id}")
    void setCancelReasonById(Long id, String cancelReason);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    void update(Orders orders);

    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} " +
            "where number = #{orderNumber}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, String orderNumber);

    @Select("select * from orders where status = #{status} and order_time <= #{localDateTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime localDateTime);

    @Select("select * from orders where status = #{status}")
    List<Orders> getByStatus(Integer status);

    Double getTurnoverByDate(Map map);
}
