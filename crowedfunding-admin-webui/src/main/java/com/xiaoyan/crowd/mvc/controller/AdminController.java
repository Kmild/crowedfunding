package com.xiaoyan.crowd.mvc.controller;


import com.github.pagehelper.PageInfo;
import com.xiaoyan.crowd.exception.CrowdRemoveInvalidException;
import com.xiaoyan.crowd.mvc.pojo.Admin;
import com.xiaoyan.crowd.service.AdminService;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /*************************管理员登录*********************************/

//    @RequestMapping("/admin/do/login.html")
//    public String processAdminLogin(@RequestParam("username") String username,
//                                    @RequestParam("password") String password,
//                                    HttpSession session) {
//
//        // 1.校验登录信息并得到admin对象 -->检验 用户名 + 密码
//        Admin admin = adminService.checkLogin(username, password);
//
//        // 2.将admin对象保存到session作用域
//        session.setAttribute(MyConstantUtil.ATTR_NAME_LOGIN_ADMIN, admin);
//
//        // 3.重定向到后台主页面
//        return "redirect:/admin/to/admin-main.html";

//    }

    /*************************管理员退出*********************************/

//    @RequestMapping("/admin/do/logout.html")
//    public String processAdminLogout(HttpSession session) {
//
//        // 1.使session强制失效
//        session.invalidate();
//
//        // 2.重定向跳转页面至登录页面
//        return "redirect:/admin/to/login.html";
//
//    }

    /*************************用户维护界面---分页*********************************/
    @RequestMapping("/admin/do/page.html")
    public String processUsers(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                               Model model) {

        // 1.获取分页对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 2.将对象保存到request域
        model.addAttribute(MyConstantUtil.ATTR_NAME_PAGE_INFO,pageInfo);

        // 3.跳转页面
        return "admin-page";
    }
    /********************************用户维护界面---删除****************************************************/
     @RequestMapping("/admin/do/remove/{adminId}/{pageNum}/{keyword}.html")  //  /admin/remove/28/1/.html
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable(name = "keyword",required = false) String keyword,
                         HttpSession session
            ){

         // 判断删除的是否是自己的信息，若是则抛出异常
         Admin admin = (Admin) session.getAttribute(MyConstantUtil.ATTR_NAME_LOGIN_ADMIN);
         if(admin.getId().equals(adminId) ) {
             throw new CrowdRemoveInvalidException(MyConstantUtil.MESSAGE_REMOVE_INVALID);
         }

         // 执行删除
         adminService.removeById(adminId);

         // 页面跳转
           // 方法1 ：直接转发到admin-page.jsp页面不携带任何信息将无数据显示
//         return "admin-page";
         // 方法2 ：转发到/admin/do/page.html地址，因为是转发，所以一旦页面刷新会重复执行删除操作
//         return "/admin/do/page.html";
         // 方法3 ：重定向到/admin/do/page.html
          //同时为了保持原本所在的页面和查询关键词 再带上参数 pageNum&keyword
         return "redirect:/admin/do/page.html?pageNum="+pageNum+"&keyword="+keyword;
     }

 /********************************用户维护界面---新增****************************************************/
       @PreAuthorize("hasAnyAuthority('user:save')")
       @RequestMapping("/admin/do/add.html")
       public String processAddAdmin(Admin admin){

           adminService.saveAdmin(admin);

           return "redirect:/admin/do/page.html?pageNum="+Integer.MAX_VALUE; //直接跳转至最后一页

       }

    /********************************用户维护界面---修改****************************************************/
//    admin/edit/${admin.id}.html
        @RequestMapping("/admin/to/edit/{adminId}.html")
        public String processToEditAdmin(@PathVariable("adminId")Integer adminId,
                                       Model model){
            // 1.查询将要编辑的admin对象并将其保存到request作用域
            Admin admin = adminService.getAdminById(adminId);
            model.addAttribute(MyConstantUtil.ATTR_NAME_LOGIN_ADMIN,admin);

            // 2.转发至edit.jsp页面
            return "admin-edit";
        }

       @RequestMapping("/admin/do/edit.html")
       public String processEditAdmin(Admin admin, Model model){

            // 0.将提交的信息保存到request作用域中，方便抛异常时返回页面时 回显数据
           model.addAttribute(MyConstantUtil.ATTR_NAME_LOGIN_ADMIN,admin); // 为异常准备的吱吱吱吱

            // 1.保存更新的数据 --> 根据id（主键）更新
           adminService.updateAdminByPrimaryKrySelective(admin);

           // 2.重定向到 admin-page 页面 --> 显示修改后的信息
           return "redirect:/admin/do/page.html?keyword="+admin.getLoginAcct();

       }




    /***********************************环境测试****************************************************/


//    @RequestMapping("/test/ssm.html")
//    public String indexHello(Model model) {
//        System.out.println();
//        model.addAttribute("allAdmin", adminService.getAll());
//        return "success";
//    }
//
//
//    @RequestMapping("/send/array.html")
//    @ResponseBody
//    public String indexAjaxJson(@RequestBody List<Integer> requestBody) { //接受json格式的数据需要使用@RequestBody
//        requestBody.forEach(System.out::println);
//        String s = null;
//        System.out.println("\uE11D");
////        System.out.println(s.length());
//        System.out.println(10 / 0);
////        System.out.println(requestBody);
//        return "success";
//    }
//
//
//    @RequestMapping("/send/error.html")
//    @ResponseBody
//    public String TestError() {
//        String s = null;
//        System.out.println("\uE11D");
//        System.out.println(s.length());
////        System.out.println(10/0);
////        System.out.println(requestBody);
//        return "success";
//    }


}
