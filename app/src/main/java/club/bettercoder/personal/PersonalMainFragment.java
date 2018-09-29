package club.bettercoder.personal;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import club.bettercoder.R;
import club.bettercoder.base.BcBaseApplication;
import club.bettercoder.base.BcBaseCallBack;
import club.bettercoder.base.BcBaseFragment;
import club.bettercoder.login.api.LoginApi;
import club.bettercoder.login.model.LoginBean;
import club.bettercoder.login.model.LoginModel;
import club.bettercoder.personal.api.PersonalApi;
import club.bettercoder.questions.api.QuestionApi;
import club.bettercoder.questions.model.QuestionBean;
import club.bettercoder.questions.model.QuestionModel;
import club.bettercoder.utils.request.SimpleCallBack;

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
