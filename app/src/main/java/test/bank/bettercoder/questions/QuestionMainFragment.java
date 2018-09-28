package test.bank.bettercoder.questions;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseApplication;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.base.BcBaseFragment;
import test.bank.bettercoder.questions.api.QuestionApi;
import test.bank.bettercoder.questions.model.QuestionBean;
import test.bank.bettercoder.questions.model.QuestionModel;
import test.bank.bettercoder.widget.code.CodeView;

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
