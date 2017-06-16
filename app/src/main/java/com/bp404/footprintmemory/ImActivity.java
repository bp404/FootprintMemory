package com.bp404.footprintmemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.widget.Toast;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by songruxin on 16/11/19.
 */

public class ImActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);

        String token = "********************";
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.d("LoginActivity", "--onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Toast.makeText(ImActivity.this,"connect success!",Toast.LENGTH_LONG).show();
                //启动会话界面
//                if (RongIM.getInstance() != null)
//                    RongIM.getInstance().startPrivateChat(ImActivity.this, "1", "title");

////启动会话列表界面
//                if (RongIM.getInstance() != null)
//                    RongIM.getInstance().startConversationList(ImActivity.this);
//
////启动聚合会话列表界面
//                if (RongIM.getInstance() != null)
//                    RongIM.getInstance().startSubConversationList(ImActivity.this, Conversation.ConversationType.GROUP);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("LoginActivity", "--onError" + errorCode);
            }
        });
    }
}
