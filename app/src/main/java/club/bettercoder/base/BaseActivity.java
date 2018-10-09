package club.bettercoder.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import club.bettercoder.R;
import club.bettercoder.utils.request.RequestManager;
import club.bettercoder.utils.request.RequestStateListener;
import club.bettercoder.utils.request.SimpleCall;
import club.bettercoder.utils.request.SimpleCallBack;
import club.bettercoder.widget.MessageDialog;

/**
 * Created by legend on 18/1/2.
 */

public abstract class BaseActivity extends FragmentActivity{
    private View mRoot;
    private FrameLayout fl_container;
    private RelativeLayout rl_title_bar;
    private TextView tv_title, tv_left1;
    private RLBaseHandler mParentHandler;
    private ProgressBar pb_loading;
    public Dialog mRetryDialog;
    protected Map<SimpleCall, RetryCallInfo> mRetryCallMap = new HashMap<>();
    public static final String ICON_BACK = "&#xe625;";
    /**
     * 请求失败，不显示异常页面
     */
    public static final int NO_EMPTY_VIEW = -1;
    private static final int WHAT_DISMISS_LOADING_DIALOG = 0x1001;
    private static final long LOADING_DISMISS_DELAY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInternal();
    }

    @Override
    public void setContentView(int layoutResID) {
        mRoot = LayoutInflater.from(this).inflate(R.layout.module_activity_base, null);
        super.setContentView(mRoot);
        rl_title_bar = (RelativeLayout) mRoot.findViewWithTag("rl_title_bar");
        fl_container = (FrameLayout) mRoot.findViewWithTag("fl_container");
        tv_title = (TextView) mRoot.findViewWithTag("tv_title");
        tv_left1 = (TextView) mRoot.findViewWithTag("tv_left1");
        pb_loading = (ProgressBar) mRoot.findViewWithTag("pb_loading");
        setLeftText(ICON_BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addViewToContent(layoutResID);
        initView();
    }

    private void addViewToContent(View view, ViewGroup.LayoutParams params) {
        fl_container.addView(view, params);
    }

    private void addViewToContent(int layoutResID) {
        addViewToContent(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    private void addViewToContent(View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addViewToContent(view, params);
    }

    protected abstract void initView();

    protected void setTitleString(String title) {
        tv_title.setText(title);
    }

    protected void setTitleId(int id) {
        setTitleString(getString(id));
    }

    protected void showTitleBar() {
        rl_title_bar.setVisibility(View.VISIBLE);
    }

    protected void hideTitleBar() {
        rl_title_bar.setVisibility(View.GONE);
    }

    protected void hideLeft() {
        tv_left1.setVisibility(View.GONE);
    }

    /**
     * 左边只有一个
     *
     * @param text 为""时隐藏
     * @return 显示的TextView
     */
    protected TextView setLeftText(String text) {
        if (isIcon(text)) {
            setIconTypeface(tv_left1);
            tv_left1.setText(getHtmlText(text));
        } else {
            tv_left1.setTypeface(Typeface.DEFAULT);
            tv_left1.setText(text);
        }
        tv_left1.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        return tv_left1;
    }

    private boolean isIcon(String s) {
        return s.length() == 8 && s.startsWith("&#") && s.endsWith(";");
    }

    protected TextView setIconTypeface(TextView tv) {
        Typeface tf = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        tv.setTypeface(tf);
        return tv;
    }

    private Spanned getHtmlText(String which) {
        return Html.fromHtml(which);
    }

    private class RetryCallInfo {
        SimpleCallBack callBack;
        boolean cancelable;
        int errorViewContainer;
    }

    /**
     * 创建一个后台、不可取消、需要重试、无异常页面的请求
     *
     * @param call
     * @param callBack
     */
    public void addRequest(SimpleCall call, SimpleCallBack callBack) {
        addRequest(call, callBack, false);
    }

    /**
     * 创建一个不可取消、需要重试、无异常页面的请求
     *
     * @param call
     * @param callBack
     * @param isForeground 是否为前台请求
     */
    public void addRequest(SimpleCall call, SimpleCallBack callBack, boolean isForeground) {
        addRequest(call, callBack, isForeground, false);
    }

    /**
     * 创建一个需要重试，无异常页面的请求
     *
     * @param call
     * @param callBack
     * @param isForeground 是否为前台请求
     * @param cancelable   是否可以被取消
     */
    public void addRequest(SimpleCall call, SimpleCallBack callBack, boolean isForeground, boolean cancelable) {
        addRequest(call, callBack, isForeground, cancelable, true);
    }

    /**
     * 创建一个请求，若需要重试，则无异常页面
     *
     * @param call
     * @param callBack
     * @param isForeground 是否为前台请求
     * @param cancelable   是否可以被取消
     * @param needRetry    是否需要重试
     */
    public void addRequest(SimpleCall call, SimpleCallBack callBack, boolean isForeground,
                           boolean cancelable, boolean needRetry) {
        addRequest(call, callBack, isForeground, cancelable, needRetry, NO_EMPTY_VIEW);
    }

    /**
     * 创建一个请求
     *
     * @param call
     * @param callBack
     * @param isForeground       是否为前台请求
     * @param cancelable         是否可以被取消
     * @param needRetry          是否需要重试
     * @param errorViewContainer 将要装载重试页面的容器ID，此ID需要为当前页面的元素。若ID未找到或非ViewGroup，则默认FULL_EMPTY_VIEW
     */
    public void addRequest(final SimpleCall call, final SimpleCallBack callBack, final boolean isForeground,
                           final boolean cancelable, final boolean needRetry, final int errorViewContainer) {
        RequestManager.create(BaseApplication.sAppContext).addRequest(call, callBack, new RequestStateListener() {
            @Override
            public void onStart() {
                refreshDialogState();
                pb_loading.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSuccess(Object body) {
                if (mRetryCallMap.containsKey(call)) {
                    mRetryCallMap.remove(call);
                }
                if(mRetryCallMap.size()==0){
                    pb_loading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure() {
                if (needRetry && !mRetryCallMap.containsKey(call)) {
                    RetryCallInfo info = new RetryCallInfo();
                    info.callBack = callBack;
                    info.cancelable = cancelable;
                    info.errorViewContainer = errorViewContainer;
                    mRetryCallMap.put(call, info);
                }
            }
            @Override
            public void onFinish() {
                refreshDialogState();
            }
        });
    }

    public <T> T getService(Class<T> service) {
        return RequestManager.create(BaseApplication.sAppContext).getService(service);
    }

    private void initInternal() {
        mParentHandler = new RLBaseHandler(this);
        // 初始化重试框
        mRetryDialog = getRetryDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    mRetryCallMap.clear();
                    pb_loading.setVisibility(View.GONE);
                } else {
                    retryCall(mRetryCallMap);
                }
            }
        }, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mRetryCallMap.clear();
                pb_loading.setVisibility(View.GONE);
            }
        });
    }

    protected Dialog getRetryDialog(final DialogInterface.OnClickListener onButtonListener, DialogInterface.OnCancelListener onCancelListener) {
        final MessageDialog dialog = new MessageDialog(this);
        dialog.setMessage(R.string.app_dialog_retry_msg);
        dialog.setPositiveButton(R.string.app_dialog_retry_positive_btn);
        dialog.setOnButtonClickListener(new MessageDialog.OnButtonClickListener() {
            @Override
            public void onClick(int which) {
                onButtonListener.onClick(dialog, which);
            }
        });
        dialog.setOnCancelListener(onCancelListener);
        return dialog;
    }

    private void retryCall(Map<SimpleCall, RetryCallInfo> callMap) {
        Set<Map.Entry<SimpleCall, RetryCallInfo>> set = new HashSet<>();
        set.addAll(callMap.entrySet());
        for (Map.Entry<SimpleCall, RetryCallInfo> entry : set) {
            SimpleCall call = entry.getKey();
            RetryCallInfo info = entry.getValue();
            callMap.remove(call);
            addRequest(call.clone(), info.callBack, true, info.cancelable, true, info.errorViewContainer);
        }
    }

    /**
     * 刷新loading状态
     */
    private void refreshDialogState() {
        boolean needShow = mRetryCallMap.size() > 0;
        mParentHandler.removeMessages(WHAT_DISMISS_LOADING_DIALOG);
        if (needShow) {
            mParentHandler.sendEmptyMessageDelayed(WHAT_DISMISS_LOADING_DIALOG, LOADING_DISMISS_DELAY);
        }
    }

    private static class RLBaseHandler extends Handler {
        private WeakReference<BaseActivity> mOuter;

        public RLBaseHandler(BaseActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity outer = mOuter.get();
            if (outer != null) {
                if (msg.what == WHAT_DISMISS_LOADING_DIALOG) {// 避免请求框高频闪动
                    try {
                        if (!outer.mRetryDialog.isShowing() && outer.mRetryCallMap.size() > 0) {
                            outer.mRetryDialog.show();
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    @Override
    public void finish() {
        if (mRetryDialog.isShowing()) {
            mRetryDialog.dismiss();
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRetryCallMap.clear();
    }
}
