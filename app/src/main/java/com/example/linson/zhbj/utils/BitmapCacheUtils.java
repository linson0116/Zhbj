package com.example.linson.zhbj.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

/**
 * Created by linson on 2016/12/20.
 */
public class BitmapCacheUtils {
    public static final int BITMAP_FROM_NET = 100;
    LruCache<String, Bitmap> lruCache;
    private Handler mHandler;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private String file_cache_dir = "/storage/sdcard/zhbj";

    public BitmapCacheUtils(Handler mHandler) {
        this.mHandler = mHandler;
        initLruCache();
    }

    private void initLruCache() {
        int size = (int) (Runtime.getRuntime().maxMemory() / 8);
        Log.i(TAG, "initLruCache: size=" + size);
        lruCache = new LruCache<String, Bitmap>(size) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public Bitmap getBitmap(String url, int position) {
        Bitmap bitmap = null;
        bitmap = getBitmapFromLruCache(url);
        if (bitmap != null)
            return bitmap;
        bitmap = getBitmapFromFile(url);
        if (bitmap != null)
            return bitmap;
        bitmap = getBitmapFromNet(url, position);
        if (bitmap != null)
            return bitmap;
        return null;
    }

    private Bitmap getBitmapFromFile(String url) {
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(file_cache_dir, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap != null) {
                    Log.i(TAG, "getBitmapFromFile: 取自文件缓存");
                    return bitmap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap getBitmapFromLruCache(String url) {
        Bitmap bitmap = null;
        bitmap = lruCache.get(url);
        if (bitmap != null) {
            Log.i(TAG, "getBitmapFromLruCache: 取自内存缓存");
        }
        Log.i(TAG, "getBitmapFromLruCache: " + url);
        return bitmap;
    }

    public Bitmap getBitmapFromNet(final String url, final int position) {
        executorService.execute(new Runnable() {
            HttpURLConnection conn = null;

            @Override
            public void run() {
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (bitmap != null) {
                            //存储文件缓存
                            setFileCache(bitmap, url);
                            //存储内存缓存
                            setLruCache(bitmap, url);

                            Message msg = Message.obtain();
                            msg.what = BITMAP_FROM_NET;
                            msg.obj = bitmap;
                            msg.arg1 = position;
                            mHandler.sendMessage(msg);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
        return null;
    }

    private void setLruCache(Bitmap bitmap, String url) {
        lruCache.put(url, bitmap);
        Log.i(TAG, "setLruCache: " + url);
    }

    private void setFileCache(Bitmap bitmap, String url) {
        try {

            String fileName = MD5Encoder.encode(url);
//            Log.i(TAG, "setFileCache: " + fileName);
            File file = new File(file_cache_dir, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            OutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            Log.i(TAG, "setFileCache: 文件保存在sdcard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
