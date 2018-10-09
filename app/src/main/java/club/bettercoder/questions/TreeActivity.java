package club.bettercoder.questions;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import club.bettercoder.R;
import club.bettercoder.base.BaseActivity;
import club.bettercoder.base.BaseCallBack;
import club.bettercoder.questions.adapter.TreeAdapter;
import club.bettercoder.questions.api.QuestionApi;
import club.bettercoder.questions.model.TreeBean;
import club.bettercoder.questions.model.TreeModel;
import club.bettercoder.questions.po.Tree;

public class TreeActivity extends BaseActivity {
    private TreeAdapter treeAdapter = null;
    private ListView treeListVew = null;
    //当前所显示的目录列表
    private List<Tree> treeShowList = new ArrayList<>();
    //各级目录列表,以parent为键
    private HashMap<String, ArrayList<Tree>> level2List, level3List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_list_tree);
        initData();
        treeAdapter = new TreeAdapter(this, R.layout.module_list_item_tree, treeShowList);
        treeListVew.setAdapter(treeAdapter);
    }

    public void initData() {
        addRequest(getService(QuestionApi.class).getTrees(new TreeBean()), new BaseCallBack<TreeModel>() {
            @Override
            public void onSuccess200(TreeModel model) {
                level2List = new HashMap<>();
                level3List = new HashMap<>();
                for (Tree tree : model.trees) {
                    switch (tree.getLevel()) {
                        case 1:
                            treeShowList.add(tree);
                            break;
                        case 2:
                            if (level2List.containsKey(tree.getParent())) {
                                level2List.get(tree.getParent()).add(tree);
                            } else {
                                ArrayList<Tree> tmp = new ArrayList<>();
                                tmp.add(tree);
                                level2List.put(tree.getParent(), tmp);
                            }
                            break;
                        case 3:
                            if (level3List.containsKey(tree.getParent())) {
                                level3List.get(tree.getParent()).add(tree);
                            } else {
                                ArrayList<Tree> tmp = new ArrayList<>();
                                tmp.add(tree);
                                level3List.put(tree.getParent(), tmp);
                            }
                            break;
                    }
                }
                treeAdapter.notifyDataSetChanged();
                initView();
            }
        });
    }
    @Override
    protected void initView() {
        treeListVew = (ListView) findViewById(R.id.lv_tree);
        treeListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!treeShowList.get(position).isHasChild()) {// 叶子节点
                    treeShowList.get(position).setSelected(!treeShowList.get(position).isSelected());
                    for (int i = position - 1; i > -1; i--) {
                        if (treeShowList.get(position).getParent().equals(treeShowList.get(i).getId())) {
                            treeShowList.get(i).addOrDelSelectChild(treeShowList.get(position).isSelected());
                            break;
                        }
                    }
                } else if (treeShowList.get(position).isExpanded()) {// 展开状态,点击关闭
                    treeShowList.get(position).setExpanded(false);
                    Tree tree = treeShowList.get(position);
                    List<Tree> temp = new ArrayList<>();
                    for (int i = position + 1; i < treeShowList.size(); i++) {
                        if (tree.getLevel() >= treeShowList.get(i).getLevel()) {
                            break;
                        }
                        temp.add(treeShowList.get(i));
                    }
                    treeShowList.removeAll(temp);
                } else {// 关闭状态,点击打开
                    treeShowList.get(position).setExpanded(true);
                    List<Tree> temp = new ArrayList<>();
                    switch (treeShowList.get(position).getLevel()) {
                        case 1:
                            for (Tree tree : level2List.get(treeShowList.get(position).getId())) {
                                temp.add(tree);
                                if (tree.hasSelectedChild()) {
                                    tree.setExpanded(true);
                                    temp.addAll(level3List.get(tree.getId()));
                                } else {
                                    tree.setExpanded(false);
                                }
                            }
                            break;
                        case 2:
                            temp.addAll(level3List.get(treeShowList.get(position).getId()));
                            break;
                    }
                    treeShowList.addAll(position + 1, temp);
                }
                treeAdapter.notifyDataSetChanged();
            }
        });
    }
}
