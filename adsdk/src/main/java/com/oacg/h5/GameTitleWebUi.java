package com.oacg.h5;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class GameTitleWebUi extends GameFullWebUi {

    ImageView iv_back;
    TextView tv_title;

    protected int getLayoutRes(){
        return getLayout("ad_game_title_web_ui");
    }

    protected void initView() {
		// TODO Auto-generated method stub
    	super.initView();
    	iv_back= (ImageView) findViewById(getId("iv_back"));
        tv_title= (TextView) findViewById(getId("tv_titlename"));
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
	}
    
    @Override
    protected String getDefaultUrl() {
    	// TODO Auto-generated method stub
    	return "http://game.oacg.cn/game_center/v1/index.php?m=Index&a=index&channel_id=1002";
    }

    @Override
    public void onReceiveTitle(String title) {
        super.onReceiveTitle(title);
        tv_title.setText(title);
    }
}
