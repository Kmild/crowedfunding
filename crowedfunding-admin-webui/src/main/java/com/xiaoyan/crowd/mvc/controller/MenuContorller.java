package com.xiaoyan.crowd.mvc.controller;

import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.mvc.pojo.Menu;
import com.xiaoyan.crowd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuContorller {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/get/whole/tree.json")
    public MessageForResponce<Menu> getWholeTree(){
        List<Menu> allNodes = menuService.getAll();

        Map<Integer,Menu> mapNodes=new HashMap<>();
        for(Menu menu:allNodes){
            mapNodes.put(menu.getId(),menu);
        }

        Menu root =null;
        for (Menu menu : allNodes) {
            // 找到根节点
            if(menu.getPid() == null){
                root = menu;
                continue;
            }
            //  把当前遍历到的节点放到对应的父节点中
            Menu father = mapNodes.get(menu.getPid());
            if(father != null){
                father.getChildren().add(menu);
            }
        }

        return MessageForResponce.ReturnMessageWithSuccess("ok",root);

    }


    @RequestMapping("/menu/do/save.json")
    public MessageForResponce<String> saveMenu(Menu menu){

        // 新增数据
        menuService.saveMenu(menu);

        return MessageForResponce.ReturnMessageWithSuccess("ok",null);

    }

    @RequestMapping("/menu/do/update.json")
    public MessageForResponce<String> updateMenu(Menu menu){

        // 更新数据 pid没有传入，所以需要 有选择的更新
        menuService.updateMenuById(menu);

        return MessageForResponce.ReturnMessageWithSuccess("ok",null);

    }

    @RequestMapping("/menu/do/remove.json")
    public MessageForResponce<String> removeMenu(@RequestParam("id") Integer id){

        // 根据id删除数据
        menuService.removeMenuById(id);

        return MessageForResponce.ReturnMessageWithSuccess("ok",null);

    }

}
