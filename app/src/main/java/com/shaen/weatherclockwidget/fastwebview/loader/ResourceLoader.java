package com.shaen.weatherclockwidget.fastwebview.loader;

import com.shaen.weatherclockwidget.fastwebview.WebResource;

/**
 * Created by Ryan
 * 2018/2/7 下午7:53
 */
public interface ResourceLoader {

    WebResource getResource(SourceRequest request);

}



