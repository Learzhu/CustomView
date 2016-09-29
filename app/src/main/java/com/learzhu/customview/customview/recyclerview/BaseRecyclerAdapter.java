package com.learzhu.customview.customview.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/28 14:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  14:12
 * @updateDes ${TODO}
 * RecyclerView的抽象Adapter
 * <p>
 * 提供两个抽象方法onCreate和onBind用来创建holder和绑定数据，而对于header做的一系列工作，
 * 我们都放到了BaseRecyclerAdapter中，而继承BaseRecyclerAdapter后，我们仅仅关心我们的holder怎么创建和数据怎么绑定就ok
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private ArrayList<T> mDatas = new ArrayList<>();
    private View mHeaderView;
    /**
     * 预留的点击事件的监听
     */
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * 设置头部
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public ArrayList<T> getDatas() {
        return mDatas;
    }

    public void addDatas(ArrayList<T> datas) {
//        mDatas = datas;
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() - 1;
    }

    /**
     * 区分是否有头部View的两种的实际数据源的position
     *
     * @param holder
     * @return
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        final T data = mDatas.get(pos);
        onBind(holder, pos, data);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(pos, data);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        return onCreate(parent, viewType);
    }

    /**
     * 当是GridViewManager 此时处理header占据两个单元格的设置
     * 注意数字是占据几个单元格
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
     * 当是StaggeredGridLayoutManager的时候 设置第一行占据全部
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
     * 自定义的抽象方法用用户加载用户自定义的数据
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    /**
     * 自定义的抽象方法用于用户绑定数据
     *
     * @param viewHolder
     * @param RealPosition
     * @param data
     */
    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    /**
     * 预留的可以自定义的ViewHolder
     */
    public class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 预留一个接口用于调用Item的点击事件
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

}
