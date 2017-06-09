package east2d.com.myapplication.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/5/9.
 */

public class GlideImagePrepareManager {
    private Executor mExecutor;
    private Map<String,Runnable> mRunnableMap;
    private Map<String,Runnable> mSaveMap;
    private Executor mSaveExecutor;

    private Context mContext;

    private static GlideImagePrepareManager sMGlideImagePrepareManager;

    public static synchronized GlideImagePrepareManager get(){
        if(sMGlideImagePrepareManager ==null){
            synchronized (GlideImagePrepareManager.class){
                if(sMGlideImagePrepareManager ==null){
                    sMGlideImagePrepareManager =new GlideImagePrepareManager();
                }
            }
        }
        return sMGlideImagePrepareManager;
    }

    public void initPrepareManager(Context context){
        if(context!=null&&mContext==null){
            mContext=context.getApplicationContext();
        }
    }


    private GlideImagePrepareManager() {
        mExecutor=Executors.newScheduledThreadPool(2);
        mSaveExecutor=Executors.newSingleThreadExecutor();
        mRunnableMap=new HashMap<>();
        mSaveMap=new HashMap<>();
    }

    public void addTask(final String url){
        if(TextUtils.isEmpty(url)||mRunnableMap.containsKey(url))
            return;
        Runnable newRun=new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(mContext).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(500,500).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(bitmap!=null&&!bitmap.isRecycled()){
                        bitmap.recycle();
                        mRunnableMap.remove(url);
                }

            }
        };
        mRunnableMap.put(url,newRun);
        mExecutor.execute(newRun);
    }

    public void addTask(List<String> urls,boolean reverse){
        if(urls==null||urls.isEmpty()){
            return;
        }
        if(reverse){
            for (int i=urls.size()-1;i>-1;i--){
                addTask(urls.get(i));
            }
        }else{
            for(String url:urls){
                addTask(url);
            }
        }

    }


    public void addDowloadTask(final String url, final String path, final Bitmap bitmap){
        if(bitmap==null||TextUtils.isEmpty(url)||mSaveMap.containsKey(url))
            return;
        Runnable newRun=new Runnable() {
            @Override
            public void run() {
                if(!saveBitmap(path, bitmap)){
                    mRunnableMap.remove(url);
                }
            }
        };
        mSaveMap.put(url,newRun);
        mSaveExecutor.execute(newRun);
    }


    /**
     * 保存图片方法
     */
    public static boolean saveBitmap(String fileName, Bitmap bitmap) {
        Log.e("glide", "保存图片");
        File f = new File(fileName);
        if (f.exists()) {
            return true;
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i("Imageloader", "已经保存");
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
