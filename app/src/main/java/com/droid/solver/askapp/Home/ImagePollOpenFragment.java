package com.droid.solver.askapp.Home;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.droid.solver.askapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePollOpenFragment extends Fragment {

    private View toolbar,bottomNavigation;
    private ImageView backImage;
    private String imageUrl;


    public ImagePollOpenFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_image_poll_open, container, false);
        if(getActivity()!=null)
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getActivity()!=null)
        getActivity().supportStartPostponedEnterTransition();
        Bundle bundle=getArguments();
        String transitionName=null;
        if(bundle!=null) {
            imageUrl = bundle.getString("imageUrl");
            transitionName = bundle.getString("imageTransitionName");
        }
        final Handler handler=new Handler();
        final CardView cardView=view.findViewById(R.id.progress_card);
        final ImageView imageView=view.findViewById(R.id.image_view);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if(transitionName!=null)
            imageView.setTransitionName(transitionName);

        }
        toolbar=getActivity().findViewById(R.id.toolbar_card_view);
        bottomNavigation=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigation.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        Picasso.get().load(imageUrl)
                .noFade()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        final float ratio=(float)bitmap.getHeight()/(float)bitmap.getWidth();
                        final float height=((float)imageView.getHeight()/2)*ratio;
                        final float width=imageView.getWidth()*ratio;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                                layoutParams.height= (int) height;
                                layoutParams.width= (int) width;
                                imageView.setLayoutParams(layoutParams);
                                imageView.setImageBitmap(bitmap);
                                cardView.setVisibility(View.GONE);
                                if(getActivity()!=null) {
                                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
                                    attacher.update();
                                }
                            }
                        });

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(imageUrl).error(R.drawable.ic_placeholder_large)
                                        .into(imageView);

                            }
                        });

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        backImage = view.findViewById(R.id.back_image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity()!=null){
                    getActivity().onBackPressed();
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        if(getActivity()!=null)
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bottomNavigation.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
}
