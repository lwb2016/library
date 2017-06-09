package com.oacg.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * 设置Glide的配置
 * Created by leo on 2017/4/21.
 */

public class GlideModuleConfig implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, getDiskSize()));  //内存中
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", getDiskSize())); //sd卡中

        // 默认内存和图片池大小
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

        // 自定义内存和图片池大小
//        builder.setMemoryCache(new LruResourceCache(getMemorySize()));
//        builder.setBitmapPool(new LruBitmapPool(getMemorySize()));

        // 定义图片格式
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认
        builder.setDecodeFormat(DecodeFormat.DEFAULT); // 默认
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    public int getMemorySize(){
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int mCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        return mCacheSize;
    }

    public int getDiskSize(){
        return 50*1024*1024;
    }
}
