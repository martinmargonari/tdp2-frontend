package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.activities.MyCourseForumThreadPostsActivity;
import com.example.margonari.tdp2_frontend.domain.AttachFile;
import com.example.margonari.tdp2_frontend.domain.ForumPost;

import java.util.ArrayList;

/**
 * Created by Margonari on 30/10/2016.
 */

public class ForumPostAdapter extends RecyclerView
        .Adapter<ForumPostAdapter
        .ForumPostHolder> {
    private static String LOG_TAG = "ForumPostAdapter";
    private ArrayList<ForumPost> mDataset;
    private Context mCOntext;

    public static class ForumPostHolder extends RecyclerView.ViewHolder {
        TextView post_author;
        TextView post_content;
        Button boton_adjuntos;

        Context context;

        public ForumPostHolder(View itemView) {
            super(itemView);
            post_author = (TextView) itemView.findViewById(R.id.forum_post_author);
            post_content = (TextView) itemView.findViewById(R.id.forum_post_content);
            boton_adjuntos= (Button) itemView.findViewById(R.id.button_files);

            context = itemView.getContext();

        }
    }

    public ForumPostAdapter(ArrayList<ForumPost> myDataset, Context context) {
        mCOntext= context;
        mDataset = myDataset;
    }

    @Override
    public ForumPostAdapter.ForumPostHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forum_post, parent, false);

        ForumPostAdapter.ForumPostHolder dataObjectHolder = new ForumPostAdapter.ForumPostHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final ForumPostAdapter.ForumPostHolder holder, final int position) {
        holder.post_author.setText(mDataset.get(position).getAuthor_id());
        holder.post_content.setText(mDataset.get(position).getContent());
        if(mDataset.get(position).getAttachments().length==0) {
            holder.boton_adjuntos.setVisibility(View.GONE);
        }else {
            holder.boton_adjuntos.setText(mDataset.get(position).getAttachments()[0].getName());
        }
        holder.boton_adjuntos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AttachFile[] arrayOfAttachments= mDataset.get(position).getAttachments();
                if(mDataset.get(position).getAttachments().length!=0) {

                    ((MyCourseForumThreadPostsActivity)mCOntext).downloadFile(mDataset.get(position).getAttachments()[0].getFile_path(),mDataset.get(position).getAttachments()[0].getName());

                }

            }
        });
    };

    public void addItem(ForumPost forumThread, int index) {
        mDataset.add(index, forumThread);
        notifyItemInserted(index);

    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}