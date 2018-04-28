package com.ulling.lib.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

/**
 * Created by P100651 on 2017-07-21.
 * <p>
 * http://thdev.tech/androiddev/2016/11/01/Android-RecyclerView-intro.html
 * <p>
 * LinearLayoutManager
 * <p>
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="LinearLayoutManager" />
 * GridLayoutManager
 * <p>
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="GridLayoutManager"
 * app:spanCount="2" />
 * LinearLayoutManager
 * <p>
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="StaggeredGridLayoutManager"
 * app:spanCount="3" />
 * <p>
 * <p>
 * LinearLayoutManager
 * // use a linear layout manager
 * mLayoutManager = new LinearLayoutManager(this);
 * mRecyclerView.setLayoutManager(mLayoutManager);
 * <p>
 * GridLayoutManager
 * // use a staggered grid layout manager
 * mGridLayoutManager = new new GridLayoutManager(this, 3);
 * mRecyclerView.setLayoutManager(mGridLayoutManager);
 * <p>
 * StaggeredGridLayoutManager
 * // use a staggered grid layout manager
 * mStgaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
 * mRecyclerView.setLayoutManager(mStgaggeredGridLayoutManager);
 * <p>
 * <p>
 * Header/Footer을 포함?
 * Fail loading ?
 */
public class QcRecyclerView extends RecyclerView {
    private Context context;
    private QcRecyclerListener qcRecyclerListener;
    private QcRecyclerBaseAdapter adapter;

    private boolean reverseLayout = false;
    private int pageSize = 0;


    public interface QcRecyclerListener {
        void onLoadMore(int page, int totalItemsCount, RecyclerView view);

        void onPositionTop();

        void onPositionBottom();
    }


    /**
     * SCROLL PAGE SETTING
     */

    private EndlessRecyclerScrollListener endlessRecyclerScrollListener = new EndlessRecyclerScrollListener((RecyclerView.LayoutManager) getLayoutManager()) {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            QcLog.i("onLoadMore =====");
            if (endlessRecyclerScrollListener != null)
                endlessRecyclerScrollListener.onNetworkLoading(true);
            if (qcRecyclerListener != null) {
                qcRecyclerListener.onLoadMore(page, totalItemsCount, view);
            }
        }

        @Override
        public void onPositionTop() {
            QcLog.i("onPositionTop =====");
            if (qcRecyclerListener != null)
                qcRecyclerListener.onPositionTop();
        }

        @Override
        public void onPositionBottom() {
            QcLog.i("onPositionBottom =====");
            if (qcRecyclerListener != null)
                qcRecyclerListener.onPositionBottom();
        }
    };

    /**
     * 스크롤 데이터 리스너 가져오기
     * 뷰에서 네트워크데이터 변경시 스크롤리스너에게 알려주기위해서
     */
    public EndlessRecyclerScrollListener getEndlessRecyclerScrollListener() {
        if (endlessRecyclerScrollListener != null)
            return endlessRecyclerScrollListener;
        return null;
    }

    /**
     * 스크롤리스너 가져오기
     */
    public void setQcRecyclerListener(QcRecyclerListener qcRecyclerListener) {
        this.qcRecyclerListener = qcRecyclerListener;
    }

    /**
     * 리사이클뷰 생성
     */

    public QcRecyclerView(Context context) {
        // 자신의 생성자를 호출합니다.
        this(context, null);
    }

    public QcRecyclerView(Context context, @Nullable AttributeSet attrs) {
        // 자신의 생성자를 호출합니다.
        this(context, attrs, 0);
    }

    public QcRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        initView();
//        getAttrs(attrs);
//        setLinearLayoutManager();
//        setEndlessRecyclerScrollListener();
        if (endlessRecyclerScrollListener != null) {
            addOnScrollListener(endlessRecyclerScrollListener);
        }
    }


    /**
     * emptyView SETTING
     */
    private View emptyView;
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                QcLog.e("adapter != null && emptyView != null =====" + adapter.getItemCount());
                if (adapter.getItemCount() == 0) {
                    setEmptyView(true);
                } else {
                    setEmptyView(false);
                }
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            QcLog.e("onItemRangeChanged =====");
            super.onItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {

            QcLog.e("onItemRangeChanged payload =====");
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            QcLog.e("onItemRangeInserted =====");
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };

    public void setAdapter(QcRecyclerBaseAdapter adapter, int pageSize, View emptyView) {
        if (!(adapter instanceof QcRecyclerBaseAdapter)) {
            throw new IllegalArgumentException("please use QcRecyclerBaseAdapter to instead of Adapter");
        }
        this.pageSize = pageSize;
        if (endlessRecyclerScrollListener != null)
            endlessRecyclerScrollListener.setPgeSize(pageSize);
        this.emptyView = emptyView;
        this.adapter = adapter;
        this.setAdapter(adapter);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null && emptyObserver != null && emptyView != null) {
            if (!adapter.hasObservers())
                adapter.registerAdapterDataObserver(emptyObserver);
//            emptyObserver.onChanged();
        }
    }

    private void setEmptyView(boolean isEmpty) {
        if (isEmpty) {
            emptyView.setVisibility(View.VISIBLE);
            QcRecyclerView.this.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            QcRecyclerView.this.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public LayoutManager getLayoutManager() {
        LayoutManager layoutManager = super.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            reverseLayout = staggeredGridLayoutManager.getReverseLayout();

        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            reverseLayout = gridLayoutManager.getReverseLayout();

        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            reverseLayout = linearLayoutManager.getReverseLayout();
        }
        return layoutManager;
    }

    public boolean isReverseLayout() {
        return reverseLayout;
    }

    public void setReverseLayout(boolean reverseLayout) {
        LayoutManager layoutManager = super.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            staggeredGridLayoutManager.setReverseLayout(reverseLayout);
            this.reverseLayout = reverseLayout;

        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setReverseLayout(reverseLayout);
            this.reverseLayout = reverseLayout;

        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            linearLayoutManager.setReverseLayout(reverseLayout);
            this.reverseLayout = reverseLayout;
        }
    }

    //    public EndlessRecyclerScrollListener getEndlessRecyclerScrollListener() {
//        return endlessRecyclerScrollListener;
//    }

    private void initView() {
//        String infService = Context.LAYOUT_INFLATER_SERVICE;
//        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
//        View v = li.inflate(R.layout.welcome_login_button, this, false);
//        addView(v);
//        bg = (LinearLayout) findViewById(R.id.bg);
//        symbol = (ImageView) findViewById(R.id.symbol);
//        text = (TextView) findViewById(R.id.text);
    }

    private void getAttrs(AttributeSet attrs) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QcAttrsRecyclerView);
//        setTypeArray(typedArray);
    }
//    private void getAttrs(AttributeSet attrs, int defStyle) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QcAttrsRecyclerView, defStyle, 0);
//        setTypeArray(typedArray);
//    }

    private void setTypeArray(TypedArray typedArray) {
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcOrientation)) {
//            orientation = typedArray.getInt(R.styleable.QcAttrsRecyclerView_qcOrientation, 0);
//        }
//
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcReverseLayout)) {
//            reverseLayout = typedArray.getBoolean(R.styleable.QcAttrsRecyclerView_qcReverseLayout, false);
//        }
//
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcSpanCount)) {
//            spanCount = typedArray.getInt(R.styleable.QcAttrsRecyclerView_qcSpanCount, 1);
//        }
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcLayoutManager)) {
//            transform = typedArray.getInt(R.styleable.QcAttrsRecyclerView_qcLayoutManager, 0);
//        }
//        try {
//            distanceExample = typedArray.getDimension(R.styleable.MyCustomElement_distanceExample, 100.0f);
//        } finally {
//            ta.recycle();
//        }
//        try {
//            mShowText = typedArray.get(R.styleable.transform, false);
//            mTextPos = typedArray.getInteger(R.styleable.PieChart_labelPosition, 0);
//        } finally {
//            typedArray.recycle();
//        }
//        int bg_resID = typedArray.getResourceId(R.styleable.LoginButton_bg, R.drawable.login_naver_bg);
//        bg.setBackgroundResource(bg_resID);
//
//        int symbol_resID = typedArray.getResourceId(R.styleable.LoginButton_symbol, R.drawable.login_naver_symbol);
//        symbol.setImageResource(symbol_resID);
//
//        int textColor = typedArray.getColor(R.styleable.LoginButton_textColor, 0);
//        text.setTextColor(textColor);
//
//        String text_string = typedArray.getString(R.styleable.LoginButton_text);
//        text.setText(text_string);
        typedArray.recycle();
    }
//    private void setLinearLayoutManager() {
//        if (linear == orientation) {
//            layoutManager = new LinearLayoutManager(getContext());
//            layoutManager.setOrientation(orientation);
//            layoutManager.setItemPrefetchEnabled(true);
//            setLayoutManager(layoutManager);
//        } else if (Grid == orientation) {
//            gridLayoutManager = new GridLayoutManager(getContext(), spanCount, orientation, reverseLayout);
//            gridLayoutManager.setItemPrefetchEnabled(true);
//            setLayoutManager(gridLayoutManager);
//        } else if (StaggeredGrid == orientation) {
//            stgaggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount,
//                    StaggeredGridLayoutManager.VERTICAL);
//            stgaggeredGridLayoutManager.setItemPrefetchEnabled(true);
//            setLayoutManager(stgaggeredGridLayoutManager);
//        }
//    }
    //    @Override
//    public RecyclerView.LayoutManager getLayoutManager() {
//        if (linear == orientation) {
//            return layoutManager;
//        } else if (Grid == orientation) {
//            return gridLayoutManager;
//        } else if (StaggeredGrid == orientation) {
//            return stgaggeredGridLayoutManager;
//        }
//        return layoutManager;
//    }


//    /**
//     * set list divider
//     *
//     * @param dividerRes divider resource
//     */
//    public void setDivider(int dividerRes) {
//        setDivider(dividerRes, WRAP_CONTENT);
//    }
//
//    /**
//     * set list divider
//     *
//     * @param dividerRes    divider resource
//     * @param dividerHeight divider height
//     */
//    public void setDivider(int dividerRes, int dividerHeight) {
//        Drawable drawable = getResources().getDrawable(dividerRes);
//        setDivider(drawable, dividerHeight);
//    }
//
//    /**
//     * set list divider
//     *
//     * @param drawable      drawable
//     * @param dividerHeight divider height
//     */
//    public void setDivider(final Drawable drawable, final int dividerHeight) {
//        if (null == drawable) {
//            throw new NullPointerException("drawable resource is null");
//        }
//        addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                int left = parent.getPaddingLeft();
//                int right = parent.getWidth() - parent.getPaddingRight();
//
//                int childCount = parent.getChildCount();
//                for (int i = 0; i < childCount; i++) {
//                    View child = parent.getChildAt(i);
//
//                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//                    int top = child.getBottom() + params.bottomMargin;
//                    int bottom;
//                    if (dividerHeight == WRAP_CONTENT) {
//                        bottom = top + drawable.getIntrinsicHeight();
//                    } else {
//                        if (dividerHeight < 0) {
//                            bottom = top;
//                        } else {
//                            bottom = top + dividerHeight;
//                        }
//
//                    }
//
//                    drawable.setBounds(left, top, right, bottom);
//                    drawable.draw(c);
//                }
//            }
//        });
//    }
//
//    /**
//     * set list divider
//     *
//     * @param drawable drawable
//     */
//    public void setDivider(Drawable drawable) {
//        setDivider(drawable, WRAP_CONTENT);
//    }


//    /**
//     * enable list view auto load more
//     *
//     * @param loadMoreListener load more listener
//     */
//    public void enableAutoLoadMore(final HiInterface.OnLoadMoreListener loadMoreListener) {
//        addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (null == mLayoutManager || null == mAdapter || mAdapter.mIsAddingFooter) {
//                    return;
//                }
//                mVisibleItemCount = mLayoutManager.getChildCount();
//                mTotalItemCount = mLayoutManager.getItemCount();
//                mFirstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
//                if ((mVisibleItemCount + mFirstVisibleItemPosition) >= mTotalItemCount) {
//                    if (null != loadMoreListener) {
//                        loadMoreListener.loadMore();
//                    }
//                }
//            }
//        });
//    }
}