package club.bettercoder.login.wxapi;

import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import club.bettercoder.base.BaseActivity;
import club.bettercoder.base.BaseCallBack;
import club.bettercoder.login.api.LoginApi;
import club.bettercoder.login.model.LoginBean;
import club.bettercoder.login.model.LoginModel;

import static club.bettercoder.base.BaseContent.APP_ID;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                //获取用户信息
                Log.d(TAG, code);
                addRequest(getService(LoginApi.class).doLoginSession(new LoginBean(code)), new BaseCallBack<LoginModel>() {
                    @Override
                    public void onSuccess200(LoginModel model) {

                    }
                });
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            default:
                finish();
                break;
        }
    }
}
