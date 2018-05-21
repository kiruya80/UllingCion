package com.ulling.ullingcion.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;

import com.ulling.lib.core.ui.QcBaseLifeFragment;
import com.ulling.lib.core.util.QcDiffCallback;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.RowCryptowatchBinding;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatch;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CryptoWatchAdapter extends QcRecyclerBaseAdapter<Candles> {


    private final SimpleDateFormat simpleDate;

    public CryptoWatchAdapter(QcBaseLifeFragment qFragment, QcRecyclerItemListener qcRecyclerItemListener) {
        super(qFragment, qcRecyclerItemListener);
        simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));
    }


//    public void addListDiffResult(final List<Candles> mNewItemList) {
//        if (itemList == null) {
//            this.itemList = new ArrayList<Candles>();
//            this.itemList.clear();
//            this.itemList.addAll(mNewItemList);
//            notifyItemRangeInserted(0, itemList.size());
//        } else {
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return itemList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return itemList_.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
////                    return itemList.get(oldItemPosition).getAnswerId() ==
////                            itemList_.get(newItemPosition).getAnswerId();
//                    return itemList.get(oldItemPosition).equals(
//                            itemList_.get(newItemPosition));
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    T newItem = itemList_.get(newItemPosition);
//                    T oldItem = itemList.get(oldItemPosition);
//
//                    return oldItem.equals(newItem);
//                }
//
//
//                @Nullable
//                @Override
//                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//                    return super.getChangePayload(oldItemPosition, newItemPosition);
//                }
//            });
//
//
//            final QcDiffCallback diffCallback = new QcDiffCallback(this.itemList, mNewItemList);
//            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//
//            this.itemList.clear();
//            this.itemList.addAll(mNewItemList);
//            diffResult.dispatchUpdatesTo(this);
//        }
//    }

    @Override
    protected void needInitToOnCreate() {
        itemList = new ArrayList<>();
    }

    @Override
    protected void needResetData() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }


    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        if (itemList != null && itemList.size() > 0) {
            if (itemList.get(position).getType() == TYPE_DEFAULT) {
                return R.layout.row_cryptowatch;
            } else if (itemList.get(position).getType() == TYPE_LOAD_FAIL) {
                return R.layout.row_load_fail;
            } else if (itemList.get(position).getType() == TYPE_LOAD_PROGRESS) {
                return R.layout.row_load_progress;
            }
        }
        return R.layout.row_cryptowatch;
    }


    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.i("needUIBinding == ");
        Candles item = (Candles) object;
        if (item.getType() == TYPE_DEFAULT) {
            RowCryptowatchBinding hoderBinding = (RowCryptowatchBinding) holder.getBinding();


//            open > close 음봉
//            open < close 양봉
            if (item.getOpenPrice() > item.getClosePrice()) {
                // 음봉
                hoderBinding.tvTime.setBackgroundColor(qCon.getResources().getColor(R.color.color_red));
            } else {
                // 양봉
                hoderBinding.tvTime.setBackgroundColor(qCon.getResources().getColor(R.color.color_green));
            }
            Date date = new Date(item.getCloseTime());
            String formattedDate = simpleDate.format(date);
            hoderBinding.tvTime.setText("" + formattedDate);

            hoderBinding.tvOpenPrice.setText("Oepn Price : " + item.getOpenPrice());
            hoderBinding.tvClosePrice.setText("Close Price : " + item.getClosePrice());
            hoderBinding.tvHighPrice.setText("High Price : " + item.getHighPrice());
            hoderBinding.tvLowPrice.setText("Low Price : " + item.getLowPrice());
        }

    }

    @Override
    protected void needUIHeaderBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUILoadFailBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUILoadProgressBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUIOtherBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUIEventListener(int viewTypeResId, ViewDataBinding binding) {

    }
}
