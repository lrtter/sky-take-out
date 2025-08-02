package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.entity.SaleReport;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        TurnoverReportVO turnoverReportVO= new TurnoverReportVO();
        String dtateList="";
        String turnoverList="";
        for(LocalDate i=begin; i.isBefore( end) || i.equals(end); i=i.plusDays(1)){
            dtateList+=i+",";
            Double turnover=reportMapper.getTurnover(LocalDateTime.of(i, LocalTime.MIN), LocalDateTime.of(i, LocalTime.MAX));
            if (turnover==null)
                turnover=0.0;
            turnoverList+=turnover+",";
        }
        turnoverReportVO.setTurnoverList(turnoverList);
        turnoverReportVO.setDateList(dtateList);
        return turnoverReportVO;
    }

    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        UserReportVO userReportVO= new UserReportVO();
        String dtateList="";
        String newUserList="";
        String totalUserList="";
        for(LocalDate i=begin; i.isBefore( end) || i.equals(end); i=i.plusDays(1)){
            dtateList+=i+",";

            Integer newuser=userMapper.getnewuser(LocalDateTime.of(i, LocalTime.MIN), LocalDateTime.of(i, LocalTime.MAX));
            if (newuser==null)
                newuser=0;
            newUserList+=newuser+",";

            Integer totalUser=userMapper.getTotalUser(LocalDateTime.of(i, LocalTime.MAX));
            if (totalUser==null)
                totalUser=0;
            totalUserList+=totalUser+",";
        }
        userReportVO.setDateList(dtateList);
        userReportVO.setNewUserList(newUserList);
        userReportVO.setTotalUserList(totalUserList);
        return userReportVO;
    }

    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        SalesTop10ReportVO salesTop10ReportVO= new SalesTop10ReportVO();
        String nameList="";
        String numberList="";
        List<SaleReport> list=orderDetailMapper.top10(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        if(list!= null){
            for (SaleReport report : list) {
                nameList+=report.getName()+",";
                numberList+=report.getNumber()+",";
            }
        }
        salesTop10ReportVO.setNameList(nameList);
        salesTop10ReportVO.setNumberList(numberList);

        return salesTop10ReportVO;
    }

    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        OrderReportVO orderReportVO= new OrderReportVO();
        String dateList="";
        String orderCountList="";
        String validOrderCountList="";

        for(LocalDate i=begin; i.isBefore( end) || i.equals(end); i=i.plusDays(1)){
            dateList+=i+",";
            Integer validOrderCount = orderMapper.calculate(LocalDateTime.of(i, LocalTime.MIN), LocalDateTime.of(i, LocalTime.MAX), Orders.COMPLETED);
            Integer orderCount = orderMapper.calculate(LocalDateTime.of(i, LocalTime.MIN), LocalDateTime.of(i, LocalTime.MAX),null);
            orderCountList+=orderCount+",";
            validOrderCountList+=validOrderCount+",";
        }

        orderReportVO.setTotalOrderCount(orderMapper.calculate(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX),null));
        orderReportVO.setValidOrderCount(orderMapper.calculate(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), Orders.COMPLETED));
        orderReportVO.setOrderCompletionRate(orderReportVO.getValidOrderCount().doubleValue()/orderReportVO.getTotalOrderCount().doubleValue());
        orderReportVO.setDateList(dateList);
        orderReportVO.setOrderCountList(orderCountList);
        orderReportVO.setValidOrderCountList(validOrderCountList);

        return orderReportVO;
    }
}
