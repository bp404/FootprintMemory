package com.bp404.footprintmemory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;


/**
 * Created by songruxin on 16/11/15.
 */

public class LoginActivity extends Activity {

    Md5Forbp404 md5 = new Md5Forbp404();
    ToolForbp404 tool = new ToolForbp404();

    private EditText usernameEdt;
    private EditText passwordEdt;
    private Button loginBtn;
    public String username;
    public String password;
    public String state = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEdt = (EditText)findViewById(R.id.usernameEdt);
        passwordEdt = (EditText)findViewById(R.id.passwordEdt);
        loginBtn = (Button)findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameEdt.getText().toString();
                password = passwordEdt.getText().toString();

                String md5Str = md5.getMD5(password);
                String timestamp = tool.getTimestamp();
                String finaltimeStampStr = timestamp.substring(0,8);
                String password_Timestamp = md5Str + finaltimeStampStr;
                //Toast.makeText(LoginActivity.this,password_Timestamp,Toast.LENGTH_LONG).show();
                String finalPassword = md5.getMD5(password_Timestamp);

                KJHttp kjHttp = new KJHttp();
                HttpParams params = new HttpParams();
                HttpConfig httpconfig = new HttpConfig();
                httpconfig.cacheTime = 0;

                params.put("username", username);
                params.put("password", finalPassword);

                kjHttp.post("http://www.bp404.com/*****.php", params, new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        JSONObject jsonob = null;
                        try {
                            jsonob = new JSONObject(t);
                            state = jsonob.get("state").toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        switch (state){
                            case "0":
                                //Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("提示")
                                        .setMessage("用户名错误")
                                        .setPositiveButton("好的", null)
                                        .show();
                                break;
                            case "1":
                                //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("提示")
                                        .setMessage("密码错误")
                                        .setPositiveButton("好的", null)
                                        .show();
                                break;
                            case "2":
                                DataForbp404 dataForbp404 = new DataForbp404();
                                dataForbp404.setnickname(username);
                                Intent intent = new Intent();
                                intent.putExtra("nickname",username);
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "服务器崩溃了～请联系管理员", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        Toast.makeText(LoginActivity.this, strMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
