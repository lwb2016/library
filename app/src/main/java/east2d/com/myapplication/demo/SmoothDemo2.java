package east2d.com.myapplication.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import east2d.com.myapplication.R;
import east2d.com.myapplication.clip.ClipHorizontalScrollView;
import east2d.com.myapplication.clip.ClipRecycleView;

public class SmoothDemo2 extends Activity {

    private Context mContext=SmoothDemo2.this;
    private ClipRecycleView rv_list;
    private Adapter mRecycleAdapter;
   // private ImageView mImageView;
    //private TextView mTextView;
    //private ScrollLinearLayout smooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_smooth_demo2);


        rv_list= (ClipRecycleView) findViewById(R.id.crv_list);
//        mImageView= (ImageView) findViewById(R.id.iv_img);
//        mTextView= (TextView) findViewById(R.id.tv_text);
//        smooth= (ScrollLinearLayout) findViewById(R.id.smooth);
//
//        smooth.setRecycleView(rv_list);
//
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mTextView.getVisibility()==View.GONE){
//                    mTextView.setVisibility(View.VISIBLE);
//                }else{
//                    mTextView.setVisibility(View.GONE);
//                }
//            }
//        });

        height=0;
        width=getWindowManager().getDefaultDisplay().getWidth();

        rv_list.setLayoutManager(new LinearLayoutManager(this));

        mRecycleAdapter=new Adapter();

        rv_list.setAdapter(mRecycleAdapter);
        ((SimpleItemAnimator)rv_list.getItemAnimator()).setSupportsChangeAnimations(false);

//        smooth.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.i("SmoothDemo","height:"+smooth.getHeight());
//                int height1=smooth.getHeight();
//                if(height!=height1){
//                    height=height1;
//                    mRecycleAdapter.notifyItemChanged(0);
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            mRecycleAdapter.notifyItemChanged(0);
////                        }
////                    });
//                    //mRecycleAdapter.notifyItemChanged(0);
//                }
//            }
//        });

    }

    private int width=80;

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate =null;
            if(viewType==HEAD_TYPE){
                inflate =new TextView(mContext);
                return new ViewHolder(inflate);
            }else{
                inflate = LayoutInflater.from(mContext).inflate(R.layout.item_add, null);
                ItemViewHolder itemViewHolder = new ItemViewHolder(inflate);
                return itemViewHolder;
            }
        }
        private int gap=1000;

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ViewHolder){
                Log.i("SmoothDemo","holder:holder change");
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,height));
            }else if(holder instanceof ItemViewHolder){
                ((ItemViewHolder) holder).mHorizontalScrollView.scrollTo(0,0);
            }
        }

        @Override
        public int getItemCount() {
            return 100+1;
        }

        private final int HEAD_TYPE=-3;
        private final int ITEM_TYPE=-4;

        @Override
        public int getItemViewType(int position) {
            if(position==0){
                return HEAD_TYPE;
            }else{
                return ITEM_TYPE;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
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

    private int height=200;
}
