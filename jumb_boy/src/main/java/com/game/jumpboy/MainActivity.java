package com.game.jumpboy;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class MainActivity extends AppCompatActivity {
    public static @Nullable Action action;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    boolean intCanShow = false;
    boolean bannerCanShow = false;

    boolean ispro = true;
    SharedPreferences sharedpreferences;

    WebView browser;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jump_boy);

        browser = findViewById(R.id.webView);
        browser.setWebChromeClient(new WebChromeClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setMediaPlaybackRequiresUserGesture(false);
        browser.getSettings().setAllowFileAccessFromFileURLs(true);
        browser.getSettings().setAllowUniversalAccessFromFileURLs(true);
        browser.addJavascriptInterface(new WebAppInterface(this), "Android");
        browser.loadUrl("file:///android_asset/js/index.html");
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                browser.setVisibility(View.GONE);
                if (!urlx.contains("http")) {
                    viewx.loadUrl(urlx);
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlx));
                    startActivity(intent);
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                browser.setVisibility(View.VISIBLE);
                if (ispro) {
                    browser.loadUrl("javascript:awebapp.itspro(1)");
                }
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
            }

        });

        MobileAds.initialize(this, initializationStatus -> {
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadAd();

    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                "ca-app-pub-3940256099942544/1033173712",
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                        MainActivity.this.mInterstitialAd = interstitialAd;

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                MainActivity.this.mInterstitialAd = null;
                                loadAd();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                MainActivity.this.mInterstitialAd = null;
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                }
        );


        mAdView = findViewById(R.id.adView);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }


            @Override
            public void onAdClosed() {
            }
        });


        sharedpreferences = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
//        if (sharedpreferences.contains("ispro")) {
//            ispro = sharedpreferences.getBoolean("ispro", false);
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ispro) {
                    initAd();
                    browser.loadUrl("javascript:awebapp.itspro(0)");
                } else {
                    browser.loadUrl("javascript:awebapp.itspro(1)");
                }

            }
        }, 5000);
    }

    public void initAd() {
        if (intCanShow) {
            if (mInterstitialAd != null) {
                showInterstitial();
            }
        }
        intCanShow = false;

        if (bannerCanShow) {
            showBanner();
        }
        bannerCanShow = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initAd();
            }
        }, 1000);
    }

    public void showInterstitial() {
        mInterstitialAd.show(this);

    }

    public void showBanner() {
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setVisibility(View.VISIBLE);
    }

    public void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public void launchSharer(String msg, String titlemsg) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titlemsg);
            String shareMessage = msg + "\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
        }
    }

    public void launchTextSharer(String msg, String titlemsg) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titlemsg);
            String shareMessage = msg;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
        }
    }

    public class WebAppInterface {

        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showAd() {
            intCanShow = true;
        }

        @JavascriptInterface
        public void getLastScore(int score) {
            if (action != null) {
                action.getScore(score);
            }
        }

        @JavascriptInterface
        public void showAndroidBanner() {
//            bannerCanShow = true;
        }

        @JavascriptInterface
        public void removeAds() {
            orderRemoveAds();
        }

        @JavascriptInterface
        public void showAlert(String alerttext) {
            Toast.makeText(getApplicationContext(), alerttext, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void portrait() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        @JavascriptInterface
        public void landscape() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @JavascriptInterface
        public void rateThisApp() {
            launchMarket();
        }

        @JavascriptInterface
        public void shareText(String txt, String title) {
            launchTextSharer(txt, title);
        }

        @JavascriptInterface
        public void shareThisApp(String txt, String title) {
            launchSharer(txt, title);
        }

    }

    public void orderRemoveAds() {
//        boolean isAvailable = BillingProcessor.isIabServiceAvailable(MainActivity.this);
//        if (isAvailable) {
//            bp.purchase(MainActivity.this, "noads");
//        }
//        if (action != null) {
//            action.orderRemoveAds();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView browser = findViewById(R.id.webView);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        } else {

        }
        return super.onKeyDown(keyCode, event);
    }

}