package r.com.rxjavademo.remote.api;

import r.com.rxjavademo.model.SOAnswersResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SOService {

//   @GET("/answers?order=desc&sort=activity&site=stackoverflow")
//   Call<SOAnswersResponse> getAnswers();
//
//   @GET("/answers?order=desc&sort=activity&site=stackoverflow")
//   Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Observable<SOAnswersResponse> getAnswers();

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Observable<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
}