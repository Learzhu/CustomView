package com.learzhu.customview.customview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learzhu.customview.customview.R;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/28 14:44
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  14:44
 * @updateDes ${TODO}
 */
public class RecyclerViewWithHeadAdapter1 extends BaseRecyclerAdapter<String> {

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_normal, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, String data) {
        if (viewHolder instanceof RecyclerView.ViewHolder) {
            ((MyHolder) viewHolder).mTextView.setText(data);
        }
    }

    class MyHolder extends BaseRecyclerAdapter.Holder {
        TextView mTextView;

        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_text_tv);
        }
    }

}
