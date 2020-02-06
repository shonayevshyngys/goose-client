package client.calls;

import client.RetrofitWrapper;
import client.model.BaseMessage;
import client.model.GameInfo;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class CreateGameCall {
    private boolean isSuccessful;
    private String message;

    public CreateGameCall() {
        isSuccessful = false;
        message = "";
    }

    public CreateGameCall(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean createGame(String gameName, String token){
        GameInfo name = new GameInfo(gameName);
        RetrofitWrapper.api.createGame(token, name).subscribe(new SingleObserver<Response<BaseMessage>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(Response<BaseMessage> baseMessageResponse) {
                if (baseMessageResponse.code() == 200){
                    isSuccessful = true;
                }
                else {
                    System.out.println("Bad code");
                    isSuccessful = false;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("failure");
                isSuccessful = false;
            }
        });
        return isSuccessful;
    }
}
