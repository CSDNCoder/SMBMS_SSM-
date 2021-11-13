package com.csnz.service;/*
 *
 *@create 2021-11-08-14:02
 */

import com.csnz.pojo.Bill;

import java.util.List;

public interface BillService {
    //根据商品名称、供应商id、是否付款 查询订单总数
    public abstract int getBillCount(String queryProductName,int queryProviderId,int queryIsPayment);

    //根据商品名称、供应商id、是否付款 查询订单列表
    public abstract List<Bill> getBillList(String queryProductName, int queryProviderId, int queryIsPayment, int currentPageNo, int pageSize);

    // 新添订单
    public abstract boolean addBill(Bill bill);

    // 根据 订单id 查询订单信息
    public abstract Bill findBill(int billId);

    // 修改 订单信息
    public abstract boolean updateBill(int billId,Bill bill);

    // 根据id 删除 订单
    public abstract boolean deleteBill(int billId);
}
