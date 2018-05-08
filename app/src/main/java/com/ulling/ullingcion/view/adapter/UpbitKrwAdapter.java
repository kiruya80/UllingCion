package com.ulling.ullingcion.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ViewDataBinding;

import com.ulling.lib.core.ui.QcBaseLifeFragment;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.RowUpbitKrwBinding;
import com.ulling.ullingcion.databinding.RowUpbitKrwErrorBinding;
import com.ulling.ullingcion.entites.UpbitPriceResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UpbitKrwAdapter extends QcRecyclerBaseAdapter<UpbitPriceResponse> {

    public UpbitKrwAdapter(QcBaseLifeFragment qFragment, QcRecyclerItemListener qcRecyclerItemListener) {
        super(qFragment, qcRecyclerItemListener);
    }

    @Override
    protected void needInitToOnCreate() {
        itemList = new ArrayList<>();
    }

    @Override
    protected void needResetData() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

//    @Override
//    public void setViewModel(AndroidViewModel viewModel) {
//    }

    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        if (itemList != null && itemList.size() > 0) {
            if (itemList.get(position).getType() == TYPE_DEFAULT) {
                return R.layout.row_upbit_krw;
            } else if (itemList.get(position).getType() == TYPE_ERROR) {
                return R.layout.row_upbit_krw_error;
            } else if (itemList.get(position).getType() == TYPE_LOAD_FAIL) {
                return R.layout.row_load_fail;
            } else if (itemList.get(position).getType() == TYPE_LOAD_PROGRESS) {
                return R.layout.row_load_progress;
            }
        }
        return R.layout.row_load_fail;
    }

    @Override
    protected void needUIEventListener(int viewTypeResId, ViewDataBinding binding) {

    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.i("needUIBinding == ");
        UpbitPriceResponse item = (UpbitPriceResponse) object;
        QcLog.i("item == " + item.toString());
        if (item.getType() == TYPE_DEFAULT) {
            RowUpbitKrwBinding hoderBinding = (RowUpbitKrwBinding) holder.getBinding();

            hoderBinding.tvPosition.setText("" + item.getCode());

            if (item.getTimestamp() > 0) {
//                long t = Long.parseLong(item.getTimestamp());
                SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.KOREA);
                hoderBinding.tvTime.setText("Time : " + simpleDate.format(item.getTimestamp()));
            }

            hoderBinding.tvPrice.setText("HighPrice : " + item.getHighPrice());

            hoderBinding.tvRsi.setText("LowPrice : " + item.getLowPrice());

        } else if (item.getType() == TYPE_ERROR) {
            RowUpbitKrwErrorBinding hoderBinding = (RowUpbitKrwErrorBinding) holder.getBinding();

            hoderBinding.tvPosition.setText("" + item.getCode());
            hoderBinding.tvTime.setText("" + item.getErrorResponse().getTimeStamp());
            hoderBinding.tvPrice.setText("" + item.getErrorResponse().getStatus());
            hoderBinding.tvRsi.setText("" + item.getErrorResponse().getMessage());
        }
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
}
