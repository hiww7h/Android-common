package com.ww7h.ww.common.bases.view.recyclerview.adapters;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import com.ww7h.ww.common.threads.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   Android-common
 * 包名：     com.ww7h.ww.common.bases.view.recyclerview.adapters
 * 创建时间：  2019/4/16 19:17
 *
 * @author ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerViewHolder, T> extends RecyclerView.Adapter<VH> {

    protected List<T> dataList = new ArrayList<>();

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, position, getItemViewType(position));
    }

    /**
     * 绑定视图
     * @param holder viewHolder
     * @param position 当前位置
     * @param viewType 视图类型
     */
    protected abstract void onBindViewHolder(VH holder, int position, int viewType);

    /**
     * 修改当前列表数据
     * @param dataList 新数据列表
     */
    public void replaceDataList(List<T> dataList) {
        changeData(dataList);
    }

    /**
     * 添加数据集合
     * @param dataList 新增的数据集合
     */
    public void addDataList(List<T> dataList) {
        List<T> newDataList = new ArrayList<>();
        newDataList.addAll(this.dataList);
        newDataList.addAll(dataList);
        changeData(newDataList);
    }

    /**
     * 添加单个数据
     * @param data 单个数据
     */
    public void addData(T data) {
        List<T> newDataList = new ArrayList<>(this.dataList);
        newDataList.add(data);
        changeData(newDataList);
    }

    /**
     * 获取指定位置数据
     * @param position 位置
     * @return 返回当前数据对象
     */
    protected T getItem(int position) {

        return this.dataList.get(position);
    }

    private void changeData(final List<T> newDataList) {
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                    @Override
                    public int getOldListSize() {
                        return dataList.size();
                    }

                    @Override
                    public int getNewListSize() {
                        return newDataList.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldPosition, int newPosition) {
                        return BaseRecyclerViewAdapter.this.areItemsTheSame(dataList.get(oldPosition), newDataList.get(newPosition));
                    }

                    @Override
                    public boolean areContentsTheSame(int oldPosition, int newPosition) {
                        return BaseRecyclerViewAdapter.this.areContentsTheSame(dataList.get(oldPosition), newDataList.get(newPosition));
                    }
                });
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        dataList.addAll(newDataList);
                        diffResult.dispatchUpdatesTo(BaseRecyclerViewAdapter.this);
                    }
                });
            }
        });
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 判断item是否有变化
     * @param oldM 之前的对象
     * @param newM 新对象
     * @return 返回结果
     */
    protected abstract boolean areItemsTheSame(T oldM, T newM);

    /**
     * 判断item内容是否变化
     * @param oldM 原对象
     * @param newM 新对象
     * @return 返回结果
     */
    protected abstract boolean areContentsTheSame(T oldM, T newM);

}
