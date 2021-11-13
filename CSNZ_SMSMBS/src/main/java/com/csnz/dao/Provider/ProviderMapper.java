package com.csnz.dao.Provider;/*
 *
 *@create 2021-11-08-19:35
 */

import com.csnz.pojo.Provider;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
@Repository作用：把资源让Spring来管理，相当于在xml中配置了一个bean
value属性指定bean的id，如果不指定，默认id为当前类的类名(首字母小写) providerMapper
 */
@Repository
public interface ProviderMapper {
    // 根据供应商编码 或 供应商名称 查询供应商总数
    public abstract int getProviderCounts(@Param("proCode") String queryProCode,@Param("proName") String queryProName);

    // 根据 供应商编码 和 供应商名称 和 当前页码 页码size 查询供应商数据列表
    public abstract List<Provider> getProviderList(@Param("proCode") String queryProCode,@Param("proName") String queryProName,@Param("currentPageNo") int currentPageNo,@Param("pageSize") int pageSize);

    // 添加供应商
    public abstract int addProvider(Provider provider);

    // 根据id 查询供应商信息
    public abstract Provider findById(@Param("proId") int proId);


    // 根据id 修改 供应商信息
    public abstract int updateProvider(@Param("proId") int proId, @Param("provider") Provider provider);

    // 根据id 删除 供应商
    public abstract int deleteProvider(@Param("proId") int proId);


}
