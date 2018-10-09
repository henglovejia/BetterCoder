package club.bettercoder.personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import club.bettercoder.R;
import club.bettercoder.base.BaseFragment;
import club.bettercoder.widget.CircleImageView;

public class PersonalMainFragment extends BaseFragment{
    public static String TAG = "PersonalMainActivity";
    private ImageView iv_head_pic;
    private CircleImageView civ_avatar;
    private TextView today_total_title, today_total_data;
    private TextView today_correct_rate_title, today_correct_rate_data;
    private TextView overall_total_title, overall_total_data;
    private LinearLayout part_today_wrong;
    private LinearLayout part_past_wrong;
    private LinearLayout part_knowledge_diagram;
    private LinearLayout part_share_data;

    //各版块点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.today_wrong:
                Toast.makeText(getActivity(), "进入今日错题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.past_wrong:
                Toast.makeText(getActivity(), "进入历史错题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.knowledge_diagram:
                Toast.makeText(getActivity(), "进入知识图谱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_data:
                Toast.makeText(getActivity(), "分享数据", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public int chooseLayout() {
        return R.layout.module_fragment_personal_main;
    }

    @Override
    public void initView() {
        iv_head_pic = (ImageView) view.findViewById(R.id.head_pic);
        civ_avatar = (CircleImageView) view.findViewById(R.id.avatar);
        today_total_title = (TextView) view.findViewById(R.id.today_total_title);
        today_total_data = (TextView) view.findViewById(R.id.today_total_data);
        today_correct_rate_data = (TextView) view.findViewById(R.id.today_correct_rate_data);
        today_correct_rate_title = (TextView) view.findViewById(R.id.today_correct_rate_title);
        overall_total_data = (TextView) view.findViewById(R.id.overall_total_data);
        overall_total_title = (TextView) view.findViewById(R.id.overall_total_title);
        part_today_wrong = (LinearLayout) view.findViewById(R.id.today_wrong);
        part_past_wrong = (LinearLayout) view.findViewById(R.id.past_wrong);
        part_knowledge_diagram = (LinearLayout) view.findViewById(R.id.knowledge_diagram);
        part_share_data = (LinearLayout) view.findViewById(R.id.share_data);
    }

    @Override
    public void onStartInit() {

    }

    @Override
    public void initClickListener() {
        part_past_wrong.setOnClickListener(this);
        part_today_wrong.setOnClickListener(this);
        part_knowledge_diagram.setOnClickListener(this);
        part_share_data.setOnClickListener(this);
    }
}
