package client;

import client.model.BaseMessage;
import client.model.Credentials;
import client.model.GameInfo;
import client.model.JwtResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.ArrayList;

public interface API {
    @POST("/signup")
    Single<Response<BaseMessage>> signUp(@Body Credentials cr);

    @POST("/login")
    Single<Response<JwtResponse>> login(@Body Credentials cr);

    @GET("/getlobbies")
    Single<Response<ArrayList<String>>> getLobbies(@Header("Authorization") String token);

    @POST("/creategame")
    Single<Response<BaseMessage>> createGame(@Header("Authorization") String token, @Body GameInfo info);

    @GET("/testconnection")
    Single<Response<BaseMessage>> testConnection();
}
