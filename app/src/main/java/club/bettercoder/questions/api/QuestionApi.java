package club.bettercoder.questions.api;

import club.bettercoder.questions.model.KnowledgeDistributionBean;
import club.bettercoder.questions.model.KnowledgeDistributionModel;
import club.bettercoder.questions.model.KnowledgeLevel1Bean;
import club.bettercoder.questions.model.KnowledgeLevel1Model;
import retrofit2.http.Body;
import retrofit2.http.POST;
import club.bettercoder.questions.model.QuestionBean;
import club.bettercoder.questions.model.QuestionModel;
import club.bettercoder.questions.model.SubjectModel;
import club.bettercoder.questions.model.TreeBean;
import club.bettercoder.questions.model.TreeModel;
import club.bettercoder.utils.request.SimpleCall;

public interface QuestionApi {
    //获取可用学科
    @POST("controller/QuestionService/initSubject")
    SimpleCall<SubjectModel> getSubject();
    //继续上次做题
    @POST("controller/QuestionService/continueExercise")
    SimpleCall<QuestionModel> continueQuestions();
    //重新开始做题
    @POST("controller/QuestionService/startExercise")
    SimpleCall<QuestionModel> startQuestions(@Body QuestionBean bean);
    //获取知识树
    @POST("controller/QuestionService/initTree")
    SimpleCall<TreeModel> getTrees(@Body TreeBean bean);
    //获取一级知识点
    @POST("controller/WechatQuestionService/getLevel1Knowledges")
    SimpleCall<KnowledgeLevel1Model> getKnowledgeLevel1(@Body KnowledgeLevel1Bean bean);
    //根据一级知识点ID获取雷达图
    @POST("controller/WechatQuestionService/getDistributeByLevel1")
    SimpleCall<KnowledgeDistributionModel> getKnowledgeDistribution(@Body KnowledgeDistributionBean bean);

}
