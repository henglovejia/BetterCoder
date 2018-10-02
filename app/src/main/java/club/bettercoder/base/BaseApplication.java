package club.bettercoder.base;

import android.app.Application;
import android.widget.Toast;

import club.bettercoder.login.api.LoginApi;
import club.bettercoder.login.model.LoginBean;
import club.bettercoder.login.model.LoginModel;
import club.bettercoder.utils.request.RequestManager;

public class BaseApplication extends Application {
    public static BaseApplication sAppContext;
    public static Boolean continueExercise = false;

    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        doLogin();
    }
    public void doLogin() {
        RequestManager requestManager = RequestManager.create(this);
        requestManager.addRequest(requestManager.getService(LoginApi.class).doLoginSession(new LoginBean("b2e4b5b0-41a0-43cc-86c6-a54c46a0c899")), new BaseCallBack<LoginModel>() {
            @Override
            public void onSuccess200(LoginModel model) {
                continueExercise = model.isContinue;
                Toast.makeText(BaseApplication.sAppContext,"接受到数据",Toast.LENGTH_SHORT).show();
            }
        }, null);
//        requestManager.addRequest(requestManager.getService(LoginApi.class).doTest(new BaseBean()), new BaseCallBack<BaseModel>() {
//            @Override
//            public void onSuccess200(BaseModel model) {
//            }
//        }, null);
    }
}
