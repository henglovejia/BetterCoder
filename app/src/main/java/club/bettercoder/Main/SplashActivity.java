package club.bettercoder.Main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import club.bettercoder.base.BcBaseActivity;
import club.bettercoder.login.LoginActivity;

public class SplashActivity extends BcBaseActivity {
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
}
