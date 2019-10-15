package Filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class HttpFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
        init();
    }

    protected void init(){

    }

    public FilterConfig getFilterConfig(){
        return this.filterConfig;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException,ServletException{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        doFilter(request,response,chain);
    }

    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException,ServletException;

    public void destroy(){

    }
}
