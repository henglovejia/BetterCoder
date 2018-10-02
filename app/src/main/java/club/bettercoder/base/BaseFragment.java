package club.bettercoder.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import club.bettercoder.main.MainActivity;
import club.bettercoder.R;
import club.bettercoder.utils.request.RequestManager;
import club.bettercoder.utils.request.RequestStateListener;
import club.bettercoder.utils.request.SimpleCall;
import club.bettercoder.utils.request.SimpleCallBack;
import club.bettercoder.widget.MessageDialog;

/**
 * Created by heng on 18/9/13
 */

public abstract class BaseFragment extends Fragment {
    private String TAG = "BcBaseFragment";
    private RLBaseHandler mParentHandler;
    public Dialog mRetryDialog;
    protected View view;
    protected Map<SimpleCall, RetryCallInfo> mRetryCallMap = new HashMap<>();
    /**
     * 请求失败，不显示异常页面
     */
    public static final int NO_EMPTY_VIEW = -1;
    private static final int WHAT_DISMISS_LOADING_DIALOG = 0x1001;
    private static final long LOADING_DISMISS_DELAY = 200;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initInternal();
        view = inflater.inflate(chooseLayout(), container, false);
        initView();
        return view;
    }

    public abstract int chooseLayout();

    public abstract void initView();

    public abstract void onStartInit();

    public abstract void initClickListener();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initClickListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        onStartInit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            }

            @Override
            public void onSuccess(Object body) {
                if (mRetryCallMap.containsKey(call)) {
                    mRetryCallMap.remove(call);
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

    private class RetryCallInfo {
        SimpleCallBack callBack;
        boolean cancelable;
        int errorViewContainer;
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
                } else {
                    retryCall(mRetryCallMap);
                }
            }
        }, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mRetryCallMap.clear();
            }
        });
    }

    protected Dialog getRetryDialog(final DialogInterface.OnClickListener onButtonListener, DialogInterface.OnCancelListener onCancelListener) {
        final MessageDialog dialog = new MessageDialog(MainActivity.sActivityContext);
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
        private WeakReference<BaseFragment> mOuter;

        public RLBaseHandler(BaseFragment fragment) {
            mOuter = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseFragment outer = mOuter.get();
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
}
