package east2d.com.myapplication.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oacg.adapter.RVTypeBaseAdapter;

import java.util.List;

import east2d.com.myapplication.R;

/**
 * Created by leo on 2017/4/27.
 */

public class DemoTypeRecycleAdapter extends RVTypeBaseAdapter<String,String> {


    public DemoTypeRecycleAdapter(Context context, String tag, List<String> list) {
        super(context, tag, list);
    }

    @Override
    public RecyclerView.ViewHolder getItemHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_add,null));
    }

    @Override
    public void bindData(RecyclerView.ViewHolder holder, int position, String itemData) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).tv_text.setText(itemData+";position:"+position);

        }else if(holder instanceof CommentViewHolder){
            ((CommentViewHolder)holder).tv_ad.setText("COMMENT"+";position:"+position);
        }
    }

    @Override
    protected RecyclerView.ViewHolder getOptionHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new CommentViewHolder(inflater.inflate(R.layout.ad_plug_layout,null));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_text;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_text= (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ad;
        public CommentViewHolder(View itemView) {
            super(itemView);
            tv_ad= (TextView) itemView.findViewById(R.id.tv_ad);
        }
    }

}
