package com.shaen.weatherclockwidget.fastwebview.offline;

import com.shaen.weatherclockwidget.fastwebview.WebResource;

/**
 * Created by Ryan
 * at 2019/9/27
 */
public interface ResourceInterceptor {

    WebResource load(Chain chain);

}
