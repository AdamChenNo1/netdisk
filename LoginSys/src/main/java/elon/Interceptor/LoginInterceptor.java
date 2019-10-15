package elon.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        boolean result = false;
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user");
        ServletContext servletContext = session.getServletContext();
        Map<String, Object> loginMap = (Map<String, Object>)servletContext.getAttribute("loginMap");
        boolean ss = user != null && loginMap.containsKey(user);
        if(ss){
            String sessionTime = String.valueOf(session.getCreationTime());
            String[] loginInfo = (String[]) loginMap.get(user);
            String onlineSessionTime = loginInfo[1];
            if(onlineSessionTime.equals(sessionTime)){
                result = true;
            }else {
                session.removeAttribute("user");
                httpServletResponse.sendRedirect("home");
            }
        }else {
            httpServletResponse.sendRedirect("home");
        }
        return result;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
