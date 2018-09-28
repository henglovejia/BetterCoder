package test.bank.bettercoder.login.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import test.bank.bettercoder.base.BcBaseModel;
import test.bank.bettercoder.login.model.LoginBean;
import test.bank.bettercoder.login.model.LoginModel;
import test.bank.bettercoder.utils.request.SimpleCall;

/**
 * Created by heng on 18/9/13.
 */

public interface LoginApi {
    //登录
    @POST("BetterCoder/controller/AndroidUserService/loginSession")
    SimpleCall<LoginModel> doLoginSession(@Body LoginBean bean);
    //注册
    @POST("doRegist")
    SimpleCall<BcBaseModel> doRegist();
    //修改密码
    @POST("doModify")
    SimpleCall<BcBaseModel> doModify();
    //登出
    @POST("doLogout")
    SimpleCall<BcBaseModel> doLogout();
}
