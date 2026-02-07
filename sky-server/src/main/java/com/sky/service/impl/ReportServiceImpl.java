package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.entity.User;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 营业额统计报表
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end) {
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        List<LocalDate> dateList = new ArrayList<>();
        List<Double> turnoverList = new ArrayList<>();
        for(LocalDate date = begin; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)){
            dateList.add(date);
            Map map = new HashMap();
            map.put("begin", LocalDateTime.of(date, LocalTime.MIN));
            map.put("end", LocalDateTime.of(date, LocalTime.MAX));
            map.put("status", Orders.COMPLETED);
            turnoverList.add(orderMapper.getTurnoverByDate(map) == null ? 0.0 : orderMapper.getTurnoverByDate(map));
        }
        String dataListString = StringUtils.join(dateList, ",");
        String turnOverString = StringUtils.join(turnoverList, ",");
        turnoverReportVO.setTurnoverList(turnOverString);
        turnoverReportVO.setDateList(dataListString);
        return turnoverReportVO;
    }

    /**
     * 用户统计报表
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserReport(LocalDate begin, LocalDate end) {
        UserReportVO userReportVO = new UserReportVO();
        List<LocalDate> dateList = new ArrayList<>();
        List<Integer> userTotalList = new ArrayList<>();
        List<Integer> userIncrementList = new ArrayList<>();
        userIncrementList.add(0);
        for(LocalDate date = begin; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)){
            dateList.add(date);
            Integer userNum = userMapper.getUserNumByDate(LocalDateTime.of(date, LocalTime.MAX));
            if(!userTotalList.isEmpty()) {
                userIncrementList.add(userNum - userTotalList.get(userTotalList.size() - 1));
            }
            userTotalList.add(userNum);
        }
        userReportVO.setDateList(StringUtils.join(dateList, ","));
        userReportVO.setNewUserList(StringUtils.join(userIncrementList, ","));
        userReportVO.setTotalUserList(StringUtils.join(userTotalList, ","));
        return userReportVO;
    }
}
