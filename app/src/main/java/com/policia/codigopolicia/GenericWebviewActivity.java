package com.policia.codigopolicia;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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

        mWebView = new WebView(this);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
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
            }
        });

        this.setContentView(mWebView);
    }
}
