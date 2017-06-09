package east2d.com.myapplication.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oacg.adapter.RVBaseAdapter;

import java.util.List;

import east2d.com.myapplication.R;

/**
 * Created by leo on 2017/4/27.
 */

public class DemoRecycleAdapter extends RVBaseAdapter<String,DemoRecycleAdapter.ViewHolder> {

    public DemoRecycleAdapter(Context context, List<String> initDatas) {
        super(context, initDatas);
    }

    @Override
    public ViewHolder getItemHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_add,null));
    }

    @Override
    public void bindData(ViewHolder holder, int position, String itemData) {
        holder.tv_text.setText(itemData+";position:"+position);
    }

    public static class ViewHolder extends RVBaseAdapter.RVBaseViewHolder{

        TextView tv_text;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_text= (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

}
