package Filter;

import Service.RedisSessionService;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends HttpFilter {
    private FilterConfig filterConfig;
    private ServletContext servletContext;
    private String userSessionKey;
    private String redirectPage;
    private String checkingNeedlessUrls;

    protected void init(){
        filterConfig = this.getFilterConfig();
        servletContext = filterConfig.getServletContext();
        userSessionKey = servletContext.getInitParameter("userSessionKey");
        redirectPage = servletContext.getInitParameter("redirectPage");
        checkingNeedlessUrls = servletContext.getInitParameter("checkingNeedlessUrls");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,FilterChain chain) throws IOException, ServletException {
        String path = request.getPathInfo();
        String token = null;
        if(path!=null&&this.checkingNeedlessUrls.contains(path)){
            chain.doFilter(request,response);
            return;
        }else{
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for (Cookie cookie : cookies){
                    System.out.println("cookie名称：" + cookie.getName());
                    switch(cookie.getName()){
                        case "X-Token":
                            token = cookie.getValue();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        if(token!=null){
            String key = userSessionKey;
            String Token = token.substring(0,token.lastIndexOf('-'));
            String user = token.substring(token.lastIndexOf('-')+1);
            String value = RedisSessionService.getAttr(Token,key);
            if(value != null && value.equals(user)){
                chain.doFilter(request,response);
                return;

            }else if(value == null || !value.equals(user)){
                try {
                    redirectTo(request,response,redirectPage);
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            try {
                redirectTo(request,response,redirectPage);
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void redirectTo(HttpServletRequest request,HttpServletResponse response,String redirectUrl) throws IOException {

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            response.setHeader("redirection", "noAuth");
            response.setHeader("loginPath",redirectUrl);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            response.sendRedirect(redirectUrl);
        }

    }
}
