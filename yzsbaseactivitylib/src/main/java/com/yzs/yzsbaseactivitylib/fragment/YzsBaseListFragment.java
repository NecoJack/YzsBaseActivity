package com.yzs.yzsbaseactivitylib.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yzs.yzsbaseactivitylib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 姚智胜
 * Version: V1.0版本
 * Description: 普通list的baseFragment
 * Date: 2016/12/15
 */
public abstract class YzsBaseListFragment<T> extends YzsBaseFragment {
    private static final String TAG = "YzsBaseListFragment";

    /**
     * 普通list布局
     */
    protected static final int LINEAR_LAYOUT_MANAGER = 0;
    /**
     * grid布局
     */
    protected static final int GRID_LAYOUT_MANAGER = 1;
    /**
     * 瀑布流布局
     */
    protected static final int STAGGERED_GRID_LAYOUT_MANAGER = 2;


    /**
     * 默认为0单行布局
     */
    private int mListType = 0;
    /**
     * 排列方式默认垂直
     */
    private boolean mIsVertical = true;
    /**
     * grid布局与瀑布流布局默认行数
     */
    private int mSpanCount = 1;

    protected RecyclerView mRecyclerView;

    protected YzsListAdapter mAdapter;
    /**
     * 子布局id
     */
    private int layoutResId = -1;


//    @Override
    protected void initView(View view) {
        initLayoutResId();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_list);
        chooseListType(mListType, mIsVertical);
        if (-1 == layoutResId) {
            throw new RuntimeException("layoutResId is null!");
        }
        mAdapter = new YzsListAdapter(layoutResId, new ArrayList<T>());
        mRecyclerView.setAdapter(mAdapter);

    }


    /**
     * 设置子布局layout
     *
     * @param layoutResId 子布局layout
     */
    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    /**
     * 初始化子布局
     */
    protected abstract void initLayoutResId();

    /**
     * 是否打开加载更多
     */
    protected void openLoadMoreSize(boolean loadMore) {
        mAdapter.loadMoreEnd(loadMore);
    }

    /**
     * @param type       布局管理type
     * @param isVertical 是否是垂直的布局 ，true垂直布局，false横向布局
     */
    protected void setListType(int type, boolean isVertical) {
        mListType = type;
        mIsVertical = isVertical;
    }

    protected void setSpanCount(int spanCount) {
        if (spanCount > 0)
            mSpanCount = spanCount;
    }

    /**
     * @param listType 选择布局种类
     */
    private void chooseListType(int listType, boolean isVertical) {
        switch (listType) {
            case LINEAR_LAYOUT_MANAGER:
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                linearLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);

                mRecyclerView.setLayoutManager(linearLayoutManager);
                break;
            case GRID_LAYOUT_MANAGER:

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mSpanCount);

                gridLayoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);

                mRecyclerView.setLayoutManager(gridLayoutManager);
                break;
            case STAGGERED_GRID_LAYOUT_MANAGER:
                //设置布局管理器
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager
                        (mSpanCount, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);

                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
            default:
                //设置布局管理器
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

                layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);

                mRecyclerView.setLayoutManager(layoutManager);
                break;
        }
    }

    /**
     * adapter内的处理
     *
     * @param baseViewHolder BaseViewHolder
     * @param t              泛型T
     */
    protected abstract void MyHolder(BaseViewHolder baseViewHolder, T t);


    public class YzsListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        public YzsListAdapter(int layoutResId, List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, T t) {
            MyHolder(baseViewHolder, t);
        }
    }

}