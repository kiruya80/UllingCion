package com.ulling.lib.core.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class QcDiffCallback<T> extends DiffUtil.Callback {

    private List<T> mOldItemList;
    private List<T> mNewItemList;

    public QcDiffCallback(List<T> mOldItemList, List<T> mNewItemList) {
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
        return mOldItemList.get(oldItemPosition).equals(
                mNewItemList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T newItem = mNewItemList.get(newItemPosition);
        T oldItem = mOldItemList.get(oldItemPosition);

        return oldItem.equals(newItem);
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
