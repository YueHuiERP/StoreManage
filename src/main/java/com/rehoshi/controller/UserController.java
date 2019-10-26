package com.rehoshi.controller;

import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.UserPageSearch;
import com.rehoshi.model.User;
import com.rehoshi.service.UserService;
import com.rehoshi.util.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/user",produces = "application/json; charset=utf-8" )
public class UserController extends BaseController{

   @Autowired
    UserService userService ;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(User user, HttpServletRequest request){
        RespData<User> login = userService.login(user);
        if(login.data != null){
            login.data.setLastLoginTime(new Date());
            ContextUtil.setCurUser(request, login.data);
        }
        return $(login) ;
    }

    @RequestMapping("cur")
    @ResponseBody
    public RespData<User> curUser(){
        return RespData.success(ContextUtil.getCurUser()) ;
    }


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public String listInPage(@RequestParam String search, @RequestParam("page") int pageIndex, @RequestParam("limit") int pageSize){

        UserPageSearch userPageSearch = new UserPageSearch();
        userPageSearch.setName(search);
        userPageSearch.setRole(User.Role.SUPER_ADMIN);
        userPageSearch.setUserId(ContextUtil.getCurUser().getId());

        return $(userService.usersInPage(userPageSearch, pageIndex, pageSize));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(User user){
        return $(userService.save(user)) ;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public String update(User user){
        return $(userService.update(user)) ;
    }

    @RequestMapping(value = "change/password", method = RequestMethod.PUT)
    @ResponseBody
    public RespData<Boolean> changePwd(User user, HttpServletRequest request){
        User curUser = ContextUtil.getCurUser(request);
        if(curUser != null){
            user.setId(curUser.getId());
        }
        RespData<Boolean> data = userService.changePassword(user);
        if(data.data){
            //删除当前用户Token信息
            ContextUtil.removeCurUser(request);
        }
        return  data;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable String id){
        return $(userService.deleteById(id)) ;
    }

    @RequestMapping(value = "deleteAll/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAll(@PathVariable String ids){
        String[] split = ids.split(",");
        List<String> idList = Arrays.asList(split);
        return $(userService.deleteInIds(idList)) ;
    }

}
