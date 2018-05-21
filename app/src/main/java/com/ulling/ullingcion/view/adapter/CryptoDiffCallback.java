package com.ulling.ullingcion.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.ulling.ullingcion.entites.Cryptowat.Candles;

import java.util.List;

public class CryptoDiffCallback extends DiffUtil.Callback {
    private List<Candles> mOldItemList;
    private List<Candles> mNewItemList;

    public CryptoDiffCallback(List<Candles> mOldItemList, List<Candles> mNewItemList) {
        this.mOldItemList = mOldItemList;
        this.mNewItemList = mNewItemList;
    }

    @Override
    public int getOldListSize() {
        return mOldItemList != null ? mOldItemList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewItemList != null ? mNewItemList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItemList.get(oldItemPosition).getCloseTime() ==
                mNewItemList.get(newItemPosition).getCloseTime();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Candles newItem = mNewItemList.get(newItemPosition);
        Candles oldItem = mOldItemList.get(oldItemPosition);

        return oldItem.getOpenPrice() == newItem.getOpenPrice()
                && oldItem.getClosePrice() == newItem.getClosePrice();
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//        Product newProduct = mNewList.get(newItemPosition);
//        Product oldProduct = mOldList.get(oldItemPosition);
//        Bundle diffBundle = new Bundle();
//        if (newProduct.hasDiscount() != oldProduct.hasDiscount()) {
//            diffBundle.putBoolean(KEY_DISCOUNT, newProduct.hasDiscount());
//        }
//        if (newProduct.getReviews().size() != oldProduct.getReviews().size()) {
//            diffBundle.putInt(Product.KEY_REVIEWS_COUNT, newProduct.getReviews().size());
//        }
//        if (newProduct.getPrice() != oldProduct.getPrice()) {
//            diffBundle.putFloat(Product.KEY_PRICE, newProduct.getPrice());
//        }
//        if (diffBundle.size() == 0) return null;
//        return diffBundle;
//    }

//    @Override
//    public void onBindViewHolder(ProductViewHolder holder, int position, List<Object> payloads) {
//        if(payloads.isEmpty()) return;
//        else{
//            Bundle o = (Bundle) payloads.get(0);
//            for (String key : o.keySet()) {
//                if(key.equals(KEY_DISCOUNT)){
//                    //TODO lets update blink discount textView :)
//                }else if(key.equals(KEY_PRICE)){
//                    //TODO lets update and change price color for some time
//                }else if(key.equals(KEY_REVIEWS_COUNT)){
//                    //TODO just update the review count textview
//                }
//            }
//        }
//    }
}
