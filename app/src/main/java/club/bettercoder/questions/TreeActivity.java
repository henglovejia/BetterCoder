package club.bettercoder.questions;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
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
    /**
     * 当前所显示的目录列表
     */
    private List<Tree> treeShowList = new ArrayList<>();
    /**
     * 所有的目录列表
     */
    private List<Tree> treeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_list_tree);
        treeListVew = (ListView) findViewById(R.id.lv_tree);
        initData();
        treeAdapter = new TreeAdapter(this, R.layout.module_list_item_tree, treeShowList);
        treeListVew.setAdapter(treeAdapter);

    }

    public void initData() {
        addRequest(getService(QuestionApi.class).getTrees(new TreeBean()), new BaseCallBack<TreeModel>() {
            @Override
            public void onSuccess200(TreeModel model) {
                treeList = model.trees;
                for (Tree tree : treeList) {
                    if (tree.getLevel() == 1) {
                        treeShowList.add(tree);
                    }
                }
                treeAdapter.notifyDataSetChanged();
                initView();
            }
        });
    }

    private void initView() {
        treeListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!treeShowList.get(position).isHasChild()) {// 没有子节点
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
                    for (Tree mt : treeList) {
                        if (mt.getParent().equals(treeShowList.get(position).getId())) {
                            temp.add(mt);
                            if (mt.hasSelectedChild()) {
                                mt.setExpanded(true);
                            } else {
                                mt.setExpanded(false);
                            }
                        }
                        for (int i = 0; i < temp.size(); i++) {
//                            if (temp.get(i).isExpanded() && mt.getParent().equals(temp.get(i).getId())) {
//                                for (int j = i; j < temp.size(); j++) {
//                                    if (!(mt.getParent().equals(temp.get(j).getParent()) || mt.getParent().equals(temp.get(j).getId()))) {
//                                        temp.add(j, mt);
//                                        break;
//                                    }
//                                    if (j == temp.size() - 1) {
//                                        temp.add(mt);
//                                        break;
//                                    }
//                                }
//                            }
                            if (temp.get(i).isExpanded()) {
                                for (int j = 0; j < treeList.size(); j++) {
                                    if (treeList.get(j).getParent().equals(temp.get(i).getId())) {
                                        temp.add(treeList.get(j));
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < temp.size(); i++) {
                        treeShowList.add(position + i + 1, temp.get(i));
                    }
                }
                treeAdapter.notifyDataSetChanged();
            }
        });
    }
}
