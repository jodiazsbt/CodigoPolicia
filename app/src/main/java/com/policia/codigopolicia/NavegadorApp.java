package com.policia.codigopolicia;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * Created by 1085253556 on 4/12/2017.
 */

public class NavegadorApp extends Fragment {

    WebView webViewFuncionario;

    private final String currentUrl;

    public NavegadorApp() {
        currentUrl = "https://srvpsi.policia.gov.co/PSC/frm_cnp_consulta.aspx";
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_funcionario, container, false);
        webViewFuncionario = (WebView) v.findViewById(R.id.webViewFuncionario);
        webViewFuncionario.loadUrl(currentUrl);

        // Enable Javascript
        WebSettings webSettings = webViewFuncionario.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            webSettings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webViewFuncionario, true);

        }
        // Force links and redirects to open in the WebView instead of in a browser
        webViewFuncionario.setWebViewClient(new WebViewClient());

        return v;
    }
}
