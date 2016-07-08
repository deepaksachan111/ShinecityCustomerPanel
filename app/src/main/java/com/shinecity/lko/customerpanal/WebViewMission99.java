package com.shinecity.lko.customerpanal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * Created by QServer on 6/10/2016.
 */
public class WebViewMission99 extends Activity {
    private WebView webView;

    private LinearLayout linear_nointernet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_mission99);



        webView = (WebView) findViewById(R.id.webView1);

        linear_nointernet =(LinearLayout)findViewById(R.id.linear_nointernet);

        NetworkConnectionChecker  connectionchecker = new NetworkConnectionChecker(this);
        boolean isInternetPresent = connectionchecker.isConnectingToInternet();

        if (isInternetPresent) {

            SharedPreferences preferences = getSharedPreferences("MY",0);
            String memid = preferences.getString("FK","");
            String weburl = "http://crm.shinecityinfra.com/SocialNetwork/Timeline.aspx?MemID="+memid;
            startWebView(weburl);

        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Connection Failed !")
                    .setMessage("Unable to connect.Please check your network settings.")
                    .setPositiveButton("OK", null).show();
            linear_nointernet.setVisibility(View.VISIBLE);

        }

    }



    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // progressDialog.dismiss();
                return true;

            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(WebViewMission99.this);
                    progressDialog.getWindow().setGravity(Gravity.CENTER);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        // progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
	    /*
	    webView.getSettings().setLoadWithOverviewMode(true);
	    webView.getSettings().setUseWideViewPort(true);
	    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    webView.setScrollbarFadingEnabled(false);
	    webView.getSettings().setBuiltInZoomControls(true);
	    */

	    /*
	     String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
	     */

        //Load url in webview
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

}
