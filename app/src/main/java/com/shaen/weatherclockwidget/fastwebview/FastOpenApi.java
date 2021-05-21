package com.shaen.weatherclockwidget.fastwebview;

import com.shaen.weatherclockwidget.fastwebview.config.CacheConfig;
import com.shaen.weatherclockwidget.fastwebview.config.FastCacheMode;
import com.shaen.weatherclockwidget.fastwebview.offline.ResourceInterceptor;

/**
 * Created by Ryan
 * at 2019/11/1
 */
public interface FastOpenApi {

    void setCacheMode(FastCacheMode mode, CacheConfig cacheConfig);

    void addResourceInterceptor(ResourceInterceptor interceptor);
}

