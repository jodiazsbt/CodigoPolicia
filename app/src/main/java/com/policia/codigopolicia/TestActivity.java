package com.policia.codigopolicia;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;

public class TestActivity extends Activity {

    public String testName;
    private boolean debugging = false;
    private int cacheConfig = 0;

    protected int getContentView() {
        return R.layout.test_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        testName = splitCamelCase(getClass().getSimpleName());

        TextView titleBar = ((TextView) findViewById(R.id.titlebar));

        if (titleBar != null) {
            titleBar.setText(testName);
        }
    }

    public DocumentView addDocumentView(CharSequence article, int type, boolean rtl) {
        final DocumentView documentView = new DocumentView(this, type);
        documentView.getDocumentLayoutParams().setTextColor(0xffffffff);
        documentView.getDocumentLayoutParams().setTextTypeface(Typeface.DEFAULT);
        documentView.getDocumentLayoutParams().setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        documentView.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        documentView.getDocumentLayoutParams().setInsetPaddingLeft(30f);
        documentView.getDocumentLayoutParams().setInsetPaddingRight(30f);
        documentView.getDocumentLayoutParams().setInsetPaddingTop(30f);
        documentView.getDocumentLayoutParams().setInsetPaddingBottom(30f);
        documentView.getDocumentLayoutParams().setLineHeightMultiplier(1f);
        documentView.getDocumentLayoutParams().setReverse(rtl);
        documentView.getDocumentLayoutParams().setDebugging(debugging);
        documentView.setText(article);
        documentView.setProgressBar((ProgressBar) findViewById(R.id.progressBar));
        documentView.setFadeInDuration(800);
        documentView.setFadeInAnimationStepDelay(30);
        documentView.setFadeInTween(new DocumentView.ITween() {
            @Override
            public float get(float t, float b, float c, float d) {
                return c * (t /= d) * t * t + b;
            }
        });

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.addView(documentView);

        LinearLayout articleList = (LinearLayout) findViewById(R.id.articleList);
        articleList.addView(linearLayout);

        debugging = documentView.getDocumentLayoutParams().isDebugging();
        cacheConfig = documentView.getCacheConfig().getId();

        final TextView debugButton = (TextView) findViewById(R.id.debugButton);

        if (debugButton != null) {
            debugButton.setText((debugging ? "DISABLE" : "ENABLE") + " DEBUG");
            debugButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    debugging = !debugging;
                    debugButton.setText((debugging ? "DISABLE" : "ENABLE") + " DEBUG");
                    documentView.getDocumentLayoutParams().setDebugging(debugging);
                }
            });
        }

        final TextView cacheButton = (TextView) findViewById(R.id.cacheButton);
        final Toast cacheConfigToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        if (cacheButton != null) {
            cacheConfigToast.setText("Activated " + documentView.getCacheConfig().name());
            cacheConfigToast.show();
            cacheButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cacheConfig = (cacheConfig + 1) % 5;
                    DocumentView.CacheConfig newCacheConfig = DocumentView.CacheConfig.getById(cacheConfig);
                    cacheConfigToast.setText("Activated " + newCacheConfig.name());
                    cacheConfigToast.show();
                    documentView.setCacheConfig(newCacheConfig);
                    documentView.destroyCache();
                    documentView.invalidate();
                }
            });
        }
        return documentView;
    }

    public DocumentView addDocumentView(CharSequence article, int type) {
        return addDocumentView(article, type, false);
    }

    /*
     * Refer to http://stackoverflow.com/a/2560017/1100536
     */
    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
