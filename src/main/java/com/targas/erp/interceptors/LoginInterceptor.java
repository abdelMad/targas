package com.targas.erp.interceptors;

import com.targas.erp.models.Utilisateur;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (u == null) {
            System.out.println("Im in interceptor");
            response.sendRedirect("/login");
            return false;
        }
        System.out.println("Im in interceptor");
        return true;
    }
}
