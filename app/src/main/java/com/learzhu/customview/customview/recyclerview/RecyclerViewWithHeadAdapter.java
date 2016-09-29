package com.learzhu.customview.customview.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learzhu.customview.customview.R;

import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/28 10:06
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  10:06
 * @updateDes ${TODO}
 */
public class RecyclerViewWithHeadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    /*可以是自定义view的头部*/
    private View mHeaderView;
    private ArrayList<String> mList = new ArrayList<>();
    /*recyclerView的点击事件*/
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addDatas(ArrayList<String> list) {
//        mList = list;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * onCreateViewHolder方法中来判断itemType，如果是header，则返回我们设置的headerView，否则正常加载item布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_normal, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        final String data = mList.get(pos);
        if (holder instanceof Holder) {
            ((Holder) holder).mTextView.setText(data);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(pos, data);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }

    /**
     * 绑定到RecyclerView的时候
     * <p/>
     * 此时处理header占据两个单元格的设置
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? mGridLayoutManager.getSpanCount() : 1;
                }
            });

        }
    }

    /**
     * 为StaggeredGridLayoutManager添加header
     * StaggeredGridLayoutManager.LayoutParams为我们提供了一个setFullSpan方法来设置占领全部空间
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams mLayoutParams = holder.itemView.getLayoutParams();
        if (mLayoutParams != null && mLayoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) mLayoutParams;
            lp.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    /**
     * 获取实际的位置
     *
     * @param holder
     * @return
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    /**
     * 重写了getItemViewType方法，并根据位置来返回不同的type
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    /**
     * 复用的View容器
     */
    class Holder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            mTextView = (TextView) itemView.findViewById(R.id.item_text_tv);
        }
    }

    /**
     * 对外的点击接口
     */
    interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
