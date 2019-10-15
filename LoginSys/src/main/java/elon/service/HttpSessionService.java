package elon.service;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Configuration
public class HttpSessionService {

    public static void sessionSetAttr(HttpSession httpSession,String key,Object value){
        Object sessionAttr = httpSession.getAttribute(key);
        if(sessionAttr == null){
            httpSession.setAttribute(key,value);
        }
    }

    public static String sessionGetId(HttpSession httpSession){
        String sessionID = httpSession.getId();
        return sessionID;
    }
}
