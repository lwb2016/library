package east2d.com.myapplication.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import east2d.com.myapplication.R;
import east2d.com.myapplication.clip.ClipHorizontalScrollView;
import east2d.com.ui.adapter.LVBaseAdapter;

public class ClipDemo3 extends Activity {

    private Context mContext=ClipDemo3.this;
    ListView rv_list;
    private Adapter mRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_smooth_demo3);
        width=getWindowManager().getDefaultDisplay().getWidth();
        rv_list= (ListView) findViewById(R.id.rv_list);
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<100;i++){
            arrayList.add("ITEM:"+i);
        }
        mRecycleAdapter=new Adapter(mContext,arrayList);
        rv_list.setAdapter(mRecycleAdapter);
    }

    private int width=80;

    class Adapter extends LVBaseAdapter<String,Adapter.ItemViewHolder> {


        public Adapter(Context context, List<String> initDatas) {
            super(context, initDatas);
        }

        @Override
        public ItemViewHolder getItemHolder(LayoutInflater inflater, ViewGroup parent, int position) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_add, parent,false);
            return  new ItemViewHolder(inflate);
        }

        @Override
        public void bindData(ItemViewHolder holder, int position, String itemData) {
            holder.mHorizontalScrollView.scrollTo(0,0);
            Glide.with(mContext).load("http://le.cdn.m.comicq.cn/Public/Images/upload/qingman/comicpage/1478/5/3ac61b845e555321ac6940bb2640f701.webp")
                    .centerCrop()
                    .into(holder.mImageView);
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        class ItemViewHolder extends LVBaseAdapter.LVBaseViewHolder{
            private ClipHorizontalScrollView mHorizontalScrollView;
            private ImageView mImageView;
            private TextView tv_delete,tv_add;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mHorizontalScrollView= (ClipHorizontalScrollView) itemView.findViewById(R.id.hsv_item);
                mImageView= (ImageView) itemView.findViewById(R.id.iv_img);
                tv_delete= (TextView) itemView.findViewById(R.id.tv_delete);
                tv_add= (TextView) itemView.findViewById(R.id.tv_add);
                ClipHorizontalScrollView.onRelayoutGrandson(mImageView,width);
            }
        }
    }
}
