package com.csnz.controller;/*
 *
 *@create 2021-11-08-19:33
 */
import com.alibaba.fastjson.JSONArray;
import com.csnz.pojo.Provider;
import com.csnz.pojo.User;
import com.csnz.service.ProviderService;
import com.csnz.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/Provider")
public class ProviderManagerController {
    @Autowired
    private ProviderService providerService;
    @Autowired
    private PageSupport pageSupport;
    /*
     *************************      查看供应商列表模块   *********************************
     */
    /**
     * @Des 供应商编码、供应商名称、pageIndex
     * @Author zzj
     * @Date 2021/11/8/0008 23:18
     **/
    @GetMapping("/toProviderPage")
    public String toProviderPage(String queryProCode , String queryProName , String pageIndex , HttpServletRequest request){
        //这里需要设置分页参数

        //1、下面是默认要返回的分页信息 还没根据前端传来的做转化
        int pageSize = 5;           // 一页显式的数据量 默认为 5
        int currentPageNo = 1;      // 当前页码
        int totalCount = 0;         // 根据条件查询出来的记录总数量 默认为0
        int totalPageCount = 0;     // 全部数据量 算出 总共显示的页数

        if(pageIndex != null && !pageIndex.trim().equals("")){
            try {
                currentPageNo = Integer.parseInt(pageIndex);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // 根据供应商编码 或 供应商名称 查询供应商总数
        totalCount = providerService.getProviderCounts(queryProCode,queryProName);

        //4、设置pageSupport的参数
        pageSupport.setPageSize(pageSize);              // 设置 页面容量
        pageSupport.setTotalCount(totalCount);          // 设置 总数量(表)
        pageSupport.setCurrentPageNo(currentPageNo);    // 设置 当前页码 来自于用户输入
        //总共显示的页数
        totalPageCount = pageSupport.getTotalPageCount();

        //5、如果页数小于1，就显示第一页  页数大于 最后一页就 显示最后一页
        if(currentPageNo<1){
            currentPageNo =1;
        }else if(currentPageNo>totalPageCount){
            currentPageNo = totalPageCount;
        }


        //6、根据条件 查询供应商列表
        List<Provider> providerList = providerService.getProviderList(queryProCode, queryProName, currentPageNo, pageSize);
        //将信息返回至前端页面
        request.setAttribute("providerList",providerList);

        //潮汕奴仔_优化： 这里加一步 判断此 列表是否为空 是则 清零页码信息 并返回
        if(providerList.size() == 0){
            System.out.println("\n潮汕奴仔_优化\n");
            request.setAttribute("queryProCode",queryProCode);
            request.setAttribute("queryProName",queryProName);
            request.setAttribute("totalPageCount",0);
            request.setAttribute("totalCount",0);
            request.setAttribute("currentPageNo",0);
            return "providerlist";
        }

        //8、将所有信息返回至前端展示
        request.setAttribute("queryProCode",queryProCode);
        request.setAttribute("queryProName",queryProName);

        request.setAttribute("totalPageCount",totalPageCount);
        request.setAttribute("totalCount",totalCount);
        request.setAttribute("currentPageNo",currentPageNo);

        return "providerlist";
    }

    /*
     *************************      新添供应商模块   *********************************
     */

    // 去往 添加供应商 页面
    @GetMapping("/toAddProviderPage")
    public String toAddProviderPage(){
        return "provideradd";
    }

    // 添加供应商
    @PostMapping("/addProvider")
    public String addProvider(Provider provider,HttpServletRequest request){
        //封装数据 存入 provider对象
        //下面两个不是表单提供的
        provider.setCreatedBy(((User)request.getSession().getAttribute("userSession")).getId());
        provider.setCreationDate(new Date());
        //调用service进行添加供应商 返回结果
        boolean flag = providerService.addProvider(provider);
        // 如果添加成功 返回到供应商列表页面 查询数据
        if(flag){
            return "redirect:/Provider/toProviderPage";
        }
        // 添加失败 返回到当前 添加供应商页面
            return "provideradd";
    }

    /*
     *************************      查看供应商信息模块   *********************************
     */
    @GetMapping("/viewProvider/{proId}")
    public ModelAndView viewProvider(@PathVariable String proId, ModelAndView modelAndView){
        // 转换前端传来的id
        int id = Integer.parseInt(proId);

        // 根据id查询供应商信息
        Provider provider = providerService.findById(id);

        // 返回至前端进行展示
        modelAndView.addObject("provider",provider);
        modelAndView.setViewName("providerview");
        return modelAndView;
    }


    /*
     *************************      修改供应商信息模块   *********************************
     */

    // 跳转到 供应商修改页面 并查询供应商信息
    @GetMapping("/toModifyProviderPage/{proId}")
    public String toModifyProviderPage(@PathVariable String proId,HttpServletRequest request){
        //根据此id查询供应商信息
        int id =  Integer.parseInt(proId);
        Provider provider = providerService.findById(id);
        // 将信息 传入requset
        request.setAttribute("provider",provider);
        // 跳转至 修改供应商页面
        return "providermodify";
    }

    // 修改供应商信息
    @PostMapping("/modifyProvider")
    public String modifyProvider(String proid , Provider provider,HttpServletRequest request){
        int id = Integer.parseInt(proid);
        provider.setModifyBy(((User)request.getSession().getAttribute("userSession")).getId());
        provider.setModifyDate(new Date());
        // 将前端传来的 provider 的信息 update到数据库
        boolean flag = providerService.updateProvider(id, provider);
        // 如果更新成功 返回至 供应商列表 进行查看
        if(flag){
            return "redirect:/Provider/toProviderPage";
        }else{
            // 如果更新失败 返回至 当前 供应商修改页面
            return "providermodify";
        }
    }


    /*
     *************************      删除供应商模块   *********************************
     */
    @GetMapping(value = "/deleteProvider/{proId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteProvider(@PathVariable String proId){
        // 将前端传来的值 进行转换
        int id = Integer.parseInt(proId);
        HashMap<String, String> map = new HashMap<>();
        if (id <= 0) {
            map.put("delResult","notexist");
        }else{
            //删除指定id 的供应商
            boolean flag = providerService.deleteProvider(id);
            if(flag){
                map.put("delResult","true");
            }else{
                map.put("delResult","false");
            }
        }
        return JSONArray.toJSONString(map);
    }


    // 订单 添加模块 需要查询供应商列表进行展示
    @GetMapping(value = "/getProviderList",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getProviderList(){
        int providerCounts = providerService.getProviderCounts("", "");
        List<Provider> providerList = providerService.getProviderList("", "", 1, providerCounts);
        return JSONArray.toJSONString(providerList);
    }
}
