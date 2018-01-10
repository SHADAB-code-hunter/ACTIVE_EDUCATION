package com.gt.active_education;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import extras.Constants;
import network.VolleySingleton;
import pojo.Gallery_Model;
import utilities.UrlEndpoints;

/**
 *
 */
public class Big_ImgView_Activity extends AppCompatActivity {

    public static final String TAG = Big_ImgView_Activity.class.getSimpleName();
    public static final int PICK_IMAGE = 1;
    public static final String DEFAULT_IMAGES_FOLDER = "default_images";
    public static final String BITMAPS = "bitmaps";
    public ArrayList<Gallery_Model> quiz_Ans_List;
    static final BitmapFactory.Options BITMAP_FACTORY_OPTIONS;
    private String strimg;
    
    static {
        BITMAP_FACTORY_OPTIONS = new BitmapFactory.Options();
        BITMAP_FACTORY_OPTIONS.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    private ViewPager viewPager;
    private ImageViewPagerAdapter imageViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_imgview);
        quiz_Ans_List = this.getIntent().getParcelableArrayListExtra("list");
        String  poss= this.getIntent().getStringExtra("position_click");
        String st_page=this.getIntent().getStringExtra("click_page");
        strimg=this.getIntent().getStringExtra("URL");
        //Log.d("popodp",""+poss);
        imageViewPagerAdapter = new ImageViewPagerAdapter(quiz_Ans_List,st_page);

        viewPager = (ViewPager) findViewById(R.id.pager);
       // viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(imageViewPagerAdapter);

        viewPager.setCurrentItem(Integer.parseInt(poss));
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       /* if(R.id.add_photo == id) {
            Intent intent = new Intent();
            intent.setType("image");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.add_photo)), PICK_IMAGE);
        } else if(R.id.clear == id) {
            List<Drawable> quiz_Ans_List = imageViewPagerAdapter.quiz_Ans_List;
            quiz_Ans_List.clear();
            addDefaultImages(quiz_Ans_List);
            imageViewPagerAdapter.notifyDataSetChanged();
        } else if(R.id.info == id) {
            DialogFragment infoDialogFragment = new InfoDialogFragment();
            infoDialogFragment.show(getFragmentManager(), "info");
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int n = imageViewPagerAdapter.quiz_Ans_List.size();
        Parcelable[] parcelables = new Parcelable[n];
      /*  for(int i = 0; i < n; i++) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageViewPagerAdapter.quiz_Ans_List.get(i);
            parcelables[i] = bitmapDrawable.getBitmap();
        }*/
        outState.putParcelableArray(BITMAPS, parcelables);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE) {
            if(data != null) {
                Uri uri = data.getData();
                //Log.d(TAG, "Picked image: " + String.valueOf(uri));

                if (uri != null) {
                    try {
                        InputStream is = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is, null, BITMAP_FACTORY_OPTIONS);
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        //Drawable drawable = new BitmapDrawable(getResources(), is);

                        // Add drawable to end of list
                   //     imageViewPagerAdapter.quiz_Ans_List.add(drawable);
                        imageViewPagerAdapter.notifyDataSetChanged();

                        // Scroll to the end of list
                        viewPager.setCurrentItem(imageViewPagerAdapter.getCount() - 1);
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, "File not found", e);
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     *
     * @param quiz_Ans_List
     */
    private void addDefaultImages(List<Drawable> quiz_Ans_List) {
        // Note: Images are stored as assets instead of as resources
        // This because content should be in its raw format as opposed to UI elements
        // and to have more control over the decoding of image files

        AssetManager assets = getAssets();
        Resources resources = getResources();
        try {
            List<String> images = Arrays.asList(assets.list(DEFAULT_IMAGES_FOLDER));
            Collections.sort(images);
            for(String image: images) {
                InputStream is = null;
                try {
                    is = assets.open(DEFAULT_IMAGES_FOLDER + "/" + image);
                    Bitmap bitmap = BitmapFactory.decodeStream(is, null, BITMAP_FACTORY_OPTIONS);
                    quiz_Ans_List.add(new BitmapDrawable(resources, bitmap));
                } finally {
                    if(is != null) {
                        try {
                            is.close();
                        } catch(IOException e) {
                        }
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private class ImageViewPagerAdapter extends PagerAdapter {

        private ArrayList<Gallery_Model> quiz_Ans_List;
        private VolleySingleton mVolleySingleton;
        private ImageLoader mImageLoader;
        public  ImageView imageView;
        public  String st_page;
    
        public ImageViewPagerAdapter(ArrayList<Gallery_Model> quiz_Ans_List,String st_page) {
            this.quiz_Ans_List = quiz_Ans_List;
            mVolleySingleton = VolleySingleton.getInstance();
            mImageLoader = mVolleySingleton.getImageLoader();
            this.st_page=st_page;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.page_image, null);
            container.addView(view);

            imageView = (ImageView) view.findViewById(R.id.image);
            //imageView.setImageDrawable();
            String str_img = quiz_Ans_List.get(position).getImage_name();
            if (str_img != null) {

                if(st_page.equals("Gallery"))
                {
                   // String strimg = UrlEndpoints.GALLERY_IMG_PATH + str_img;
                    Log.d("strimg", strimg+"  "+st_page+" "+strimg+str_img);
                   if (!strimg.equals(Constants.NA)) {
                    Log.d("strimg", strimg);
                    mImageLoader.get(strimg+str_img, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            imageView.setImageBitmap(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    }

                } else if(st_page.equals("Banner")) {
                    String strimg = UrlEndpoints.GALLERY_IMG_PATH + str_img;
                    //Log.d("strimg", strimg);
                    if (!strimg.equals(Constants.NA)) {
                        //Log.d("strimg", strimg);
                        mImageLoader.get(strimg, new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                imageView.setImageBitmap(response.getBitmap());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    }
                }else if(st_page.equals("AE_Gallery"))
                {
                  //  Log.d("strimg", strimg+"  "+st_page+" "+strimg+str_img);
                   /* if (!strimg.equals(Constants.NA)) {
                        */Log.d("str___img", strimg+quiz_Ans_List.get(position).getImage_name());
                    String stsr_img=strimg+quiz_Ans_List.get(position).getImage_name();
                    Glide.with(context).
                            load(stsr_img)
                   . into(imageView);
                  //  }
                }

            }
            ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(context);
            imageView.setOnTouchListener(imageMatrixTouchHandler);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;

            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(0);

            container.removeView(view);
        }

        @Override
        public int getCount() {
            return quiz_Ans_List.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition (Object object) {
            return POSITION_NONE;
        }
    }

/*
    private void loadImages(final String urlThumbnail, final ImageView imageView) {

    }
*/

}
