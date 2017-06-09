package east2d.com.myapplication.demo;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import east2d.com.myapplication.R;
import me.henrytao.smoothappbarlayout.SmoothAppBarLayout;

public class SmoothDemo extends Activity {

    private Context mContext=SmoothDemo.this;
    RecyclerView rv_list;
    private Adapter mRecycleAdapter;
    private ImageView mImageView;
    private TextView mTextView;
    private SmoothAppBarLayout smooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_smooth_demo);


        rv_list= (RecyclerView) findViewById(R.id.rv_list);
        mImageView= (ImageView) findViewById(R.id.iv_img);
        mTextView= (TextView) findViewById(R.id.tv_text);
        smooth= (SmoothAppBarLayout) findViewById(R.id.smooth);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTextView.getVisibility()==View.GONE){
                    mTextView.setVisibility(View.VISIBLE);
                }else{
                    mTextView.setVisibility(View.GONE);
                }
            }
        });

        height=0;

        rv_list.setLayoutManager(new LinearLayoutManager(this));

        mRecycleAdapter=new Adapter();

        rv_list.setAdapter(mRecycleAdapter);
        ((SimpleItemAnimator)rv_list.getItemAnimator()).setSupportsChangeAnimations(false);


        smooth.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("SmoothDemo","height:"+smooth.getHeight());
                int height1=smooth.getHeight();
                if(height!=height1){
                    height=height1;
                    mRecycleAdapter.notifyItemChanged(0);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mRecycleAdapter.notifyItemChanged(0);
//                        }
//                    });
                    //mRecycleAdapter.notifyItemChanged(0);
                }
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate =null;
            if(viewType==HEAD_TYPE){
                inflate =new TextView(mContext);
            }else{
                inflate = LayoutInflater.from(mContext).inflate(R.layout.item_add, null);
            }
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(position==0){
                Log.i("SmoothDemo","holder:holder change");
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,height));
            }
        }

        @Override
        public int getItemCount() {
            return 5+1;
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
    }

    private int height=200;
}
