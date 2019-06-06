package fr.eni.projet_encheres.ihm.users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(
        urlPatterns = {
                "/user/*",
                "/updateProfile",
                "/deleteProfile",
                "/postAuction"
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
            HttpSession session = httpRequest.getSession();
            if (httpRequest.getQueryString() != null) {
                session.setAttribute("uriAndParamsRequested", httpRequest.getRequestURI() + "?" + httpRequest.getQueryString());
            } else {
                session.setAttribute("uriAndParamsRequested", httpRequest.getRequestURI());
            }
            session.setAttribute("mustBeLogged", "true");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
