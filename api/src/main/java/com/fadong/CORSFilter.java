package com.fadong;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 7. 7.
 */
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.addHeader("Access-Control-Max-Age", Integer.toString(1800));
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,Accept,Cookie,Cookie__");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {

    }
}
