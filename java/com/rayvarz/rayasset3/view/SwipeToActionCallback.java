package com.rayvarz.rayasset3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rayvarz.rayasset3.R;

public class SwipeToActionCallback extends ItemTouchHelper.SimpleCallback {
    private Context context;
    private RecyclerView recyclerView;
    private int previousOpenedPosition = -1;
    private Bitmap deleteIcon;
    private Bitmap editIcon;

    public SwipeToActionCallback(Context context, RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        this.context = context;
        this.recyclerView = recyclerView;

        // LOAD ICON (FORMAT PNG & JPG)
        deleteIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.history);
        editIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.hierarchy);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (previousOpenedPosition != -1 && previousOpenedPosition != viewHolder.getAdapterPosition()) {
            closePreviousItem();
        }
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float maxSwipeDistance = 400; // max Swipe Distance
            float limitedDx = Math.max(dX, -maxSwipeDistance);

            // وقتی dX از -50 کمتر باشه، دکمه‌ها رو نشون می‌دهیم
            if (limitedDx < -100) {
                drawButtons(c, viewHolder);
                previousOpenedPosition = viewHolder.getAdapterPosition(); // ذخیره آیتم باز شده
            }

            super.onChildDraw(c, recyclerView, viewHolder, limitedDx, dY, actionState, isCurrentlyActive);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    private void closePreviousItem() {
        if (previousOpenedPosition != -1) {
            // اطلاع‌رسانی به آداپتر برای به‌روزرسانی آیتم قبلی که باز بوده
            recyclerView.getAdapter().notifyItemChanged(previousOpenedPosition);
            previousOpenedPosition = -1; // ریست کردن آیتم باز شده
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float buttonWidth = 200;
        float buttonMargin = 70;

        Paint paint = new Paint();

        // دکمه حذف (باید در سمت راست نمایش داده بشه)
        paint.setColor(Color.parseColor("#669bbc"));
        RectF deleteButton = new RectF(viewHolder.itemView.getRight() - buttonWidth,
                viewHolder.itemView.getTop(),
                viewHolder.itemView.getRight(),
                viewHolder.itemView.getBottom());
        c.drawRect(deleteButton, paint);

        // رسم آیکون حذف
        paint.setColor(Color.WHITE);
        paint.setTextSize(16);
        float iconSize = buttonWidth - buttonMargin * 2 + 20; // iconSize
        float deleteIconLeft = (float) (viewHolder.itemView.getRight() - (buttonWidth / 1.4));
        float deleteIconTop = (viewHolder.itemView.getTop() + ((viewHolder.itemView.getHeight() - iconSize) / 2)) - 10;
        c.drawBitmap(deleteIcon, null, new RectF(deleteIconLeft, deleteIconTop,
                deleteIconLeft + iconSize, deleteIconTop + iconSize), paint);

        // رسم متن زیر آیکون حذف
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20); // اندازه متن
        float deleteTextX = deleteButton.centerX(); // موقعیت متن در مرکز دکمه
        float deleteTextY = deleteIconTop + iconSize + 30; // فاصله کمی زیر آیکون
        c.drawText("تاریخچه", deleteTextX, deleteTextY, paint);

        // دکمه ویرایش (باید در سمت راست نمایش داده بشه)
        paint.setColor(Color.parseColor("#f18701"));
        RectF editButton = new RectF(viewHolder.itemView.getRight() - 2 * buttonWidth,
                viewHolder.itemView.getTop(),
                viewHolder.itemView.getRight() - buttonWidth,
                viewHolder.itemView.getBottom());
        c.drawRect(editButton, paint);

        // رسم آیکون ویرایش
        paint.setColor(Color.WHITE);
        float editIconLeft = (float) (viewHolder.itemView.getRight() - 1.7 * buttonWidth);
        float editIconTop = (viewHolder.itemView.getTop() + ((viewHolder.itemView.getHeight() - iconSize) / 2)) - 10;
        c.drawBitmap(editIcon, null, new RectF(editIconLeft, editIconTop,
                editIconLeft + iconSize, editIconTop + iconSize), paint);

        // رسم متن زیر آیکون ویرایش
        float editTextX = editButton.centerX(); // موقعیت متن در مرکز دکمه
        float editTextY = editIconTop + iconSize + 30; // فاصله کمی زیر آیکون
        c.drawText("دارایی فرزند", editTextX, editTextY, paint);
    }

}
