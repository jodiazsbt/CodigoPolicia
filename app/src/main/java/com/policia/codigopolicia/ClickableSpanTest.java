package com.policia.codigopolicia;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.hyphen.SqueezeHyphenator;
import com.bluejamesbond.text.style.JustifiedSpan;
import com.bluejamesbond.text.util.ArticleBuilder;

/**
 * Created by 1085253556 on 4/01/2018.
 */

public class ClickableSpanTest extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DocumentView documentView = addDocumentView(new ArticleBuilder().append("Healthcare workers returning to New York or New Jersey after treating Ebola patients in West Africa will be placed under a mandatory quarantine, officials announced Friday, one day after a Doctors Without Borders doctor was diagnosed with the virus in New York City. Illinois announced a similar policy Saturday, meaning it will be enforced in states with three of the five airports through which passengers traveling from the Ebola-stricken West African countries must enter the United States.",
                true, new RelativeSizeSpan(1f), new JustifiedSpan())
                .append("N.J. Gov. Chris Christie and N.Y. Gov. Andrew Cuomo made the announcement as part of a broader procedural plan to help protect the densely packed, highly populated area from any further spread of the disease. ",
                        true, new RelativeSizeSpan(0.8f), new JustifiedSpan(), new MyQuoteSpan(0xFFFFC801))
                .append("“Since taking office, I have erred on the side of caution when it comes to the safety and protection of New Yorkers, and the current situation regarding Ebola will be no different,” Gov. Cuomo said. “The steps New York and New Jersey are taking today will strengthen our safeguards to protect our residents against this disease and help ensure those that may be infected by Ebola are treated with the highest precautions.”",
                        true, new RelativeSizeSpan(1f), new JustifiedSpan(), new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Toast.makeText(ClickableSpanTest.this, "Clicked", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(Color.parseColor("#ff05c5cf"));
                                ds.setUnderlineText(true);
                            }
                        })
                .append("New York and New Jersey state health department staff will be present on the ground at John F. Kennedy International Airport in New York and Newark Liberty Airport in New Jersey. In addition to implementing the mandatory quarantine of health care workers and others who had direct contact with Ebola patients, health department officials in each state will determine whether others should travelers should be hospitalized or quarantined.",
                        true, new RelativeSizeSpan(1f), new JustifiedSpan())
                .append("“The announcements mark a dramatic escalation in measures designed to prevent the spread of Ebola in the United States. Previously, only individuals with symptoms of Ebola would be quarantined upon entry to the U.S. under a federal rule from the Centers for Diseases Control and the Department of Homeland Security.”",
                        true, new RelativeSizeSpan(1f), new JustifiedSpan()), DocumentView.FORMATTED_TEXT);

        documentView.getDocumentLayoutParams().setHyphenator(SqueezeHyphenator.getInstance());
        documentView.getDocumentLayoutParams().setHyphenated(true);
    }
}
