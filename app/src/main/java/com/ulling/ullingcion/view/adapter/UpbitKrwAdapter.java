package com.ulling.ullingcion.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ViewDataBinding;

import com.squareup.picasso.Picasso;
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
    public static final int TYPE_ERROR = 998;
    private final SimpleDateFormat simpleDate;

    public UpbitKrwAdapter(QcBaseLifeFragment qFragment, QcRecyclerItemListener qcRecyclerItemListener) {
        super(qFragment, qcRecyclerItemListener);
        simpleDate = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.KOREA);
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
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        UpbitPriceResponse item = (UpbitPriceResponse) object;
        RowUpbitKrwBinding hoderBinding = (RowUpbitKrwBinding) holder.getBinding();

        Picasso.get()
                .load(item.getLogoImgUrl())
                .into(hoderBinding.imgLogo);

        if (item.getTimestamp() > 0) {
            hoderBinding.tvTime.setText("Time : " + simpleDate.format(item.getTimestamp()));
        }

        hoderBinding.tvName.setText("" + item.getHighPrice());
        hoderBinding.tvMsg.setText("" + item.getLowPrice());
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
        UpbitPriceResponse item = (UpbitPriceResponse) object;
        if (item.getType() == TYPE_ERROR) {
            RowUpbitKrwErrorBinding hoderBinding = (RowUpbitKrwErrorBinding) holder.getBinding();

            Picasso.get()
                    .load(item.getLogoImgUrl())
                    .into(hoderBinding.imgLogo);

            hoderBinding.tvTime.setText("" + item.getErrorResponse().getTimeStamp());
            hoderBinding.tvName.setText("" + item.getCode());
            hoderBinding.tvMsg.setText("" + item.getErrorResponse().getMessage());
        }
    }


    @Override
    protected void needUIEventListener(int viewTypeResId, ViewDataBinding binding) {

    }
}
