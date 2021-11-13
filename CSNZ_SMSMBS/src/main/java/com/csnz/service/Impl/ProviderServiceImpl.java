package com.csnz.service.Impl;/*
 *
 *@create 2021-11-08-19:52
 */

import com.csnz.dao.Provider.ProviderMapper;
import com.csnz.pojo.Provider;
import com.csnz.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProviderServiceImpl implements ProviderService {
    //service层调用dao层
    @Autowired
    private ProviderMapper providerMapper;

//  使用注解模式开发：对应类上加 @Service 和 @Autowired 此时就不用set方法注入了
//    public void setProviderMapper(ProviderMapper providerMapper) {
//        this.providerMapper = providerMapper;
//    }

    @Override
    public int getProviderCounts(String queryProCode, String queryProName) {
        return providerMapper.getProviderCounts(queryProCode,queryProName);
    }

    @Override
    public List<Provider> getProviderList(String queryProCode, String queryProName, int currentPageNo, int pageSize) {
        return providerMapper.getProviderList(queryProCode,queryProName,(currentPageNo-1)*pageSize,pageSize);
    }

    // 添加供应商
    @Override
    public boolean addProvider(Provider provider) {
        return providerMapper.addProvider(provider) == 1;
    }

    // 根据id 查询供应商信息
    @Override
    public Provider findById(int proId) {
        return providerMapper.findById(proId);
    }

    // 根据id 修改 供应商信息
    @Override
    public boolean updateProvider(int proId, Provider provider) {
        return providerMapper.updateProvider(proId,provider) == 1;
    }

    // 根据id 删除 供应商
    @Override
    public boolean deleteProvider(int proId) {
        return providerMapper.deleteProvider(proId) == 1;
    }
}
