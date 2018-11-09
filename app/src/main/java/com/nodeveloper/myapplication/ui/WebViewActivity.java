package com.nodeveloper.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nodeveloper.myapplication.R;

public class WebViewActivity extends BaseActivity {
    private ProgressBar webview_progress;
    private WebView webview_page;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }

    private void initView() {
        webview_progress = findViewById(R.id.webview_progress);
        webview_page = findViewById(R.id.webview_page);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");

        getSupportActionBar().setTitle(title);

        webview_page.getSettings().setJavaScriptEnabled(true);

        webview_page.getSettings().setSupportZoom(true);

        webview_page.getSettings().setBuiltInZoomControls(true);

        webview_page.setWebChromeClient(new WebChromeClient());

        webview_page.loadUrl(url);

        webview_page.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                webview_progress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

}
