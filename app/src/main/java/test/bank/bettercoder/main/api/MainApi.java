package test.bank.bettercoder.main.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import test.bank.bettercoder.main.model.SubjectBean;
import test.bank.bettercoder.main.model.SubjectModel;
import test.bank.bettercoder.utils.request.SimpleCall;

public interface MainApi {
     @POST("BetterCoder/controller/WechatQuestionService/initSubject")
    SimpleCall<SubjectModel> getSubject(@Body SubjectBean bean);
}
