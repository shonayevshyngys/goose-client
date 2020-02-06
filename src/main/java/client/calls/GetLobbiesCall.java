package client.calls;

import client.RetrofitWrapper;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import java.util.ArrayList;

public class GetLobbiesCall {
    private ArrayList<String> names;
    private boolean isSuccessful;

    public GetLobbiesCall() {
        isSuccessful = false;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public boolean getLobbies(String token){
        RetrofitWrapper.api.getLobbies(token).subscribe(new SingleObserver<Response<ArrayList<String>>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(Response<ArrayList<String>> arrayListResponse) {
                if(arrayListResponse.code() == 200){
                    isSuccessful = true;
                    names = arrayListResponse.body();
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
