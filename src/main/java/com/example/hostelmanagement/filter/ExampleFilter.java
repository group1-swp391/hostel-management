package com.example.hostelmanagement.filter;

import com.example.hostelmanagement.models.AccountSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class ExampleFilter implements Filter{
    private static final Logger logger = LoggerFactory.getLogger(ExampleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("filter:"+ ((HttpServletRequest)servletRequest).getRequestURL());

        String path =  ((HttpServletRequest) servletRequest).getServletPath();
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        AccountSession accSession = (AccountSession) session.getAttribute("accSession");
        if (path.contains("/api/v1/Admin/")){
            if(accSession == null || accSession.getRoleid() != 1) {
                response.sendError(403, "Not Admin");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}