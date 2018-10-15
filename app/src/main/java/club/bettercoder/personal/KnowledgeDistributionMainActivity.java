package club.bettercoder.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import club.bettercoder.R;
import club.bettercoder.base.BaseActivity;
import club.bettercoder.base.BaseCallBack;
import club.bettercoder.personal.adapter.KnowledgeDistributionAdapter;
import club.bettercoder.questions.api.QuestionApi;
import club.bettercoder.questions.model.KnowledgeDistributionBean;
import club.bettercoder.questions.model.KnowledgeDistributionModel;
import club.bettercoder.questions.model.KnowledgeLevel1Bean;
import club.bettercoder.questions.model.KnowledgeLevel1Model;
import club.bettercoder.questions.model.SubjectModel;
import club.bettercoder.questions.po.Subject;

public class KnowledgeDistributionMainActivity extends BaseActivity {
    private static final String TAG = "KnowledgeDistribution";
    private Spinner spKnowledge, spSubject;
    private ListView lvKnowledgeDistribution;
    private List<String> lsSubject, lsKnowledge, lsKnowledgeId;
    private String[] level1TreeId;
    private ArrayAdapter<String> subjectAdapter, knowledgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_list_knowledge_distribution);
    }

    @Override
    protected void initView() {
        spSubject = (Spinner) findViewById(R.id.sp_subject);
        spKnowledge = (Spinner) findViewById(R.id.sp_knowledge);
        lvKnowledgeDistribution = (ListView) findViewById(R.id.lv_knowledge_distribution);
        lsSubject = new ArrayList<>();
        lsSubject.add("请选择知识点");
        lsKnowledge = new ArrayList<>();
        lsKnowledge.add("请选择章节");
        subjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsSubject);
        spSubject.setAdapter(subjectAdapter);
        knowledgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsKnowledge);
        spKnowledge.setAdapter(knowledgeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSubjects();
    }

    private void getSubjects() {
        addRequest(getService(QuestionApi.class).getSubject(), new BaseCallBack<SubjectModel>() {
            @Override
            public void onSuccess200(SubjectModel body) {
                for (Subject subject : body.valid_knowledge_subjects) {
                    lsSubject.add(subject.getName());
                }
                subjectAdapter.notifyDataSetChanged();
                spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            getKnowledge(lsSubject.get(i));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    private void getKnowledge(String subject) {
        subject = subject.toLowerCase();
        addRequest(getService(QuestionApi.class).getKnowledgeLevel1(new KnowledgeLevel1Bean(subject)), new BaseCallBack<KnowledgeLevel1Model>() {
            @Override
            public void onSuccess200(KnowledgeLevel1Model body) {
                level1TreeId = body.level1TreeId.split(",");
                String[] level1Tree = body.level1Tree.split(",");
                lsKnowledge.clear();
                lsKnowledge.add("请选择章节");
                for (String level1 : level1Tree) {
                    lsKnowledge.add(level1);
                }
                knowledgeAdapter.notifyDataSetChanged();
                spKnowledge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            getKnowledgeDistribution(level1TreeId[i - 1]);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    private void getKnowledgeDistribution(String parent) {
        String subject = lsSubject.get(spSubject.getSelectedItemPosition());
        addRequest(getService(QuestionApi.class).getKnowledgeDistribution(new KnowledgeDistributionBean(subject, parent)), new BaseCallBack<KnowledgeDistributionModel>() {
            @Override
            public void onSuccess200(KnowledgeDistributionModel model) {
                KnowledgeDistributionAdapter adapter = new KnowledgeDistributionAdapter(KnowledgeDistributionMainActivity.this, model.getSummary(), model.getTitles(), model.getData());
                lvKnowledgeDistribution.setAdapter(adapter);
            }
        });
    }
}
