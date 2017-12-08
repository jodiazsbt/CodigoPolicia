package com.policia.codigopolicia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 1085253556 on 4/12/2017.
 */

public class IdentificacionFuncionario extends Fragment {

    WebView webViewFuncionario;
    Activity activity ;
    ProgressDialog progDailog;

    private final String currentUrl;

    public IdentificacionFuncionario() {
        currentUrl = "https://srvpsi.policia.gov.co/PSC/frm_cnp_consulta.aspx";
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        View x = inflater.inflate(R.layout.fragment_funcionario, container, false);
        webViewFuncionario = (WebView) x.findViewById(R.id.webViewFuncionario);

        /*
        progDailog = ProgressDialog.show(activity, "Cargando","Un momento por favor...", true);
        progDailog.setCancelable(false);
        */

        String url = currentUrl;
        // probably a good idea to check it's not null, to avoid these situations:
        if (webViewFuncionario != null) {
            webViewFuncionario.getSettings().setJavaScriptEnabled(true);
            webViewFuncionario.getSettings().setLoadWithOverviewMode(true);
            webViewFuncionario.getSettings().setUseWideViewPort(true);
            webViewFuncionario.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //progDailog.show();
                    if (url.equals(currentUrl)) {
                        view.loadUrl(url);
                    }
                    return true;
                }
                @Override
                public void onPageFinished(WebView view, final String url) {
                    //progDailog.dismiss();
                }
            });

            webViewFuncionario.loadUrl(currentUrl);

        }

        return x;
    }
}
