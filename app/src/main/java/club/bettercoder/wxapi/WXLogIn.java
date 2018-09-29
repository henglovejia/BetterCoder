package club.bettercoder.wxapi;

import android.content.Intent;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import club.bettercoder.Main.MainActivity;
import club.bettercoder.base.BcBaseActivity;
import club.bettercoder.base.BcBaseApplication;

import static club.bettercoder.base.BcBaseContent.APP_ID;

public class WXLogIn extends BcBaseActivity {


    public WXLogIn(){
    }

    public void doLogin(){
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(BcBaseApplication.sAppContext, APP_ID, true);
        iwxapi.registerApp(APP_ID);
        if (!iwxapi.isWXAppInstalled()) {
            Intent intent = new Intent(BcBaseApplication.sAppContext, MainActivity.class);
            startActivity(intent);
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            iwxapi.sendReq(req);
        }
    }
}
