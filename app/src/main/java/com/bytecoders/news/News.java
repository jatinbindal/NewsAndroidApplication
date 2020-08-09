package com.bytecoders.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends AppCompatActivity implements ArticleAdapter.ItemClickCallback {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private String apiKey;

    ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiKey  = getString(R.string.api_key);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        bar = new ProgressDialog(this);
        bar.setMessage("Getting Latest News");
        final Context c = this;

        bar.show();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Bundle bundle = getIntent().getExtras();
        final String source = bundle.getString("id");
        setTitle(source);
        Call<ArticleList> call = apiService.loadArticles(source, apiKey);
        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                if (response.code() == 200) {
                    ArticleList articleList = response.body();


                    adapter = new ArticleAdapter(articleList.getArticles(), getApplicationContext());
                    adapter.setItemClickCallback(new ArticleAdapter.ItemClickCallback() {
                        @Override
                        public void onItemClick(String url) {

                            adapter.setItemClickCallback(this);
                            Intent intent = new Intent(getApplicationContext(), FullNews.class);
                            intent.putExtra("url", url);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    bar.dismiss();

                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(News.this, "Unable to get article" + response.code(), Toast.LENGTH_SHORT).show();
                }
                Log.d("tag", "on Response " + call.request().url());
            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                // Log error here since request failed
                Log.d("fail", "on Response " + call.request().url());
            }
        });
    }

    @Override
    public void onItemClick(String url) {

        adapter.setItemClickCallback(this);
        Intent intent = new Intent(this, FullNews.class);
        intent.putExtra("url", url);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }
}
class SnapHelperOneByOne extends LinearSnapHelper {

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);

        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        LinearLayoutManager myLayoutManager = (LinearLayoutManager) layoutManager;

        int position1 = myLayoutManager.findFirstVisibleItemPosition();
        int position2 = myLayoutManager.findLastVisibleItemPosition();

        int currentPosition = layoutManager.getPosition(currentView);

        if (velocityX > 400) {
            currentPosition = position2;
        } else if (velocityX < 400) {
            currentPosition = position1;
        }

        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        return currentPosition;
    }
}

