package com.csnz.service;/*
 *
 *@create 2021-11-08-19:50
 */

import com.csnz.pojo.Provider;

import java.util.List;

public interface ProviderService {

    // 根据供应商编码 或 供应商名称 查询供应商总数
    public abstract int getProviderCounts(String queryProCode,String queryProName);

    // 根据 供应商编码 和 供应商名称 和 当前页码 页码size 查询供应商数据列表
    public abstract List<Provider> getProviderList(String queryProCode, String queryProName, int currentPageNo, int pageSize);

    // 添加供应商
    public abstract boolean addProvider(Provider provider);

    // 根据id 查询供应商信息
    public abstract Provider findById(int proId);

    // 根据id 修改 供应商信息
    public abstract boolean updateProvider(int proId,Provider provider);

    // 根据id 删除 供应商
    public abstract boolean deleteProvider(int proId);
}
