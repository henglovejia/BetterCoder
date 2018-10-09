package club.bettercoder.questions.api;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import club.bettercoder.questions.model.QuestionBean;
import club.bettercoder.questions.model.QuestionModel;
import club.bettercoder.questions.model.SubjectBean;
import club.bettercoder.questions.model.SubjectModel;
import club.bettercoder.questions.model.TreeBean;
import club.bettercoder.questions.model.TreeModel;
import club.bettercoder.utils.request.SimpleCall;

public interface QuestionApi {
    //获取可用学科
    @POST("controller/QuestionService/initSubject")
    SimpleCall<SubjectModel> getSubject(@Body SubjectBean bean);
    //继续上次做题
    @POST("controller/QuestionService/continueExercise")
    SimpleCall<QuestionModel> continueQuestions();
    //重新开始做题
    @POST("controller/QuestionService/startExercise")
    SimpleCall<QuestionModel> startQuestions(@Body QuestionBean bean);
    //获取知识树
    @POST("controller/QuestionService/initTree")
    SimpleCall<TreeModel> getTrees(@Body TreeBean bean);
}
