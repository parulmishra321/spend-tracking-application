package com.spendTracker.app.context;

import com.spendTracker.app.entities.TenantDatabaseConfig;

public final class RequestContext {
    public static ThreadLocal<String> _currentTenant = new ThreadLocal();
    public static ThreadLocal<TenantDatabaseConfig> _mongoConfiguration = new ThreadLocal();
}
