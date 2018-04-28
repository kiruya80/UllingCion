package com.ulling.lib.core.entities;

import android.arch.persistence.room.Ignore;

import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 29.
 * @description :
 * @since :
 */

public class QcBaseItem {
    @Ignore
    public int type = QcRecyclerBaseAdapter.TYPE_DEFAULT;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
