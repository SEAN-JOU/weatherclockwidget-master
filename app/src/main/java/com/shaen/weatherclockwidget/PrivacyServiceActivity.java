package com.shaen.weatherclockwidget;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shaen.weatherclockwidget.fastwebview.FastWebView;
import com.shaen.weatherclockwidget.fastwebview.WebResource;
import com.shaen.weatherclockwidget.fastwebview.config.CacheConfig;
import com.shaen.weatherclockwidget.fastwebview.config.DefaultMimeTypeFilter;
import com.shaen.weatherclockwidget.fastwebview.config.FastCacheMode;
import com.shaen.weatherclockwidget.fastwebview.cookie.CookieInterceptor;
import com.shaen.weatherclockwidget.fastwebview.cookie.CookieStrategy;
import com.shaen.weatherclockwidget.fastwebview.cookie.FastCookieManager;
import com.shaen.weatherclockwidget.fastwebview.offline.Chain;
import com.shaen.weatherclockwidget.fastwebview.offline.ResourceInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Cookie;
import okhttp3.HttpUrl;



public class PrivacyServiceActivity extends AppCompatActivity {

    FastWebView wv;
    WebSettings webSettings;
    EditText value1,value2,value3;
    Button save,copy1,copy2,copy3;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mSharedPreferences = PrivacyServiceActivity.this.getSharedPreferences("com.shaen.weatherclockwidget", MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        initView();
    }

    protected int setLayoutId() {
        return R.layout.activity_privacy_policy;
    }

    protected void initView() {

        FastWebView.setDebug(true);
        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        value3 = findViewById(R.id.value3);
        copy1 = findViewById(R.id.copy1);
        copy2 = findViewById(R.id.copy2);
        copy3 = findViewById(R.id.copy3);
        save = findViewById(R.id.save);
        copy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", value1.getText().toString());
                manager.setPrimaryClip(clipData);
            }
        });

        copy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", value2.getText().toString());
                manager.setPrimaryClip(clipData);
            }
        });

        copy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", value3.getText().toString());
                manager.setPrimaryClip(clipData);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("value1", value1.getText().toString());
                editor.putString("value2", value2.getText().toString());
                editor.putString("value3", value3.getText().toString());
                editor.apply();
                Toast.makeText(PrivacyServiceActivity.this,"Save Success",Toast.LENGTH_SHORT).show();
            }
        });
        value1.setText(mSharedPreferences.getString("value1",""));
        value2.setText(mSharedPreferences.getString("value2",""));
        value3.setText(mSharedPreferences.getString("value3",""));

        wv = (FastWebView) findViewById(R.id.webView);
        wv.setWebViewClient(new MyWebClient());
        wv.setWebChromeClient(new MyWebChromeClient());
        webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setBlockNetworkImage(true);
        try{
            url = getIntent().getStringExtra("URL1");
            wv.loadUrl(url);
        }catch (Exception e){
        }
    }

    @SuppressLint("JavascriptInterface")
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(wv, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        CacheConfig config = new CacheConfig.Builder(this)
                .setCacheDir(getCacheDir() + File.separator + "custom")
                .setExtensionFilter(new CustomMimeTypeFilter())
                .build();
        wv.setCacheMode(FastCacheMode.FORCE, config);
        wv.addResourceInterceptor(new ResourceInterceptor() {
            @Override
            public WebResource load(Chain chain) {
                return chain.process(chain.getRequest());
            }
        });
        wv.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        Map<String, String> headers = new HashMap<>();
        headers.put("custom", "test");

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除旧的[可以省略]
        cookieManager.setCookie(url, "custom=12345678910;");
        CookieSyncManager.getInstance().sync();

        FastCookieManager fastCookieManager = wv.getFastCookieManager();
        fastCookieManager.setCookieStrategy(CookieStrategy.PERSISTENT);
        fastCookieManager.setRequestCookieInterceptor(new CookieInterceptor() {
            @Override
            public List<Cookie> newCookies(HttpUrl url, List<Cookie> originCookies) {

                return originCookies;
            }
        });
        fastCookieManager.setResponseCookieInterceptor(new CookieInterceptor() {
            @Override
            public List<Cookie> newCookies(HttpUrl url, List<Cookie> originCookies) {
                return originCookies;
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (wv != null) {
            wv.clearCache(true);
            wv.destroy();
        }
    }

    public class MyWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("aaaaaa2",url);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            wv.loadUrl(request.getUrl().toString());
            return true;
        }
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.d("aaaaaa3",request.getUrl().toString());
            return super.shouldInterceptRequest(view, request);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("aaaaaa4",url);
            view.getSettings().setBlockNetworkImage(false);
            view.loadUrl("javascript:android.sendResource(JSON.stringify(window.performance.timing))");

            if (view.getContentHeight() > 0) {
                try {
                    view.loadUrl("javascript:window.java_obj.getSource('<head>'+" +
                            "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    view.getOriginalUrl();
                } catch (Exception e) {
                    wv.loadUrl(getIntent().getStringExtra("URL1"));
                }
            } else {
                wv.loadUrl(getIntent().getStringExtra("URL1"));
            }
        }
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getSource(String html) {
            wv.clearCache(true);
            wv.loadUrl(html);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class CustomMimeTypeFilter extends DefaultMimeTypeFilter {
        CustomMimeTypeFilter() {
            addMimeType("text/html");
        }
    }
}
