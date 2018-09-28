package test.bank.bettercoder.base;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import test.bank.bettercoder.R;
import test.bank.bettercoder.login.api.LoginApi;
import test.bank.bettercoder.login.model.LoginBean;
import test.bank.bettercoder.utils.ToastUtils;
import test.bank.bettercoder.utils.request.RequestManager;
import test.bank.bettercoder.utils.request.SimpleCallBack;

/**
 * Created by heng on 18/9/13.
 */

public abstract class BcBaseCallBack<T> implements SimpleCallBack<T> {
    @Override
    public void onSuccess(T t) {
        if (t instanceof BcBaseModel) {
            BcBaseModel m = (BcBaseModel) t;
            if ("0".equals(m.returnCode) || "1".equals(m.returnCode)) {// 请求处理成功
                onSuccess200(t);
            } else if ("-1".equals(m.returnCode)) {// todo 登录超时
                LoginBean bean = new LoginBean("java", "b2e4b5b0-41a0-43cc-86c6-a54c46a0c899");
                RequestManager.create(BcBaseApplication.sAppContext).addRequest(RequestManager.create(BcBaseApplication.sAppContext).getService(LoginApi.class).doLoginSession(bean),null,null);
            } else {
                ToastUtils.show(BcBaseApplication.sAppContext, m.returnMsg);
            }
        } else if (t instanceof JSONObject) {
            JSONObject jo = (JSONObject) t;
            String status = "";
            String msg = "";
            try {
                status = jo.getString("status");
                msg = jo.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("success".equals(status)) {
                onSuccess200(t);
            } else if (!TextUtils.isEmpty(msg)) {
                ToastUtils.show(BcBaseApplication.sAppContext, msg);
            }
        }
        onFinish(t);
    }

    @Override
    public void onFailure(Object o) {

    }

    @Override
    public void onError(Exception e) {

    }

    /**
     * override for custom case when http success
     * @param t
     */
    public void onFinish(T t) {
    }

    public abstract void onSuccess200(T t);
}
