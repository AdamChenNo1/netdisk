package elon.controller;

import elon.service.HttpSessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class PageController {
    @Value("${fileSource.baseUrl}")
    String fileSource;

    @CrossOrigin
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("welcome.html").forward(request, response);
    }

    @RequestMapping(value = "/fileService",method = RequestMethod.GET)
    public String fileService(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        String user = (String) httpSession.getAttribute("user");
        String cval =  HttpSessionService.sessionGetId(httpSession) + "-" + user;
        redirectAttributes.addAttribute("cname", "X-Token");
        redirectAttributes.addAttribute("cval",cval);
        String url = fileSource + "netdisk/cookie";
        return "redirect:" + url;
    }
}
