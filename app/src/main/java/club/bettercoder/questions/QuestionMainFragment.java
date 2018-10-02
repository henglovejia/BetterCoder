package club.bettercoder.questions;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import club.bettercoder.R;
import club.bettercoder.base.BaseApplication;
import club.bettercoder.base.BaseCallBack;
import club.bettercoder.base.BaseFragment;
import club.bettercoder.questions.api.QuestionApi;
import club.bettercoder.questions.model.QuestionBean;
import club.bettercoder.questions.model.QuestionModel;
import club.bettercoder.widget.code.CodeView;

public class QuestionMainFragment extends BaseFragment {
    private static String TAG = "QuestionMainActivity";
    private CodeView codeView;
    private Button bt_get_question,bt_get_tree;

    @Override
    public int chooseLayout() {
        return R.layout.module_fragment_question_main;
    }

    @Override
    public void initView() {
        codeView = (CodeView) view.findViewById(R.id.codeView);
        bt_get_question = (Button) view.findViewById(R.id.bt_get_question);
        bt_get_tree = (Button)view.findViewById(R.id.bt_get_tree);
    }

    @Override
    public void onStartInit() {
    }

    @Override
    public void initClickListener() {
        bt_get_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApplication.continueExercise) {
                    addRequest(getService(QuestionApi.class).continueQuestions(new QuestionBean()), new BaseCallBack<QuestionModel>() {
                        @Override
                        public void onSuccess200(QuestionModel model) {
                            codeView.showCodeHtmlByCssSelect(model.number + "、" + model.questions.get(0).getqContent().getHtmlContent(), ".brush");
                        }
                    });
                }
            }
        });
        bt_get_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TreeActivity.class);
                startActivity(intent);
            }
        });
    }
}
