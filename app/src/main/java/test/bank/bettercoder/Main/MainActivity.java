package test.bank.bettercoder.Main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


import test.bank.bettercoder.R;
import test.bank.bettercoder.personal.PersonalMainFragment;
import test.bank.bettercoder.questions.QuestionMainFragment;


public class MainActivity extends FragmentActivity {
    public static MainActivity sActivityContext;
    private TabLayout mMenu;
    private ViewPager mContent;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
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
                itemTab.setCustomView(R.layout.item_tab_layout_custom);
                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(tabIndicators.get(i));
            }
        }
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            tabIndicators.add("Tab " + i);
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
}
