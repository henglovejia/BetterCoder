package club.bettercoder.questions;

import android.view.View;
import android.widget.Button;

import club.bettercoder.R;
import club.bettercoder.base.BcBaseApplication;
import club.bettercoder.base.BcBaseCallBack;
import club.bettercoder.base.BcBaseFragment;
import club.bettercoder.questions.api.QuestionApi;
import club.bettercoder.questions.model.QuestionBean;
import club.bettercoder.questions.model.QuestionModel;
import club.bettercoder.widget.code.CodeView;

public class QuestionMainFragment extends BcBaseFragment {
    private static String TAG = "QuestionMainActivity";
    private CodeView codeView;
    private Button bt_get_question;

    @Override
    public int chooseLayout() {
        return R.layout.question_fragment_main;
    }

    @Override
    public void initView() {
        codeView = (CodeView) view.findViewById(R.id.codeView);
        bt_get_question = (Button) view.findViewById(R.id.bt_get_question);
    }

    @Override
    public void onStartInit() {
    }

    @Override
    public void initClickListener() {
        bt_get_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BcBaseApplication.continueExercise) {
                    addRequest(getService(QuestionApi.class).continueQuestions(new QuestionBean()), new BcBaseCallBack<QuestionModel>() {
                        @Override
                        public void onSuccess200(QuestionModel model) {
                            codeView.showCodeHtmlByCssSelect(model.number + "、" + model.questions.get(0).getqContent().getHtmlContent(), ".brush");
                        }
                    });
                }
            }
        });
    }
}
