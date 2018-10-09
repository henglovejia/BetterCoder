package club.bettercoder.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import club.bettercoder.R;
import club.bettercoder.base.BaseActivity;
import club.bettercoder.base.BaseApplication;
import club.bettercoder.main.MainActivity;

import static club.bettercoder.base.BaseContent.APP_ID;

/**
 * Created by heng on 18/9/13.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_login);
        setTitleId(R.string.sign);
        hideLeft();
    }

    @Override
    protected void initView() {

    }

    public void wxLogin(View view){
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(BaseApplication.sAppContext, APP_ID, true);
        iwxapi.registerApp(APP_ID);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
//        if (!iwxapi.isWXAppInstalled()) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        } else {
//            final SendAuth.Req req = new SendAuth.Req();
//            req.scope = "snsapi_userinfo";
//            req.state = "wechat_sdk_demo_test";
//            iwxapi.sendReq(req);
//        }
    }
}
