package r.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import r.com.rxjavademo.adapter.AnswersAdapter;
import r.com.rxjavademo.model.Item;
import r.com.rxjavademo.model.SOAnswersResponse;
import r.com.rxjavademo.remote.ApiUtils;
import r.com.rxjavademo.remote.api.SOService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private AnswersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mAdapter = new AnswersAdapter(this, new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadAnswers();
    }

    public void loadAnswers() {
//        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
//            @Override
//            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
//
//                if(response.isSuccessful()) {
//                    mAdapter.updateAnswers(response.body().getItems());
//                    Log.d("MainActivity", "posts loaded from API");
//                }else {
//                    int statusCode  = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
////                showErrorMessage();
//                Log.d("MainActivity", "error loading from API");
//
//            }
//        });

        /*

        When making the requests, our anonymous subscriber responds to the
        observable's stream which emits events, in our case SOAnswersResponse.
         The onNext method is then called when our subscriber receives any event
         emitted which is then passed to our adapter.

         */
        mService.getAnswers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SOAnswersResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "=================onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SOAnswersResponse soAnswersResponse) {
                        Log.d("MainActivity", "=================onNext " + soAnswersResponse.getItems());
                        mAdapter.updateAnswers(soAnswersResponse.getItems());
                    }
                });
    }


}
