package test.bank.bettercoder.base;

import android.app.Application;

public class BcBaseApplication extends Application {
    public static BcBaseApplication sAppContext;

    public void onCreate() {
        super.onCreate();
        sAppContext = this;
    }
}
