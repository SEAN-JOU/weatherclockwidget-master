package com.shaen.weatherclockwidget.fastwebview.offline;

import android.content.Context;
import android.text.TextUtils;

import com.shaen.weatherclockwidget.fastwebview.WebResource;
import com.shaen.weatherclockwidget.fastwebview.config.CacheConfig;
import com.shaen.weatherclockwidget.fastwebview.config.MimeTypeFilter;
import com.shaen.weatherclockwidget.fastwebview.loader.OkHttpResourceLoader;
import com.shaen.weatherclockwidget.fastwebview.loader.ResourceLoader;
import com.shaen.weatherclockwidget.fastwebview.loader.SourceRequest;


/**
 * Created by Ryan
 * at 2019/9/27
 */
public class ForceRemoteResourceInterceptor implements Destroyable, ResourceInterceptor {

    private ResourceLoader mResourceLoader;
    private MimeTypeFilter mMimeTypeFilter;

    ForceRemoteResourceInterceptor(Context context, CacheConfig cacheConfig) {
        mResourceLoader = new OkHttpResourceLoader(context);
        mMimeTypeFilter = cacheConfig != null ? cacheConfig.getFilter() : null;
    }

    @Override
    public WebResource load(Chain chain) {
        CacheRequest request = chain.getRequest();
        String mime = request.getMime();
        boolean isFilter;
        if (TextUtils.isEmpty(mime)) {
            isFilter = isFilterHtml();
        } else {
            isFilter = mMimeTypeFilter.isFilter(mime);
        }
        SourceRequest sourceRequest = new SourceRequest(request, isFilter);
        WebResource resource = mResourceLoader.getResource(sourceRequest);
        if (resource != null) {
            return resource;
        }
        return chain.process(request);
    }

    @Override
    public void destroy() {
        if (mMimeTypeFilter != null) {
            mMimeTypeFilter.clear();
        }
    }

    private boolean isFilterHtml() {
        return mMimeTypeFilter.isFilter("text/html");
    }
}
