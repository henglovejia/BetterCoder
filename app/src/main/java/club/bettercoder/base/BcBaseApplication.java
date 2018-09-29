package club.bettercoder.base;

import android.app.Application;

public class BcBaseApplication extends Application {
    public static BcBaseApplication sAppContext;
    public static Boolean continueExercise = false;

    public void onCreate() {
        super.onCreate();
        sAppContext = this;
    }
}
