package test.bank.bettercoder.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.bank.bettercoder.utils.request.RequestManager;
import test.bank.bettercoder.utils.request.RequestStateListener;
import test.bank.bettercoder.utils.request.SimpleCall;
import test.bank.bettercoder.utils.request.SimpleCallBack;
import test.bank.bettercoder.widget.LoadingDialog;

public class BaseActivity extends AppCompatActivity {
    protected Dialog mLoadingDialog, mRetryDialog;
    private RLBaseHandler mParentHandler;
    protected List<SimpleCall> mCancelableCallList = new ArrayList<>();
    protected List<SimpleCall> mUncancelableCallList = new ArrayList<>();

    protected Map<SimpleCall, RetryCallInfo> mRetryCallMap = new HashMap<>();
    protected Map<SimpleCall, RetryCallInfo> mAutoRetryCallMap = new HashMap<>();
    /**
     * 请求失败，不显示异常页面
     */
    public static final int NO_EMPTY_VIEW = -1;

    private static final long LOADING_DISMISS_DELAY = 200;
    private static final int WHAT_DISMISS_LOADINGDIALOG = 0x1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInternal();
    }
    /**
     * 创建一个前台、不可取消、需要重试、无异常页面的请求
     *
     * @param call
     * @param callBack
     */
    public void addRequest(SimpleCall call, SimpleCallBack callBack) {
        addRequest(call, callBack, true);
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
     * 创建一个后台请求。该请求可以被取消，不需要重试
     *
     * @param call
     * @param callBack
     */
    public void addBackgroundRequest(SimpleCall call, SimpleCallBack callBack) {
        addRequest(call, callBack, false, true, false, NO_EMPTY_VIEW);
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
        RequestManager.create(this).addRequest(call, callBack, new RequestStateListener() {
            @Override
            public void onStart() {
                if (isForeground) {
                    if (cancelable) {
                        mCancelableCallList.add(call);
                    } else {
                        mUncancelableCallList.add(call);
                    }
                    refreshDialogState();
                }
            }

            @Override
            public void onFinish() {
                if (isForeground) {
                    if (mCancelableCallList.contains(call)) {
                        mCancelableCallList.remove(call);
                    } else if (mUncancelableCallList.contains(call)) {
                        mUncancelableCallList.remove(call);
                    }
                    refreshDialogState();
                }
            }

            @Override
            public void onSuccess(Object body) {
                if (mRetryCallMap.containsKey(call)) {
                    mRetryCallMap.remove(call);
                }
                if (needAutoRetry(body) && !mRetryCallMap.containsKey(call)) {
                    RetryCallInfo info = new RetryCallInfo();
                    info.callBack = callBack;
                    info.cancelable = cancelable;
                    info.errorViewContainer = errorViewContainer;
                    mAutoRetryCallMap.put(call, info);
                }
            }

            @Override
            public void onFailure() {
                if (isForeground && needRetry && !mRetryCallMap.containsKey(call)) {
                    RetryCallInfo info = new RetryCallInfo();
                    info.callBack = callBack;
                    info.cancelable = cancelable;
                    info.errorViewContainer = errorViewContainer;
                    mRetryCallMap.put(call, info);
                }
            }
        });
    }

    /**
     * 取消前台、可以被取消的请求
     */
    public void cancelRequest() {
        for (SimpleCall call : mCancelableCallList) {
            call.cancel();
        }
    }

    /**
     * 刷新loading状态
     */
    private void refreshDialogState() {
        boolean needShow = mCancelableCallList.size() > 0 || mUncancelableCallList.size() > 0;
        mParentHandler.removeMessages(WHAT_DISMISS_LOADINGDIALOG);
        if (!mLoadingDialog.isShowing() && needShow) {
            mLoadingDialog.show();
        } else {
            mParentHandler.sendEmptyMessageDelayed(WHAT_DISMISS_LOADINGDIALOG, LOADING_DISMISS_DELAY);
        }
    }

    private class RetryCallInfo {
        SimpleCallBack callBack;
        boolean cancelable;
        int errorViewContainer;
    }

    protected boolean needAutoRetry(Object body) {
        return false;
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
                if (msg.what == WHAT_DISMISS_LOADINGDIALOG) {// 避免请求框高频闪动
                    try {
                        boolean needShow = outer.mCancelableCallList.size() > 0 || outer.mUncancelableCallList.size() > 0;
                        if (outer.mLoadingDialog.isShowing() && !needShow) {
                            outer.mLoadingDialog.dismiss();
                            if (!outer.mRetryDialog.isShowing() && outer.mRetryCallMap.size() > 0) {
                                outer.mRetryDialog.show();
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    public <T> T getService(Class<T> service){
        return RequestManager.create(this).getService(service);
    }
    private void initInternal() {
        mParentHandler = new RLBaseHandler(this);
        // 初始化加载框
        mLoadingDialog = getLoadingDialog();
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (mLoadingDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    // 拦截在加载框显示过程中的物理返回键，用于下面做取消的动作
                    return true;
                } else if (mLoadingDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK &&
                        keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cancelRequest();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
    protected Dialog getLoadingDialog() {
        return new LoadingDialog(this);
    }
}
