package com.saild.douyintest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.saild.douyintest.R;
import com.saild.douyintest.util.Global;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mStartDouyinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartDouyinButton  = (Button) findViewById(R.id.bt_start_douyin);
        mStartDouyinButton.setOnClickListener(this);
        initConfig();
    }


    private void initConfig() {
        try {
            Global.setContext(this);
            Global.setHandler(new Handler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mStartDouyinButton){
            startActivity(new Intent(this,DouyinActivity.class));
        }
    }
}
