package club.bettercoder.base;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import club.bettercoder.login.api.LoginApi;
import club.bettercoder.login.model.LoginBean;
import club.bettercoder.utils.ToastUtils;
import club.bettercoder.utils.request.RequestManager;
import club.bettercoder.utils.request.SimpleCallBack;

/**
 * Created by heng on 18/9/13.
 */

public abstract class BaseCallBack<T> implements SimpleCallBack<T> {
    @Override
    public void onSuccess(T t) {
        if (t instanceof BaseModel) {
            BaseModel m = (BaseModel) t;
            if ("0".equals(m.returnCode) || "1".equals(m.returnCode)) {// 请求处理成功
                onSuccess200(t);
            } else if ("-1".equals(m.returnCode)) {
                LoginBean bean = new LoginBean("b2e4b5b0-41a0-43cc-86c6-a54c46a0c899");
                RequestManager.create(BaseApplication.sAppContext).addRequest(RequestManager.create(BaseApplication.sAppContext).getService(LoginApi.class).doLoginSession(bean), null, null);
            } else {
                ToastUtils.show(BaseApplication.sAppContext, m.returnMsg);
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
                ToastUtils.show(BaseApplication.sAppContext, msg);
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
     *
     * @param t
     */
    public void onFinish(T t) {
    }

    public abstract void onSuccess200(T t);
}
