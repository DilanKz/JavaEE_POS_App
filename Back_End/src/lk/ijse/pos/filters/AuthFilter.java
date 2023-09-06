package lk.ijse.pos.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",filterName = "B")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AUTH init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("AUTH init before");
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String auth = req.getHeader("Auth");

        if (auth != null && auth.equals("user=admin,pass=admins")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            res.setStatus(401);
        }
    }

    @Override
    public void destroy() {

    }
}
