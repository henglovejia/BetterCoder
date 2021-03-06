package club.bettercoder.utils.request;

import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import club.bettercoder.utils.PackageUtils;
import club.bettercoder.utils.request.converters.SimpleConverterFactory;
import club.bettercoder.utils.request.cookie.PersistentCookieJar;

/**
 * Created by heng on 2018/9/10.
 */

public class RequestManager {
    private static RequestManager rManager;
    private Handler mHandler;
    private OkHttpClient mClient;
    private PersistentCookieJar mCookieJar;
    private Map<String, Retrofit> mRetrofitMap = new HashMap<>();
//        private static final String BASE_HOST = "http://192.168.31.84:8080/BetterCoder/";
    private static final String BASE_HOST = "https://wechat.bettercoder.club/BetterCoder/";
    private static int DEFAULT_NETWORK_TIMEOUT = 10;

    private RequestManager(Context context) {
        mHandler = new Handler();
        mCookieJar = new PersistentCookieJar(context);
        mClient = new OkHttpClient();
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.NONE;
        if (PackageUtils.isApkDebugable(context)) {
            level = HttpLoggingInterceptor.Level.BODY;
        }
        mClient = mClient.newBuilder()
                .cookieJar(mCookieJar)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(level))
                .connectTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build();
        getRetrofit(BASE_HOST);
    }

    public static RequestManager create(Context context) {
        if (rManager == null)
            rManager = new RequestManager(context);
        return rManager;
    }

    public <T> T getService(Class<T> service) {
        return getRetrofit(BASE_HOST).create(service);
    }

    public <R> R getService(String endPoint, Class<R> c) {
        return getRetrofit(endPoint).create(c);
    }

    private Retrofit getRetrofit(String endPoint) {
        if (mRetrofitMap.containsKey(endPoint)) {
            return mRetrofitMap.get(endPoint);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .client(mClient)
                .addCallAdapterFactory(new ErrorHandlingCallAdapter.ErrorHandlingCallAdapterFactory())
                .addConverterFactory(SimpleConverterFactory.create())
                .build();
        mRetrofitMap.put(endPoint, retrofit);
        return retrofit;
    }

    public <R> void addRequest(final SimpleCall call, final SimpleCallBack<R> callBack,
                               final RequestStateListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        call.enqueue(new ErrorHandlingCallBack<R>() {
            @Override
            public void success(final Response<R> response) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onSuccess(response.body());
                        }
                        if (listener != null) {
                            listener.onSuccess(response.body());
                            listener.onFinish();
                        }
                    }
                });
            }

            @Override
            public void unauthenticated(final Response<?> response) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFailure(response.body());
                        }
                        if (listener != null) {
                            listener.onFailure();
                            listener.onFinish();
                        }
                    }
                });
            }

            @Override
            public void clientError(final Response<?> response) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFailure(response.body());
                        }
                        if (listener != null) {
                            listener.onFailure();
                            listener.onFinish();
                        }
                    }
                });
            }

            @Override
            public void serverError(final Response<?> response) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFailure(response.body());
                        }
                        if (listener != null) {
                            listener.onFailure();
                            listener.onFinish();
                        }
                    }
                });
            }

            @Override
            public void networkError(final IOException e) {
                e.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onError(e);
                        }
                        if (listener != null) {
                            listener.onFailure();
                            listener.onFinish();
                        }
                    }
                });
            }

            @Override
            public void unexpectedError(final Throwable t) {
                t.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onError(new Exception(t));
                        }
                        if (listener != null) {
                            listener.onFailure();
                            listener.onFinish();
                        }
                    }
                });
            }
        });
    }
}
