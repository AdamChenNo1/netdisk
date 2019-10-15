package elon.service;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    String username;
    HttpSession httpSession;
    Map<String, Object> loginMap;
    ServletContext servletContext;

    public LoginService(String username,HttpSession httpSession){
        this.username = username;
        this.httpSession = httpSession;
        servletContext = httpSession.getServletContext();
        loginMap = (Map<String, Object>)servletContext.getAttribute("loginMap");
        if(loginMap == null){
            loginMap = new HashMap<String, Object>();
        }
    }

    public boolean isLogin(){
        boolean result = false;   //初次登录
        if(username != null && loginMap.containsKey(username)){
            String[] loginFeature = (String[])loginMap.get(username);
            String onlineSessionTime = loginFeature[1];
            String sessionTime = String.valueOf(httpSession.getCreationTime());
            if(onlineSessionTime.equals(sessionTime)){
                result = true;
            }
        }
        return result;
    }

    public void logout(){
        httpSession.removeAttribute("user");
        if(isLogin()){
            loginMap.remove(username);
            servletContext.setAttribute("loginMap",loginMap);
        }
    }

    public void login(){
        HttpSessionService.sessionSetAttr(httpSession,"user",username);
        String[] loginFeature = {httpSession.getId(), String.valueOf(httpSession.getCreationTime())};
        loginMap.put(username,loginFeature);
        servletContext.setAttribute("loginMap",loginMap);
    }
}
