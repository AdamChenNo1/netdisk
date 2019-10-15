package Filter;

import Service.PropertiesFileUtil;
import Service.RedisSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.*;
import java.util.Map;

@PreMatching
public class AuthRequestFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRequestFilter.class);

    private String userSessionKey;
    private String checkingNeedlessUrls;

    @Context
    HttpServletRequest req;

    @Context
    HttpServletResponse res;

    public void filter(ContainerRequestContext crc) {
        Map map = PropertiesFileUtil.getProperties("RequestAuth.properties");
        userSessionKey = (String) map.get("filter.userSessionKey");
        checkingNeedlessUrls = (String) map.get("filter.checkingNeedlessUrls");

        UriInfo ui = crc.getUriInfo();
        String requestURI = ui.getPath();


        Map<String, Cookie> cookies = crc.getCookies();

        if(checkingNeedlessUrls.contains(requestURI)){
            return;
        }else if(cookies.containsKey("X-Token")){
            String key = userSessionKey;
            Cookie cookie = cookies.get("X-Token");
            String Token = cookie.getValue();
            String token = Token.substring(0,Token.lastIndexOf('-'));
            String value = RedisSessionService.getAttr(token,key);
            if(value != null){
                UriBuilder ub = ui.getRequestUriBuilder();
                ub.queryParam(key,value);
                crc.setRequestUri(ui.getBaseUri(),ub.build());
            }
        }
    }
}
