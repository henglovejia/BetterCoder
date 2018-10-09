package club.bettercoder.questions;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import club.bettercoder.R;
import club.bettercoder.base.BaseFragment;
import club.bettercoder.widget.code.CodeView;

public class QuestionMainFragment extends BaseFragment {
    private static String TAG = "QuestionMainActivity";
    private CodeView codeView;
    private Button bt_get_question, bt_get_tree;

    @Override
    public int chooseLayout() {
        return R.layout.module_fragment_question_main;
    }

    @Override
    public void initView() {
        codeView = (CodeView) view.findViewById(R.id.codeView);
        bt_get_question = (Button) view.findViewById(R.id.bt_get_question);
        bt_get_tree = (Button) view.findViewById(R.id.bt_get_tree);
    }

    @Override
    public void onStartInit() {
    }

    @Override
    public void initClickListener() {
        bt_get_question.setOnClickListener(this);
        bt_get_tree.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_get_question:
                intent = new Intent(getActivity(), DoQuestionMainActivity.class);
                intent.putExtra("knowledgeUuid","5c47dc94-8f53-4e34-b18a-a06f58862364");
                intent.putExtra("subject","java");
                startActivity(intent);
                break;
            case R.id.bt_get_tree:
                intent = new Intent(getActivity(), TreeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
