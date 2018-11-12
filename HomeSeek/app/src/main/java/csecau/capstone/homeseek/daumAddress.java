package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.os.Handler;

public class daumAddress extends AppCompatActivity {
    private WebView browser;
    private TextView daum_result;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daum);
        daum_result = (TextView)findViewById(R.id.daum_result);

        init_webView();

        handler = new Handler();
    }

    public void init_webView(){
        browser = (WebView)findViewById(R.id.daum_webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.addJavascriptInterface(new AndroidBridge(), "TestApp");
        browser.setWebChromeClient(new WebChromeClient());
        browser.loadUrl("http://dozonexx.dothome.co.kr/daum_address.php");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    daum_result.setText(String.format("%s %s", arg2, arg3));
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("data", daum_result.getText().toString());
                    setResult(RESULT_OK, returnIntent);
                    finish();

                    init_webView();
                }
            });
        }
    }
}