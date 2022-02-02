package com.flex83.app.context;

import com.flex83.app.entities.TenantDatabaseConfig;

public final class RequestContext {
    public static ThreadLocal<String> _currentTenant = new ThreadLocal();
    public static ThreadLocal<TenantDatabaseConfig> _mongoConfiguration = new ThreadLocal();
}
