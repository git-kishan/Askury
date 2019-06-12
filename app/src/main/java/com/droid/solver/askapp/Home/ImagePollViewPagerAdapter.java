package com.droid.solver.askapp.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePollViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String [] imageUrlList;
    ImagePollViewPagerAdapter (Context context,String [] imageUrlList){
        this.context=context;
        this.imageUrlList=imageUrlList;
        if(context!=null)
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrlList.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view=inflater.inflate(R.layout.view_pager_layout, container,false);
        ImageView imageView=view.findViewById(R.id.image_view);
        GlideApp.with(context).load(imageUrlList[position]).into(imageView);
        container.addView(view);
        PhotoViewAttacher attacher=new PhotoViewAttacher(imageView);
        attacher.update();
        return view;

    }
}
