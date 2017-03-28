package com.applications.kabarkhodu.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends AppCompatActivity implements ArticleAdapter.ItemClickCallback {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    //private SourceAdapter adapter;
    TextView text;
    ImageView img;
    private static final String apiKey = "a6580ddc35504d7fad7244f65e3c0977";
    //private  static final String s="the-next-web";

    ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //text= (TextView) findViewById(R.id.check);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bar = new ProgressDialog(this);
        bar.setMessage("Getting Latest News");
        final Context c = this;

        bar.show();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Bundle bundle = getIntent().getExtras();
        String source = bundle.getString("id");
        Toast.makeText(this, "hello " + source, Toast.LENGTH_SHORT).show();
        //
        // text.setText(source);
        setTitle(source);

        Call<ArticleList> call = apiService.loadArticles(source, apiKey);
        //text.setText(call.request().url().toString());
        // Response<SourceList> response =call.execute();
        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                int statuscode = response.code();
                //SourceList sourceList=new SourceList();
                ArticleList articleList = response.body();
                //text.setText("hello");
                //text.setText(sourceList.sources.get(5).getName());

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

                //Picasso.with(getApplicationContext()).load(sourceList.sources.get(5).getUrlsToLogos().getSmall()).into(img);
                //recyclerView.setAdapter(new SourceAdapter(sourceList.sources,R.layout.sources,getApplicationContext()));
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
