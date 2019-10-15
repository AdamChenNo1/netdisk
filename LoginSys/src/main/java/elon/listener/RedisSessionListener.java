package elon.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

public class RedisSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext sc = session.getServletContext();
        Map<String, Object> loginMap = (Map<String, Object>)sc.getAttribute("loginMap");
        String user = (String) session.getAttribute("user");

        if(user != null && loginMap!=null && loginMap.containsKey(user)){
            String[] loginInfo = (String[]) loginMap.get(user);
            String sessionTime = String.valueOf(session.getCreationTime());
            String onlineSessionTime = loginInfo[1];
            if(sessionTime.equals(onlineSessionTime)){
                loginMap.remove(user);
                sc.setAttribute("loginMap",loginMap);
            }
        }
    }
}
