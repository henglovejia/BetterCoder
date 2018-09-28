package test.bank.bettercoder.questions;

import test.bank.bettercoder.R;
import test.bank.bettercoder.base.BcBaseFragment;
import test.bank.bettercoder.widget.code.CodeView;

public class QuestionMainFragment extends BcBaseFragment {
    private static String TAG = "QuestionMainActivity";
    private CodeView codeView;
    private String testJava="<pre class=\"brush\">public void getCustomerInfo() {\n &ensp;&ensp; &ensp;try {\n &ensp;&ensp; &ensp;} catch (java.io.FileNotFoundException ex) {\n &ensp;&ensp; &ensp;&ensp;&ensp; &ensp;System.out.print(&quot;FileNotFoundException!&quot;);\n &ensp;&ensp; &ensp;} catch (java.io.IOException ex) {\n &ensp;&ensp; &ensp;&ensp;&ensp; &ensp;System.out.print(&quot;IOException!&quot;);\n &ensp;&ensp; &ensp;} catch (java.lang.Exception ex) {\n &ensp;&ensp; &ensp;&ensp;&ensp; &ensp;System.out.print(&quot;Exception!&quot;);\n &ensp;&ensp; &ensp;}\n }\n</pre>";

    @Override
    public int chooseLayout() {
        return R.layout.question_fragment_main;
    }

    @Override
    public void initView() {
        codeView = (CodeView) view.findViewById(R.id.codeView);
    }

    @Override
    public void onStartInit() {
        codeView.showCodeHtmlByCssSelect(testJava,".brush");
    }

    @Override
    public void initClickListener() {
    }
}
