package com.policia.codigopolicia;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class GenericWebviewActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_webview);

        Bundle b = getIntent().getExtras();
        String url = ""; // or other values
        if (b != null)
            url = b.getString("url");

        final Activity activity = this;
        mWebView = new WebView(this);
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                activity.setTitle(title);
            }

            /*
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(GenericWebviewActivity.this, error.getDescription(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(GenericWebviewActivity.this, "Tenemos problemas para cargar esta página en la aplicación", Toast.LENGTH_SHORT).show();
            }*/
        });

        this.setContentView(mWebView);
    }
}
