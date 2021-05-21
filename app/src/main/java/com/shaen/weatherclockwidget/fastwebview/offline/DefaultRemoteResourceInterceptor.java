package com.shaen.weatherclockwidget.fastwebview.offline;

import android.content.Context;

import com.shaen.weatherclockwidget.fastwebview.WebResource;
import com.shaen.weatherclockwidget.fastwebview.loader.OkHttpResourceLoader;
import com.shaen.weatherclockwidget.fastwebview.loader.ResourceLoader;
import com.shaen.weatherclockwidget.fastwebview.loader.SourceRequest;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public class DefaultRemoteResourceInterceptor implements ResourceInterceptor {

    private ResourceLoader mResourceLoader;

    DefaultRemoteResourceInterceptor(Context context) {
        mResourceLoader = new OkHttpResourceLoader(context);
    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        SourceRequest sourceRequest = new SourceRequest(request, true);
        WebResource resource = mResourceLoader.getResource(sourceRequest);
        if (resource != null) {
            return resource;
        }
        return chain.process(request);
    }
}
