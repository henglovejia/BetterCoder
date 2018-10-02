package club.bettercoder.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


import club.bettercoder.R;
import club.bettercoder.base.BaseActivity;
import club.bettercoder.base.BaseApplication;
import club.bettercoder.base.BaseCallBack;
import club.bettercoder.login.api.LoginApi;
import club.bettercoder.login.model.LoginBean;
import club.bettercoder.login.model.LoginModel;
import club.bettercoder.personal.PersonalMainFragment;
import club.bettercoder.questions.QuestionMainFragment;


public class MainActivity extends BaseActivity {
    public static MainActivity sActivityContext;
    private TabLayout mMenu;
    private ViewPager mContent;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private Integer[] tabIcon = new Integer[]{R.drawable.btn_menu_fei, R.drawable.btn_menu_friend};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_main);
        mMenu = (TabLayout) findViewById(R.id.menu);
        mContent = (ViewPager) findViewById(R.id.content);
        initContent();
        initMenu();
        sActivityContext = this;
    }


    private void initMenu() {
        mMenu.setTabMode(TabLayout.MODE_FIXED);
        mMenu.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mMenu, 10);
        mMenu.setupWithViewPager(mContent);
        for (int i = 0; i < tabIndicators.size(); i++) {
            TabLayout.Tab itemTab = mMenu.getTabAt(i);
            if (itemTab != null) {
                itemTab.setCustomView(R.layout.module_list_item_menu_custom);
                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(tabIndicators.get(i));
                ImageView itemIv = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_menu_item);
                itemIv.setImageResource(tabIcon[i]);
            }
        }
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        Resources res = BaseApplication.sAppContext.getResources();
        String[] simpleCountries = res.getStringArray(R.array.menu);
        for (int i = 0; i < simpleCountries.length; i++) {
            tabIndicators.add(simpleCountries[i]);
        }
        tabFragments = new ArrayList<>();
        tabFragments.add(new QuestionMainFragment());
        tabFragments.add(new PersonalMainFragment());
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContent.setAdapter(contentAdapter);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
