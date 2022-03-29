package com.nnk.springboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j(topic = "CONTROLLER_LOGGER")
public class LogInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("URI: {} | Request: {} | Response: {}", request.getRequestURI(), request.getMethod(), request.getRequestURI());
        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }
}
