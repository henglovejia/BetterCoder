package test.bank.bettercoder.questions.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import test.bank.bettercoder.questions.model.QuestionBean;
import test.bank.bettercoder.questions.model.QuestionModel;
import test.bank.bettercoder.questions.model.SubjectBean;
import test.bank.bettercoder.questions.model.SubjectModel;
import test.bank.bettercoder.utils.request.SimpleCall;

public interface QuestionApi {
    @POST("BetterCoder/controller/WechatQuestionService/initSubject")
    SimpleCall<SubjectModel> getSubject(@Body SubjectBean bean);
    @POST("BetterCoder/controller/WechatQuestionService/continueExercise")
    SimpleCall<QuestionModel> getQuestions(@Body QuestionBean bean);
}
