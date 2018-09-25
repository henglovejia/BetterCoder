package test.bank.bettercoder.questions;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.base.BcBaseFragment;
import test.bank.bettercoder.questions.api.QuestionApi;
import test.bank.bettercoder.questions.model.SubjectBean;
import test.bank.bettercoder.questions.model.SubjectModel;

public class QuestionMainFragment extends BcBaseFragment {
    private static String TAG = "QuestionMainActivity";
    private Button background;

    @Override
    public int chooseLayout() {
        return R.layout.question_fragment_main;
    }

    @Override
    public void initView() {
        background = (Button)view.findViewById(R.id.Background);
    }

    @Override
    public void initClickListener() {
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectBean bean = new SubjectBean();
                addRequest(getService(QuestionApi.class).getSubject(bean), new BcBaseCallBack<SubjectModel>() {
                    @Override
                    public void onSuccess200(SubjectModel body) {
                        Log.d(TAG,body.valid_knowledge_subjects[0].getName());
                    }
                });
            }
        });
    }
}
