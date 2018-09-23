package test.bank.bettercoder.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by heng on 18/9/13
 */

public class BcBaseActivity extends BaseActivity {
    private String TAG = "BcBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void init() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
