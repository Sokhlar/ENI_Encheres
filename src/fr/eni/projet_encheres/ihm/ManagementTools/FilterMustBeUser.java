package fr.eni.projet_encheres.ihm.ManagementTools;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(
        urlPatterns = {
                "/user/*"
        },
        dispatcherTypes = {
                DispatcherType.ERROR,
                DispatcherType.FORWARD,
                DispatcherType.INCLUDE,
                DispatcherType.REQUEST
        }
)
public class FilterMustBeUser implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        if (httpRequest.isUserInRole("basic_user")) {
            chain.doFilter(req, resp);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
