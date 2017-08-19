package chroma.fiot.chroma;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        WebView myWebView = (WebView) findViewById(R.id.m_webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.loadUrl("file:///android_asset/test.html");
    }
}

class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void returnPaymentResult(String transactionRefId, String transactionStatus, String token) {
        Log.i(TAG, "IT WORKS");
        Log.d(TAG, "returnPaymentResult() called with: transactionRefId = [" + transactionRefId + "], transactionStatus = [" + transactionStatus + "], token = [" + token + "]");
    }

    @JavascriptInterface
    public void test () {
        Log.i(TAG, "test: ");
    }
}
