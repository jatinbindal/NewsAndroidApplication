
package com.applications.kabarkhodu.news;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SourceAdapter.ItemClickCallback {


    private ProgressDialog bar;
    RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(this);

    private final String KEY_RECYCLER_STATE = "recycler_state";

    private static Bundle mBundleRecyclerViewState;

    Parcelable mListState;
    private RecyclerView recyclerView;
    //private ArticleAdapter adapter;
    private SourceAdapter adapter;
    TextView text;
    ImageView img;
    private static final String apiKey="a6580ddc35504d7fad7244f65e3c0977";
    private  static final String s="the-next-web";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //Toast.makeText(this,"OnCreate",Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        //text= (TextView) findViewById(R.id.check);
        img= (ImageView) findViewById(R.id.img);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);


        bar=new ProgressDialog(this);
        bar.setMessage("Fetching News Sources.....");
        bar.show();
        if(savedInstanceState!=null)
        {
            recyclerView.setAdapter(adapter);
        }
        if (info == null)
        {
            Toast.makeText(this,"Please Connect to Internet and Refresh",Toast.LENGTH_LONG).show();
            /*AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Connect to wifi or quit")
                    .setCancelable(false)
                    .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
*/
            bar.dismiss();
        }

        final Context c=this;
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        SourceList sourceList;
        //SourceList list= apiService.loadSources();

        Call<SourceList> call = apiService.loadSources();
       // Response<SourceList> response =call.execute();
        call.enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList>call, Response<SourceList> response) {
                int statuscode=response.code();
                //SourceList sourceList=new SourceList();
                 SourceList sourceList= response.body();
                adapter = new SourceAdapter(sourceList.sources,getApplicationContext());
                adapter.setItemClickCallback(new SourceAdapter.ItemClickCallback() {

                    @Override
                    public void onItemClick(String id) {
                        adapter.setItemClickCallback(this);
                        Intent intent =new Intent(getApplicationContext(),News.class);
                        intent.putExtra("id",id);


                        startActivity(intent);
                    }
                });


                bar.dismiss();
                recyclerView.setAdapter(adapter);
                //Picasso.with(getApplicationContext()).load(sourceList.sources.get(5).getUrlsToLogos().getSmall()).into(img);
                //recyclerView.setAdapter(new SourceAdapter(sourceList.sources,R.layout.sources,getApplicationContext()));
                Log.d("tag", "on Response " + statuscode);
            }

            @Override
            public void onFailure(Call<SourceList>call, Throwable t) {
                // Log error here since request failed
                Log.d("fail", "on Response " + t.getMessage());
            }
        });
        /*



*/
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "Saving", Toast.LENGTH_SHORT).show();
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable("myState",mListState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        Toast.makeText(this, "Restore", Toast.LENGTH_SHORT).show();
        if(savedState != null)
        {
            mListState = savedState.getParcelable("myState");
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();


        if (mListState != null) {
            Toast.makeText(this,"no null "+ mListState.toString(),Toast.LENGTH_SHORT).show();
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }
*/
@Override
protected void onPause()
{
    super.onPause();

    // save RecyclerView state
    mBundleRecyclerViewState = new Bundle();
    //Toast.makeText(this,"onpause",Toast.LENGTH_SHORT).show();
    Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
    mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

}

    @Override
    protected void onResume()
    {
        super.onResume();
        //Toast.makeText(this,"onresume",Toast.LENGTH_SHORT).show();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void onItemClick(String id) {
        adapter.setItemClickCallback(this);
        Intent intent =new Intent(this,News.class);
        intent.putExtra("id",id);
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