package elon.controller;

/*
处理用户增删查改请求
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import elon.pojo.Msg;
import elon.pojo.User;
import elon.service.HttpSessionService;
import elon.service.LoginService;
import elon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性
public class UserController {

    @Autowired
    UserService userService;




    /*
    用户登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Msg login(@RequestBody User user, HttpServletRequest request, HttpSession httpSession){
        boolean valid = userService.validateUser(user);
        LoginService loginService = new LoginService(user.getName(),httpSession);
        if(!valid) {
            //校验失败，返回失败信息
            return Msg.failed().add("error", "用户名与密码不一致");
//        }else if(!loginService.isLogin()){
//            //账户已登录
//            return Msg.failed().add("error","用户已登录，请先注销或15分钟后再尝试");
//        }
        }else {
            loginService.login();
            Map map = new HashMap();
            map.put("url","fileService");
            map.put("userName",user.getName());
            return Msg.sucess().add("index",map);
        }
    }

    /*
    用户注销登录
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public Msg logout(@RequestParam String user,HttpSession httpSession){
        LoginService loginService = new LoginService(user,httpSession);
        loginService.logout();
        return Msg.sucess().add("index","/");
    }

    /*
    用户注册
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveUser(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            //校验失败，返回失败信息
            Map<String,Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError:errors){
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.failed().add("errorFields",map);
        }else {
            userService.saveUser(user);
            return Msg.sucess();
        }
    }

    /*
    用户名存在校验
    */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkUser(@RequestParam("name") String name){
        boolean existence = userService.checkUser(name);
        if(existence){
            return Msg.sucess();
        }else{
            return Msg.failed();
        }
    }

}
