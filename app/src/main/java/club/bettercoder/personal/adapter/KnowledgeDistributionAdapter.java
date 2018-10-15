package club.bettercoder.personal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import club.bettercoder.R;
import club.bettercoder.widget.RadarView;

public class KnowledgeDistributionAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> summary;
    private List<List<String>> titles;
    private List<List<Double>> data;

    public KnowledgeDistributionAdapter(Context context, List<String> summary, List<List<String>> titles, List<List<Double>> data) {
        mInflater = LayoutInflater.from(context);
        this.titles = titles;
        this.data = data;
        this.summary = summary;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvSummary;
        RadarView rvKnowledgeDistribution;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.module_list_item_knowledge_distribution, null);
        }
        tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
        rvKnowledgeDistribution = (RadarView) convertView.findViewById(R.id.rv_knowledge_distribution);
        tvSummary.setText(summary.get(position));
        rvKnowledgeDistribution.initData(titles.get(position), data.get(position));
        return convertView;
    }
}
