package com.zz.crowd.mvc.handler;

import com.zz.crowd.entity.Menu;
import com.zz.crowd.response.ResponseEntity;
import com.zz.crowd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("whole/tree")
    public ResponseEntity getWholeTree(){
        List<Menu> all = menuService.getAll();
        Menu menuRoot = null;

        HashMap<Integer, Menu> map = new HashMap<>();

        for (Menu menu : all) {
            map.put(menu.getId(), menu);
        }

        for (Menu menu : all) {
            if(menu.getPid() == null){
                menuRoot = menu;
                continue;
            }
            map.get(menu.getPid()).getChildren().add(menu);
        }

        /*for (Menu menu : all) {
            if(menu.getPid() == null){
                menuRoot = menu;
                continue;
            }
            for (Menu menu1 : all) {
                if(menu1.getId().equals(menu.getPid())){
                    menu1.getChildren().add(menu);
                    break;
                }
            }
        }*/

        return ResponseEntity.successWithData(menuRoot);
    }



    @RequestMapping("/save")
    @ResponseBody
    public ResponseEntity saveMenu(Menu menu){
        menuService.save(menu);
        return ResponseEntity.successWithData(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseEntity updateMenu(Menu menu){
        menuService.update(menu);
        return ResponseEntity.successWithData(null);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ResponseEntity removeMenu(@RequestParam("id") Integer id){

        if(id == null){
            return ResponseEntity.errorWithMessage("id 不能为空");
        }
        menuService.remove(id);
        return ResponseEntity.successWithData(null);
    }
}
