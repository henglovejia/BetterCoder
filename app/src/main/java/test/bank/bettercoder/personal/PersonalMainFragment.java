package test.bank.bettercoder.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseFragment;

public class PersonalMainFragment extends BcBaseFragment {
    public  static String TAG = "PersonalMainActivity";
    public Button jump;
    @Override
    public int chooseLayout() {
        return R.layout.personal_fragment_main;
    }

    @Override
    public void initView() {
        jump = (Button)view.findViewById(R.id.jump);
    }

    @Override
    public void initClickListener() {
    jump.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), HistoryErrorActivity.class);
            startActivity(intent);
        }
    });
    }
}
