package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into order_detail (order_id, dish_id, setmeal_id, name, image, dish_flavor, number, amount) " +
            "values (#{orderId}, #{dishId}, #{setmealId}, #{name}, #{image}, #{dishFlavor}, #{number}, #{amount})")
    void insert(OrderDetail orderDetail);

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> selectById(Orders order);
}
