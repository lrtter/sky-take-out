package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.entity.SaleReport;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private WorkspaceService workspaceService;
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

    @Override
    public void export(HttpServletResponse response) {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.minusDays(30);
        LocalDate end = now.minusDays(1);
        BusinessDataVO businessDataVO = workspaceService.businessData(begin, end);

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");

        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);

             XSSFSheet sheet1 = excel.getSheet("Sheet1");
             sheet1.getRow(1).getCell(1).setCellValue("时间："+begin+"至"+end);

            XSSFRow row = sheet1.getRow(3);

            row.getCell(2).setCellValue(businessDataVO.getTurnover());
            row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessDataVO.getNewUsers());

            row = sheet1.getRow(4);

            row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4).setCellValue(businessDataVO.getUnitPrice());

            for(int i=0;i<30;i++){
                LocalDate time=begin.plusDays( i);
                BusinessDataVO businessDataVO1 = workspaceService.businessData(time, time);
                row = sheet1.getRow(7+i);
                row.getCell(1).setCellValue(time.toString());
                row.getCell(2).setCellValue(businessDataVO1.getTurnover());
                row.getCell(3).setCellValue(businessDataVO1.getValidOrderCount());
                row.getCell(4).setCellValue(businessDataVO1.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessDataVO1.getUnitPrice());
                row.getCell(6).setCellValue(businessDataVO1.getNewUsers());
            }


            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            out.close();
            excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
