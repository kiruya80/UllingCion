package com.ulling.ullingcion.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ViewDataBinding;

import com.ulling.lib.core.ui.QcBaseLifeFragment;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.RowCryptowatchBinding;
import com.ulling.ullingcion.entites.CryptoWatch;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CryptoWatchAdapter extends QcRecyclerBaseAdapter<CryptoWatch> {

    private CryptoWatchViewModel viewModel;


    public CryptoWatchAdapter(QcBaseLifeFragment qFragment, QcRecyclerItemListener qcRecyclerItemListener) {
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
    protected void needUIEventListener(int viewTypeResId, ViewDataBinding binding) {

    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.i("needUIBinding == ");
        CryptoWatch item = (CryptoWatch) object;
        if (item.getType() == TYPE_DEFAULT) {
            RowCryptowatchBinding hoderBinding = (RowCryptowatchBinding) holder.getBinding();

            hoderBinding.tvPosition.setTag(position);
            hoderBinding.tvPosition.setText("" + position);

            hoderBinding.tvPrice.setTag("" + item.getClosePrice());
            hoderBinding.tvRsi.setText("" + item.getRsi());

            long t = Long.parseLong(item.getCloseTime() + "000");
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z", Locale.KOREA);
            hoderBinding.tvTime.setTag("" + simpleDate.format(t));

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
