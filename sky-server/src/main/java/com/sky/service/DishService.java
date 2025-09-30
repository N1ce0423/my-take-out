package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 启用、禁用菜品
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 新增菜品和对应的口味
     * @param
     */
    void saveWithFlavor(DishDTO dishDTO);
}
