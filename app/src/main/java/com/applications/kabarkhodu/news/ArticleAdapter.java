package com.applications.kabarkhodu.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my computer on 10/28/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<Article> articles;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(String url);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public ArticleAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.inflater = LayoutInflater.from(context);
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private View container;
        private ImageView img;
        private TextView des;
        private TextView author;
        private String url;


        public ArticleViewHolder(View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.article_text);
            img = (ImageView) v.findViewById(R.id.article_img);
            img.setImageResource(R.drawable.loading);
            des = (TextView) v.findViewById(R.id.article_description);
            author = (TextView) v.findViewById(R.id.article_author);
            container = v.findViewById(R.id.article_item);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallback.onItemClick(url);
        }
    }


    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = inflater.inflate(R.layout.article, parent, false);
        view.setMinimumWidth(parent.getMeasuredWidth());
        return new ArticleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ArticleViewHolder holder, final int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.des.setText(article.getDescription());
        holder.url = article.getUrl();
        if (article.getAuthor() == null)
            holder.author.setText("--By Anonymous!");
        else
            holder.author.setText("--By " + article.getAuthor());
        Context context = holder.img.getContext();
        Picasso.with(context).load(article.getUrlToImage()).into(holder.img);

    }

    @Override
    public int getItemCount() {

        return articles.size();
    }
}
