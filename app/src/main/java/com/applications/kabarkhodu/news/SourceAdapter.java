package com.applications.kabarkhodu.news;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by my computer on 10/28/2016.
 */
public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder> {
    private List<Source> sources;
   // private int layout;
    private LayoutInflater inflater;
    //private Context c;

    private  ItemClickCallback itemClickCallback;

    public interface ItemClickCallback

    {
        void onItemClick (String id);
    }

        public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback=itemClickCallback;
    }

    public SourceAdapter(List<Source> sources, Context context) {
        this.sources = sources;
        this.inflater=LayoutInflater.from(context);
    }
    public  class SourceViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private   TextView name;
        private View container;
        private ImageView img;
        private String id;



        public SourceViewHolder(View v) {
            super(v);

            name=(TextView)v.findViewById(R.id.text);
            img= (ImageView) v.findViewById(R.id.img);
            container=v.findViewById(R.id.layout);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            TextView text= (TextView) v.findViewById(R.id.text);
            //Intent intent=new Intent(v.getContext(), News.class);
            //intent.putExtra("source",id);
            //v.getContext().startActivity(inten
                    itemClickCallback.onItemClick(id);

            //Toast.makeText(v.getContext(),"click on "+ text.getText(),Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public SourceAdapter.SourceViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
      View view=inflater.inflate(R.layout.card,parent,false);
        return new SourceViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SourceViewHolder holder, final int position) {
        Source source=sources.get(position);
        holder.name.setText(source.getName());
        holder.id=source.getId();
        Context context=holder.img.getContext();
        Picasso.with(context).load(source.getUrlsToLogos().getSmall()).into(holder.img);

    }

    @Override
    public int getItemCount() {

        return sources.size();
    }
}