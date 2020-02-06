package client.calls;

import client.RetrofitWrapper;
import client.model.BaseMessage;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class TestConnectionCall {
    private boolean isConnected;

    public TestConnectionCall() {
        isConnected = false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean testConnection(){
        RetrofitWrapper.api.testConnection().subscribe(new SingleObserver<Response<BaseMessage>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(Response<BaseMessage> baseMessageResponse) {
                if (baseMessageResponse.code() == 200){
                    isConnected = true;
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
        return isConnected;
    }
}
