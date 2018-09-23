package test.bank.bettercoder.main;

import android.os.Bundle;
import android.util.Log;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseActivity;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.main.api.MainApi;
import test.bank.bettercoder.main.model.SubjectBean;
import test.bank.bettercoder.main.model.SubjectModel;

public class MainActivity extends BcBaseActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubjectBean bean = new SubjectBean();
        addRequest(getService(MainApi.class).getSubject(bean), new BcBaseCallBack<SubjectModel>() {
            @Override
            public void onSuccess200(SubjectModel body) {
                Log.d(TAG,body.valid_knowledge_subjects[0].getName());
            }
        });
    }
}
