package WebService;


import pojo.Msg;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;

@Path("cookie")
public class TokenService {

    @GET
    public void addToken(@QueryParam("cname") String cname,
                         @QueryParam("cval") String cval,
                             @Context UriInfo ui,
                             @Context HttpServletRequest req,
                             @Context HttpServletResponse res) throws IOException {
        Cookie cookie = new Cookie(cname,cval);
        cookie.setPath("/");
        cookie.setMaxAge(600);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
        String baseUrl = ui.getBaseUri().toString();
        String path = req.getServletPath();
        String redirectPage = baseUrl.substring(0,baseUrl.indexOf(path));
        res.sendRedirect(redirectPage);
    }

    @DELETE
    public Msg delToken(@QueryParam("cname") String cname,
                        @Context HttpServletRequest req,
                        @Context HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        if (null == cookies) {
            return Msg.failed().add("info", "没有cookie");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cname)) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);// 立即销毁cookie
                    res.addCookie(cookie);
                    break;
                }
            }
            return Msg.sucess();
        }
    }
}
