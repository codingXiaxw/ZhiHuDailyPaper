package com.example.codingboy.zhihupaper.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.domin.WebContent;
import com.example.codingboy.zhihupaper.http.HttpRequest;
import com.google.gson.Gson;

import org.w3c.dom.Text;

/**
 * Created by codingBoy on 16/8/12.
 */
public class NewsDetailAsyncTask extends AsyncTask<String,Void,String> {

    private final WebView mWebView;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private View mView;

    public NewsDetailAsyncTask(WebView webView,Context context)
    {
        mContext=context;
        mInflater=LayoutInflater.from(context);
        mWebView=webView;
    }

    @Override
    protected String doInBackground(String... params) {
        String result= HttpRequest.requestJSONResult(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson=new Gson();
        WebContent webContent=gson.fromJson(s,WebContent.class);
        String html=convertToHtml(webContent);
        initWebHeader(webContent);
        mWebView.loadDataWithBaseURL(null, html, null, null, null);
        mWebView.addView(mView, 0);
        Log.e("TAG7", "加载headerview10");

    }

    private void initWebHeader(WebContent webContent) {
        mView=mInflater.inflate(R.layout.webview_header,mWebView,false);
        ImageView imageView= (ImageView) mView.findViewById(R.id.iv_webheader);
        imageView.setTag(webContent.image);
        new ImageLoaderForPager().loadPagerImage(webContent.image, imageView);
        TextView tvSource= (TextView) mView.findViewById(R.id.tv_source);
        tvSource.setText(webContent.image_source);
        TextView tvTitle= (TextView) mView.findViewById(R.id.tv_webheader_title);
        tvTitle.setText(webContent.title);
    }

    private String convertToHtml(WebContent webContent) {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" href=\"").append(webContent.css[0]).append(
                "\" type=\"text/css\">\n" +
                        "\t\t<meta charset=\"utf-8\">\n"
        ).append("</head>\n" +"<body>\n").append(webContent.body).append("</body>\n" +
                "</html>");
        return stringBuilder.toString();
    }
}
