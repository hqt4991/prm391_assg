package se04869.vn.edu.fpt.prm391_assg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class WebActivity extends Activity {

    WebView wvb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        wvb = findViewById(R.id.wvb);

        wvb.getSettings().setLoadsImagesAutomatically(true);
        wvb.getSettings().setJavaScriptEnabled(true);
        wvb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvb.loadUrl(getIntent().getExtras().getString("url"));
    }
}
