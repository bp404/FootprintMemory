package com.bp404.footprintmemory;

import android.app.Application;

/**
 * Created by songruxin on 16/11/19.
 */

public class DataForbp404 extends Application {

    private String nickname;

    public String getnickname(){
        return nickname;
    }
    public void setnickname(String s){
        nickname = s;
    }
}

