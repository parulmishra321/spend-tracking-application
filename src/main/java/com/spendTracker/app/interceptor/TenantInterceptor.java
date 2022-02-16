package com.spendTracker.app.interceptor;

import com.spendTracker.app.constant.ApplicationConstants;
import com.spendTracker.app.context.RequestContext;
import com.spendTracker.app.entities.TenantDatabaseConfig;
import com.spendTracker.app.repository.TenantDatabaseConfigRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LogManager.getLogger(TenantInterceptor.class);
    @Autowired
    private TenantDatabaseConfigRepository tenantDatabaseConfigRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String tenantName = String.valueOf(request.getHeader(ApplicationConstants.X_TENANT_NAME));
        RequestContext._currentTenant.set(tenantName);
        RequestContext._mongoConfiguration.set(getConfiguration(tenantName));
        return super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        RequestContext._currentTenant.remove();
        RequestContext._mongoConfiguration.remove();
        ThreadContext.clearMap();
        ThreadContext.clearAll();
    }

    private TenantDatabaseConfig getConfiguration(String tenant) {
        return tenantDatabaseConfigRepository.getDatabaseConfigByTenant(tenant);
    }
}
