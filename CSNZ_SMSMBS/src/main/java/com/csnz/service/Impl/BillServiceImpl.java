package com.csnz.service.Impl;/*
 *
 *@create 2021-11-08-14:02
 */
import com.csnz.dao.Bill.BillMapper;
import com.csnz.dao.Role.RoleMapper;
import com.csnz.pojo.Bill;
import com.csnz.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BillServiceImpl implements BillService {
    //service层调用dao层
    @Autowired
    private BillMapper billMapper;

    //  使用注解模式开发：对应类上加 @Service 和 @Autowired 此时就不用set方法注入了
//    public void setBillMapper(BillMapper billMapper) {
//        this.billMapper = billMapper;
//    }

    @Override
    public int getBillCount(String queryProductName, int queryProviderId, int queryIsPayment) {
        return billMapper.getBillCount(queryProductName,queryProviderId,queryIsPayment);
    }

    @Override
    public List<Bill> getBillList(String queryProductName, int queryProviderId, int queryIsPayment, int currentPageNo, int pageSize) {
        return billMapper.getBillList(queryProductName,queryProviderId,queryIsPayment,(currentPageNo-1)*pageSize,pageSize);
    }

    // 新添订单
    @Override
    public boolean addBill(Bill bill) {
        return billMapper.addBill(bill) == 1;
    }

    // 根据 订单id 查询订单信息
    @Override
    public Bill findBill(int billId) {
        return billMapper.findBill(billId);
    }
    // 修改 订单信息
    @Override
    public boolean updateBill(int billId, Bill bill) {
        return billMapper.updateBill(billId,bill) == 1;
    }
    // 根据id 删除 订单
    @Override
    public boolean deleteBill(int billId) {
        return billMapper.deleteBill(billId) == 1;
    }

}

