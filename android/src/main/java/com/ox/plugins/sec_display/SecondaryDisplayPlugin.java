package com.ox.plugins.sec_display;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "SecondaryDisplay")
public class SecondaryDisplayPlugin extends Plugin {
    private SecondaryDisplayPresentation presentation;
    private DisplayManager displayManager;

    @PluginMethod
    public void initialize(PluginCall call) {
        // Ensure we're on the main thread for initialization
        getActivity().runOnUiThread(() -> {
            try {
                displayManager = (DisplayManager) getContext().getSystemService(Context.DISPLAY_SERVICE);
                setupDisplayListener();
                call.resolve();
            } catch (Exception e) {
                call.reject("Failed to initialize secondary display", e);
            }
        });
    }

    @PluginMethod
    public void showContent(PluginCall call) {
        String url = call.getString("url", "");
        getActivity().runOnUiThread(() -> {
            try {
                if (presentation != null) {
                    presentation.loadUrl(url);
                    call.resolve();
                } else {
                    call.reject("No secondary display available");
                }
            } catch (Exception e) {
                call.reject("Failed to show content", e);
            }
        });
    }

    @PluginMethod
    public void sendMessage(PluginCall call) {
        JSObject message = call.getObject("message");

        Log.println(Log.INFO, "m", String.valueOf(message));
        getActivity().runOnUiThread(() -> {
            try {
                if (presentation != null && presentation.getWebView() != null) {
                    // Send message to secondary display
                    String jsCommand = String.format(
                            "window.dispatchEvent(new CustomEvent('mainMessage', { detail: %s }))",
                            message
                    );

                    presentation.getWebView().evaluateJavascript(jsCommand, null);
                    call.resolve();
                } else {
                    call.reject("Secondary display not available");
                }
            } catch (Exception e) {
                call.reject("Failed to send message", e);
            }
        });
    }

    private void setupDisplayListener() {
        displayManager.registerDisplayListener(new DisplayManager.DisplayListener() {
            @Override
            public void onDisplayAdded(int displayId) {
                getActivity().runOnUiThread(() -> handleSecondaryDisplay());
            }

            @Override
            public void onDisplayRemoved(int displayId) {
                getActivity().runOnUiThread(() -> {
                    if (presentation != null) {
                        presentation.dismiss();
                        presentation = null;
                    }
                });
            }

            @Override
            public void onDisplayChanged(int displayId) {
                getActivity().runOnUiThread(() -> handleSecondaryDisplay());
            }
        }, null);

        // Check for existing displays
        handleSecondaryDisplay();
    }

    private void handleSecondaryDisplay() {
        Display[] displays = displayManager.getDisplays(
                DisplayManager.DISPLAY_CATEGORY_PRESENTATION
        );

        if (displays.length > 0 && presentation == null) {
            try {
                presentation = new SecondaryDisplayPresentation(getContext(), displays[0]);
                presentation.show();
            } catch (Exception e) {
                // Log error or notify through a callback if needed
                e.printStackTrace();
                throw e;
            }
        }
    }
}

class SecondaryDisplayPresentation extends android.app.Presentation {
    private WebView webView;

    public WebView getWebView() {
        return webView;
    }

    public SecondaryDisplayPresentation(Context context, Display display) {
        super(context, display);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create WebView on the UI thread
        webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        // For production builds
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        setContentView(webView);
    }

    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void dismiss() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.dismiss();
    }
}