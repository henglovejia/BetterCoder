package test.bank.bettercoder.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseFragment;

public class PersonalMainFragment extends BcBaseFragment {
    public  static String TAG = "PersonalMainActivity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.personal_fragment_main, container, false);
    }
}
