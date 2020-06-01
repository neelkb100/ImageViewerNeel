package com.example.imageviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.imageutils.ImageLoader;
import com.example.imageviewer.R;
import com.example.imageviewer.model.ResponseModel;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>{

    Context mContext;
    List<ResponseModel> mResponseModels;


    public PhotosAdapter(Context mContext, List<ResponseModel> mResponseModels) {
        this.mContext = mContext;
        this.mResponseModels = mResponseModels;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item, parent, false);

        // Return a new holder instance
        PhotoViewHolder viewHolder = new PhotoViewHolder(contactView);

            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {


        ImageLoader mImageLoader = new ImageLoader(mContext);
        ImageView image = holder.ivImage;

        String imageId = mResponseModels.get(position).getId().toString();

        String url = "https://i.picsum.photos/id/"+imageId+"/300/300.jpg";
        holder.tvAuthor.setText(mResponseModels.get(position).getAuthor());

        mImageLoader.DisplayImage(url, image);



    }

    @Override
    public int getItemCount() {
        return mResponseModels.size();
    }



    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAuthor;

        private ImageView ivImage;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.textViewAuthor);
            ivImage = itemView.findViewById(R.id.imageViewPicture);

        }
    }





}
