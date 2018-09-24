package test.bank.bettercoder.questions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.base.BcBaseFragment;
import test.bank.bettercoder.questions.api.QuestionApi;
import test.bank.bettercoder.questions.model.SubjectBean;
import test.bank.bettercoder.questions.model.SubjectModel;

public class QuestionMainFragment extends BcBaseFragment {
    private static String TAG = "QuestionMainActivity";
    private Button Background;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.question_fragment_main, container, false);
        Background = (Button)view.findViewById(R.id.Background);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Background.setOnClickListener(new View.OnClickListener() {
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
