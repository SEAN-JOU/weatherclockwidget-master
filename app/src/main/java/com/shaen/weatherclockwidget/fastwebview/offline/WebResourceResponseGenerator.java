package com.shaen.weatherclockwidget.fastwebview.offline;

import android.webkit.WebResourceResponse;

import com.shaen.weatherclockwidget.fastwebview.WebResource;

/**
 * Created by Ryan
 * at 2019/10/8
 */
public interface WebResourceResponseGenerator {

    WebResourceResponse generate(WebResource resource, String urlMime);

}
