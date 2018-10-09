package club.bettercoder.login.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import club.bettercoder.base.BaseModel;
import club.bettercoder.login.model.LoginBean;
import club.bettercoder.login.model.LoginModel;
import club.bettercoder.utils.request.SimpleCall;

/**
 * Created by heng on 18/9/13.
 */

public interface LoginApi {
    //登录
    @POST("controller/AndroidUserService/loginSession")
    SimpleCall<LoginModel> doLoginSession(@Body LoginBean bean);
    //注册
    @POST("doRegist")
    SimpleCall<BaseModel> doRegist();
    //修改密码
    @POST("doModify")
    SimpleCall<BaseModel> doModify();
    //登出
    @POST("doLogout")
    SimpleCall<BaseModel> doLogout();
}
