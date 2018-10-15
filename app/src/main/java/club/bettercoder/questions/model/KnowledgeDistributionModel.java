package club.bettercoder.questions.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import club.bettercoder.base.BaseModel;

public class KnowledgeDistributionModel extends BaseModel {
    public List<String> distribute;
    private List<List<String>> titles;
    private List<List<Double>> data;
    private List<String> summary;


    private void initData() {
        summary = new ArrayList<>(distribute.size());
        titles = new ArrayList<>(distribute.size());
        data = new ArrayList<>(distribute.size());
        for (String item : distribute) {
            String[] items = item.split("=");
            summary.add(items[0]);
            List<String> titleItem = new ArrayList<>();
            List<Double> dataItem = new ArrayList<>();
            if (items.length > 1) {
                for (String dItem : items[1].split(",")) {
                    String[] dItems = dItem.split("\\|");
                    titleItem.add(dItems[0]);
                    if (dItems.length == 2) {
                        dataItem.add(Double.parseDouble(dItems[1]));
                    } else {
                        dataItem.add(0.0);
                    }
                }
            }
            titles.add(titleItem);
            data.add(dataItem);
        }
    }

    public List<String> getSummary() {
        if (summary == null) {
            initData();
        }
        return summary;
    }

    public List<List<String>> getTitles() {
        if (titles == null) {
            initData();
        }
        return titles;
    }

    public List<List<Double>> getData() {
        if (titles == null) {
            initData();
        }
        return data;
    }
}
