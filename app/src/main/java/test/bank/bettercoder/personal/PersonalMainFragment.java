package test.bank.bettercoder.personal;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseApplication;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.base.BcBaseFragment;
import test.bank.bettercoder.login.api.LoginApi;
import test.bank.bettercoder.login.model.LoginBean;
import test.bank.bettercoder.login.model.LoginModel;
import test.bank.bettercoder.personal.api.PersonalApi;
import test.bank.bettercoder.questions.api.QuestionApi;
import test.bank.bettercoder.questions.model.QuestionBean;
import test.bank.bettercoder.questions.model.QuestionModel;
import test.bank.bettercoder.utils.request.SimpleCallBack;

public class PersonalMainFragment extends BcBaseFragment {
    public static String TAG = "PersonalMainActivity";

    @Override
    public int chooseLayout() {
        return R.layout.personal_fragment_main;
    }

    @Override
    public void initView() {
    }

    @Override
    public void onStartInit() {

    }

    @Override
    public void initClickListener() {
    }
}
