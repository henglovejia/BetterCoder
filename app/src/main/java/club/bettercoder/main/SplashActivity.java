package club.bettercoder.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import club.bettercoder.R;
import club.bettercoder.base.BaseActivity;
import club.bettercoder.login.LoginActivity;

import static club.bettercoder.base.BaseContent.WX_CODE_PREF;
import static club.bettercoder.base.BaseContent.WX_CODE_PREF_KEY;

public class SplashActivity extends BaseActivity {
    private static final int SPLASH_TIME = 2000;
    private static final int HAS_LOGIN = 0;
    private static final int NEED_LOGIN = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = null;
            switch (msg.what) {
                case HAS_LOGIN:
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                    break;
                case NEED_LOGIN:
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.module_activity_splash);
        hideTitleBar();
        String wx_code = getSharedPreferences(WX_CODE_PREF,0).getString(WX_CODE_PREF_KEY,null);
        if(wx_code == null){
            mHandler.sendEmptyMessage(NEED_LOGIN);
        }else {
            mHandler.sendEmptyMessageDelayed(HAS_LOGIN,SPLASH_TIME);
        }
    }
}
