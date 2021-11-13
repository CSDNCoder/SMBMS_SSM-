package com.csnz.dao.Bill;/*
 *
 *@create 2021-11-08-13:59
 */

import com.csnz.pojo.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
@Repository作用：把资源让Spring来管理，相当于在xml中配置了一个bean
value属性指定bean的id，如果不指定，默认id为当前类的类名(首字母小写) billMapper
 */
@Repository
public interface BillMapper {
    // 根据商品名称、供应商id、是否付款 查询订单总数
    public abstract int getBillCount(@Param("productName") String queryProductName,@Param("providerId") int queryProviderId,@Param("isPayment") int queryIsPayment);
    // 根据商品名称、供应商id、是否付款 查询订单列表
    public abstract List<Bill> getBillList(@Param("productName") String queryProductName,@Param("providerId")int queryProviderId,@Param("isPayment")int queryIsPayment,@Param("currentPageNo") int currentPageNo,@Param("pageSize") int pageSize);

    // 新添订单
    public abstract int addBill(@Param("bill") Bill bill);

    // 根据 订单id 查询订单信息
    public abstract Bill findBill(int billId);

    // 修改 订单信息
    public abstract int updateBill(@Param("billId") int billId,@Param("bill") Bill bill);

    // 根据id 删除 订单
    public abstract int deleteBill(@Param("billId") int billId);
}
