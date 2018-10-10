package club.bettercoder.questions;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import club.bettercoder.R;
import club.bettercoder.base.BaseActivity;
import club.bettercoder.base.BaseApplication;
import club.bettercoder.base.BaseCallBack;
import club.bettercoder.questions.api.QuestionApi;
import club.bettercoder.questions.model.QuestionBean;
import club.bettercoder.questions.model.QuestionModel;
import club.bettercoder.questions.po.DbQuestion;
import club.bettercoder.widget.code.CodeView;

public class DoQuestionMainActivity extends BaseActivity {
    private String TAG = "DoQuestionMainActivity";
    private String knowledgeUuid, subject;
    private TextView questionItemA, questionItemB, questionItemC, questionItemD, questionAnswer, questionAnalyse;
    private CodeView questionContent;
    private DbQuestion question;
    private Button preQuestion,nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_question);
        hideTitleBar();
        Bundle bundle = getIntent().getExtras();
        knowledgeUuid = bundle.getString("knowledgeUuid");
        subject = bundle.getString("subject");
        getQuestion();
    }

    @Override
    protected void initView() {
        questionContent = (CodeView) findViewById(R.id.question_content);
        questionItemA = (TextView) findViewById(R.id.tv_question_item_A);
        questionItemB = (TextView) findViewById(R.id.tv_question_item_B);
        questionItemC = (TextView) findViewById(R.id.tv_question_item_C);
        questionItemD = (TextView) findViewById(R.id.tv_question_item_D);
        questionAnswer = (TextView) findViewById(R.id.tv_question_answer);
        questionAnalyse = (TextView) findViewById(R.id.tv_question_analyse);
        questionContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    questionItemA.setVisibility(View.VISIBLE);
                    questionItemB.setVisibility(View.VISIBLE);
                    questionItemC.setVisibility(View.VISIBLE);
                    questionItemD.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getQuestion() {
        if (BaseApplication.continueExercise) {
            addRequest(getService(QuestionApi.class).continueQuestions(), new BaseCallBack<QuestionModel>() {
                @Override
                public void onSuccess200(QuestionModel model) {
                    fillQuestion(model);
                }
            });
        } else {
            addRequest(getService(QuestionApi.class).startQuestions(new QuestionBean(knowledgeUuid, subject)), new BaseCallBack<QuestionModel>() {
                @Override
                public void onSuccess200(QuestionModel model) {
                    fillQuestion(model);
                }
            });
        }
    }

    public void doExercise(){

    }

    public void fillQuestion(QuestionModel model) {
        question = model.questions.get(0);
        questionContent.showCodeHtmlByClass("<p>" + model.number + "、" + question.getqContent().getHtmlContent().substring(3), "brush");
        questionItemA.setText(parseHtml2Text(question.getItemList().get(0).getHtmlContent()));
        questionItemB.setText(parseHtml2Text(question.getItemList().get(1).getHtmlContent()));
        questionItemC.setText(parseHtml2Text(question.getItemList().get(2).getHtmlContent()));
        questionItemD.setText(parseHtml2Text(question.getItemList().get(3).getHtmlContent()));
    }

    public void chooseItem(View view) {
        if (questionAnswer.getVisibility() == View.GONE) {
            questionAnswer.setVisibility(View.VISIBLE);
            questionAnalyse.setVisibility(View.VISIBLE);
            questionAnswer.setText("答案:  " + parseHtml2Text(question.getqContent().getHtmlAnswer()));
            questionAnalyse.setText("解析:" + parseHtml2Text(question.getqContent().getHtmlAnalyse()));
            switch (view.getId()) {
                case R.id.tv_question_item_A:
                    if (question.getItemList().get(0).getRightFlag() == 1) {
                        questionItemA.setBackgroundColor(getResources().getColor(R.color.question_item_right));
                    } else {
                        questionItemA.setBackgroundColor(getResources().getColor(R.color.question_item_error));
                    }
                    break;
                case R.id.tv_question_item_B:
                    if (question.getItemList().get(1).getRightFlag() == 1) {
                        questionItemB.setBackgroundColor(getResources().getColor(R.color.question_item_right));
                    } else {
                        questionItemB.setBackgroundColor(getResources().getColor(R.color.question_item_error));
                    }
                    break;
                case R.id.tv_question_item_C:
                    if (question.getItemList().get(2).getRightFlag() == 1) {
                        questionItemC.setBackgroundColor(getResources().getColor(R.color.question_item_right));
                    } else {
                        questionItemC.setBackgroundColor(getResources().getColor(R.color.question_item_error));
                    }
                    break;
                case R.id.tv_question_item_D:
                    if (question.getItemList().get(3).getRightFlag() == 1) {
                        questionItemD.setBackgroundColor(getResources().getColor(R.color.question_item_right));
                    } else {
                        questionItemD.setBackgroundColor(getResources().getColor(R.color.question_item_error));
                    }
                    break;
            }
        }
    }

    public void chooseQuestionDirection(View view){
        switch (view.getId()){
            case R.id.bt_pre_question:
                getQuestion();
                break;
            case R.id.bt_next_question:
                getQuestion();
                break;
        }
    }

    private String parseHtml2Text(String htmlText) {
        return (Html.fromHtml(htmlText) + "").replace("\n", "");
    }
}
