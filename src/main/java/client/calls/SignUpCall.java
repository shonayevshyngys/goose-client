package client.calls;

import client.RetrofitWrapper;
import client.model.BaseMessage;
import client.model.Credentials;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class SignUpCall {
    private boolean isSuccessful;

    public SignUpCall() {
        isSuccessful = false;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public boolean signUp(Credentials cr){
        RetrofitWrapper.api.signUp(cr).subscribe(new SingleObserver<Response<BaseMessage>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(Response<BaseMessage> baseMessageResponse) {
                if (baseMessageResponse.code() == 201){
                    isSuccessful = true;
                }
                else {
                    isSuccessful = false;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                isSuccessful = false;
            }
        });
        return isSuccessful;
    }
}
