package club.bettercoder.questions.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import club.bettercoder.R;
import club.bettercoder.questions.po.Tree;

public class TreeAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private List<Tree> mTree;

    public TreeAdapter(@NonNull Context context, int textViewResourceId, List tree) {
        super(context, textViewResourceId, tree);
        mInflater = LayoutInflater.from(context);
        mTree = tree;
    }

    @Override
    public int getCount() {
        return mTree.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView text;
        ImageView iv_tree_book, iv_tree_expanded, iv_tree_select;
        LinearLayout ll_tree;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.module_list_item_tree, null);
        }
        text = (TextView) convertView.findViewById(R.id.text);
        iv_tree_book = (ImageView) convertView.findViewById(R.id.iv_tree_book);
        iv_tree_expanded = (ImageView) convertView.findViewById(R.id.iv_tree_expanded);
        iv_tree_select = (ImageView) convertView.findViewById(R.id.iv_tree_select);
        ll_tree = (LinearLayout) convertView.findViewById(R.id.ll_tree);
        Tree mt = mTree.get(position);
        if(!mt.isHasChild()){
            iv_tree_expanded.setVisibility(View.GONE);
            iv_tree_select.setVisibility(View.VISIBLE);
            iv_tree_select.setSelected(mt.isSelected());
        }else {
            iv_tree_expanded.setVisibility(View.VISIBLE);
            iv_tree_select.setVisibility(View.GONE);
            iv_tree_expanded.setSelected(mt.isExpanded());
        }
        int level = mt.getLevel();
        ll_tree.setPadding(32 * level - 32, iv_tree_book.getPaddingTop(), 0, iv_tree_book.getPaddingBottom());
        text.setText(mt.getText());
        return convertView;
    }
}
