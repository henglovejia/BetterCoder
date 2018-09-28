package test.bank.bettercoder.questions.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import test.bank.bettercoder.questions.model.QuestionBean;
import test.bank.bettercoder.questions.model.QuestionModel;
import test.bank.bettercoder.questions.model.SubjectBean;
import test.bank.bettercoder.questions.model.SubjectModel;
import test.bank.bettercoder.questions.model.TreeBean;
import test.bank.bettercoder.questions.model.TreeModel;
import test.bank.bettercoder.utils.request.SimpleCall;

public interface QuestionApi {
    //获取可用学科
    @POST("BetterCoder/controller/QuestionService/initSubject")
    SimpleCall<SubjectModel> getSubject(@Body SubjectBean bean);
    //继续上次做题
    @POST("BetterCoder/controller/QuestionService/continueExercise")
    SimpleCall<QuestionModel> continueQuestions(@Body QuestionBean bean);
    //重新开始做题
    @POST("BetterCoder/controller/QuestionService/startExercise")
    SimpleCall<QuestionModel> startQuestions(@Body QuestionBean bean);
    //获取知识树
    @POST("BetterCoder/controller/QuestionService/initTree")
    SimpleCall<TreeModel> getTrees(@Body TreeBean bean);
}
