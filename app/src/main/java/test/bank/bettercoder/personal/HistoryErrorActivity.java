package test.bank.bettercoder.personal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseActivity;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.questions.api.QuestionApi;
import test.bank.bettercoder.questions.model.SubjectBean;
import test.bank.bettercoder.questions.model.SubjectModel;

public class HistoryErrorActivity extends BcBaseActivity {
    private static String TAG = "HistoryErrorActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_error_activity);
        setTitleId(R.string.history_error_title);
    }
    public void Background(View view){
        SubjectBean bean = new SubjectBean();
        addRequest(getService(QuestionApi.class).getSubject(bean), new BcBaseCallBack<SubjectModel>() {
            @Override
            public void onSuccess200(SubjectModel body) {
                Log.d(TAG,body.valid_knowledge_subjects[0].getName());
            }
        });
    }
}
