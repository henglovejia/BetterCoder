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
    @POST("doLogin")
    SimpleCall<BcBaseModel> doLogin();
    //注册
    @POST("doRegist")
    SimpleCall<BcBaseModel> doRegist();
    //修改密码
    @POST("doModify")
    SimpleCall<BcBaseModel> doModify();
    //登出
    @POST("doLogout")
    SimpleCall<BcBaseModel> doLogout();

    @POST("/tc/getLoginState/")
    SimpleCall<LoginModel> doLogin(@Body LoginBean model);

    @POST("/tc/apiv1/")
    SimpleCall<LoginModel> getPassword(@Body LoginBean model);
}
