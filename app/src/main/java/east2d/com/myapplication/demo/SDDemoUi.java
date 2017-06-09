package east2d.com.myapplication.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oacg.download.data.StorageData;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import east2d.com.download.FileDownloader;
import east2d.com.myapplication.R;
import east2d.com.tool.KEncryption;
import east2d.com.tool.LogU;
import east2d.com.ui.adapter.LVBaseAdapter;

/**
 * Created by leo on 2017/4/26.
 */

public class SDDemoUi extends Activity{
    ListView lv_list;
    Adapter mAdapter;

    ProgressBar progressBar;

    private String url="http://le.cdn.m.comicq.cn/Public/Images/upload/qingman/comicpage/1478/5/3ac61b845e555321ac6940bb2640f701.webp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sd_ui);
        lv_list= (ListView) findViewById(R.id.lv_list);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);


        List<StorageData> extSDCardPath = StorageData.getAllStorageData(this);

        mAdapter=new Adapter(this,extSDCardPath);
        lv_list.setAdapter(mAdapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StorageData item = mAdapter.getItem(position);
                if(item!=null){
                    startDownload(item.getPath()+"/test/download",url);
                }
            }
        });

    }

    private boolean isDownloading=false;

    private void startDownload(String path,String url){
        if(isDownloading)
            return;
        isDownloading=true;
        FileDownloader fileDownloader=new FileDownloader();
        fileDownloader.setParams(url,path,"Test"+ KEncryption.md5(url));
        fileDownloader.setOnDownloadListener(new FileDownloader.OnDownloadListener() {
            @Override
            public void startDownload(String downloadPath) {
                progressBar.setProgress(0);
                progressBar.setMax(100);
            }

            @Override
            public void downloadOnProgress(String downloadPath, long progress, long max) {
                progressBar.setProgress((int) (progress*100/max));
            }

            @Override
            public void completeDownload(String downloadPath, String filePath) {
                isDownloading=false;
            }

            @Override
            public void cancelDownload(String downloadPath) {
                isDownloading=false;
            }

            @Override
            public void downloadFail(String downloadPath) {
                isDownloading=false;
            }
        });
        fileDownloader.execute();
    }

    class Adapter extends LVBaseAdapter<StorageData,Adapter.ViewHolder>{


        public Adapter(Context context, List<StorageData> initDatas) {
            super(context, initDatas);
        }

        @Override
        public ViewHolder getItemHolder(LayoutInflater inflater, ViewGroup parent, int position) {
            View inflate=inflater.inflate(R.layout.item,parent,false);
            return new ViewHolder(inflate);
        }

        @Override
        public void bindData(ViewHolder holder, int position, StorageData itemData) {
            holder.tv_item.setText(itemData.getLabel()+":"+itemData.getPath());
        }

        class ViewHolder extends LVBaseAdapter.LVBaseViewHolder{
            TextView tv_item;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_item= (TextView) itemView.findViewById(R.id.tv_item);
            }
        }
    }

//
//    /**
//     * 获取内置SD卡路径
//     * @return
//     */
//    public String getInnerSDCardPath() {
//        return Environment.getExternalStorageDirectory().getPath();
//    }
//
//    /**
//     * 获取外置SD卡路径
//     * @return  应该就一条记录或空
//     */
//    public List<String> getExtSDCardPath()
//    {
//        List<String> lResult = new ArrayList<String>();
//        try {
//            Runtime rt = Runtime.getRuntime();
//            Process proc = rt.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.contains("extSdCard"))
//                {
//                    String [] arr = line.split(" ");
//                    String path = arr[1];
//                    File file = new File(path);
//                    if (file.isDirectory())
//                    {
//                        LogU.i("SD_DEMO",path);
//                        lResult.add(path);
//                    }
//                }
//            }
//            isr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return lResult;
//    }
//
//    @SuppressLint("SdCardPath")
//    public String getPath2() {
//        String sdcard_path = null;
//        String sd_default = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        Log.d("text", sd_default);
//        if (sd_default.endsWith("/")) {
//            sd_default = sd_default.substring(0, sd_default.length() - 1);
//        }
//        // 得到路径
//        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process proc = runtime.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            String line;
//            BufferedReader br = new BufferedReader(isr);
//            while ((line = br.readLine()) != null) {
//                if (line.contains("secure"))
//                    continue;
//                if (line.contains("asec"))
//                    continue;
//                if (line.contains("fat") && line.contains("/mnt/")) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        if (sd_default.trim().equals(columns[1].trim())) {
//                            continue;
//                        }
//                        sdcard_path = columns[1];
//                    }
//                } else if (line.contains("fuse") && line.contains("/mnt/")) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        if (sd_default.trim().equals(columns[1].trim())) {
//                            continue;
//                        }
//                        sdcard_path = columns[1];
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Log.d("text", sdcard_path);
//        return sdcard_path;
//    }

}
