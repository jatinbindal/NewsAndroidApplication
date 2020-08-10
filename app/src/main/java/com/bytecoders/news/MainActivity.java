
package com.bytecoders.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SourceAdapter.ItemClickCallback {


    private ProgressDialog bar;
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

    private final String KEY_RECYCLER_STATE = "recycler_state";

    private static Bundle mBundleRecyclerViewState;

    Parcelable mListState;
    private RecyclerView recyclerView;
    //private ArticleAdapter adapter;
    private SourceAdapter adapter;
    TextView text;
    private String apiKey;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiKey  = getString(R.string.api_key);

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);


        bar = new ProgressDialog(this);
        bar.setMessage("Fetching News Sources.....");
        bar.show();
        if (savedInstanceState != null) {
            recyclerView.setAdapter(adapter);
        }
        if (info == null) {
            Toast.makeText(this, "Please Connect to Internet and Refresh", Toast.LENGTH_LONG).show();
            bar.dismiss();
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SourceList> newsSourceCall = apiService.loadSources();
        newsSourceCall.enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {

                if (response.code() == 200) {
                    SourceList sourceList = response.body();
                    adapter = new SourceAdapter(sourceList.sources, getApplicationContext());
                    adapter.setItemClickCallback(new SourceAdapter.ItemClickCallback() {

                        @Override
                        public void onItemClick(String id) {
                            adapter.setItemClickCallback(this);
                            Intent intent = new Intent(getApplicationContext(), News.class);
                            intent.putExtra("id", id);


                            startActivity(intent);
                        }
                    });
                    bar.dismiss();
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Unable to fetch information " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SourceList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to fetch information ", Toast.LENGTH_SHORT).show();
                Log.d("fail", "on Response " + t.getMessage());
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void onItemClick(String id) {
        adapter.setItemClickCallback(this);
        Intent intent = new Intent(this, News.class);
        intent.putExtra("id", id);
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