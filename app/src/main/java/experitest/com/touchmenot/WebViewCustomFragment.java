package experitest.com.touchmenot;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewCustomFragment extends Fragment {


    public WebViewCustomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_web_view_custom, container, false);
        final WebView webview = root.findViewById(R.id.webitems);
        if(webview!=null) {
            webview.loadUrl("http://172.16.16.123:8080");
        }
        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                webview.reload();
                this.sendEmptyMessageDelayed(0, 1000);
            }
        };
        handler.sendEmptyMessageDelayed(0, 1000);
        return root;
    }
}
