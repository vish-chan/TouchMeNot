package experitest.com.touchmenot;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.lang.ref.WeakReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewCustomFragment extends Fragment {

    final String TAG = "WEBVIEW<TouchMeNo>";
    final long RELOADINTERVAL = 10000;
    Handler mHandler;

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


        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                hLogWrapper.write('D', TAG, "handleMessage called for "+ getActivity().getLocalClassName());
                if(webview!=null)
                    webview.reload();
                this.sendEmptyMessageDelayed(0, RELOADINTERVAL);
            }
        };
        mHandler.sendEmptyMessageDelayed(0, RELOADINTERVAL );
        return root;
    }

    @Override
    public void onDestroyView() {
        if(mHandler!=null) {
            mHandler.removeCallbacksAndMessages(null);
            hLogWrapper.write('D', TAG, "Callbacks removed for"+ getActivity().getLocalClassName());
        }
        super.onDestroyView();
    }
}
