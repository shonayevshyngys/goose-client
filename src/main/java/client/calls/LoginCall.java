package client.calls;

import client.RetrofitWrapper;
import client.model.Credentials;
import client.model.JwtResponse;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class LoginCall {
    private String token;
    private boolean isSuccessful;

    public LoginCall() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public boolean login (Credentials cr){
        RetrofitWrapper.api.login(cr).subscribe(new SingleObserver<Response<JwtResponse>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(Response<JwtResponse> jwtResponseResponse) {
                if (jwtResponseResponse.code()==200){
                    isSuccessful = true;
                    token = "Bearer "+ jwtResponseResponse.body().getToken();
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
