package club.bettercoder.utils.request;

public interface RequestStateListener<T> {

    void onStart();
    void onFinish();
    void onSuccess(T body);
    void onFailure();
}
