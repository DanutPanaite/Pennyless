package com.example.pennyless.adapters;

import android.view.View;

public interface RecyclerClickHandler {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
