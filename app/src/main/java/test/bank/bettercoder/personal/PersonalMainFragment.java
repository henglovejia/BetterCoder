package test.bank.bettercoder.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseCallBack;
import test.bank.bettercoder.base.BcBaseFragment;
import test.bank.bettercoder.login.api.LoginApi;
import test.bank.bettercoder.login.model.LoginBean;
import test.bank.bettercoder.login.model.LoginModel;
import test.bank.bettercoder.personal.api.PersonalApi;
import test.bank.bettercoder.utils.request.SimpleCallBack;

public class PersonalMainFragment extends BcBaseFragment {
    public static String TAG = "PersonalMainActivity";
    public Button login;

    @Override
    public int chooseLayout() {
        return R.layout.personal_fragment_main;
    }

    @Override
    public void initView() {
        login = (Button) view.findViewById(R.id.login);
    }

    @Override
    public void onStartInit() {

    }

    @Override
    public void initClickListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest(getService(LoginApi.class).doLoginSession(new LoginBean("java", "b2e4b5b0-41a0-43cc-86c6-a54c46a0c899")), new BcBaseCallBack<LoginModel>() {
                    @Override
                    public void onSuccess200(LoginModel loginModel) {
                        Toast.makeText(getActivity(),loginModel.returnMsg,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
