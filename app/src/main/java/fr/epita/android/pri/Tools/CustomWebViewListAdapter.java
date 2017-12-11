package fr.epita.android.pri.Tools;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by sadekseridj on 11/12/2017.
 */

public class CustomWebViewListAdapter extends BaseAdapter {

    private ArrayList<String> url;
    private MainActivity context;
    View rowView;

    public CustomWebViewListAdapter(MainActivity context, ArrayList<String> url)
    {
        this.url = url;
        this.context = context;
    }

    @Override
    public int getCount() { return url.size(); }

    @Override
    public Object getItem(int i) { return url.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final String item = (String) this.getItem(i);
        final ViewHolder viewHolder;

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.webviewitem, viewGroup, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }
        else
        {
            rowView = view;
            viewHolder = (ViewHolder) view.getTag();
        }

        // configuration du webview pour l'utilisation du javascript
        WebSettings webSettings = viewHolder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // permet l'ouverture des fenêtres
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // Autorise le stockage des DOM (Document Object Model)
        webSettings.setDomStorageEnabled(true);

        viewHolder.webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //viewHolder.webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        viewHolder.webView.loadUrl(item);
        viewHolder.webView.setWebViewClient(new WebViewClient() {

            // permet l'affichage de la page lorsque tout est chargé@Override
         public void onPageFinished(WebView webView, String url) {
            viewHolder.webView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url)
            {
                webView.loadUrl(url);
                return true;
            }
        });

        return rowView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class ViewHolder {
        //final TextView textView;
        final WebView webView;

        public ViewHolder(final View rowView) {
          //textView = (TextView) rowView.findViewById(R.id.text_web_view);
            webView = (WebView) rowView.findViewById(R.id.web_view);
        }
    }
}
