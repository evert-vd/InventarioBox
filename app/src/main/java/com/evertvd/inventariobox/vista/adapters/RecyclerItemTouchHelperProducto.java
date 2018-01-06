package com.evertvd.inventariobox.vista.adapters;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by evertvd on 24/11/2017.
 */

public class RecyclerItemTouchHelperProducto extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;


    public RecyclerItemTouchHelperProducto(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
        //this.relativeLayout=relativeLayout;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {

            final View view_content = ((NuevoProductoAdapter.MyViewHolder) viewHolder).view_content;
            getDefaultUIUtil().onSelected(view_content);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((NuevoProductoAdapter.MyViewHolder) viewHolder).view_content;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((NuevoProductoAdapter.MyViewHolder) viewHolder).view_content;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((NuevoProductoAdapter.MyViewHolder) viewHolder).view_content;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);


        //solucionado
        //final RelativeLayout view_edit = ((NuevoProductoAdapter.MyViewHolder) viewHolder).view_edit;
        /*final RelativeLayout view_delete = ((NuevoProductoAdapter.MyViewHolder) viewHolder).view_delete;

        if (dX < 0) {
            //view_edit.setVisibility(View.GONE);
            view_delete.setVisibility(View.VISIBLE);

        } else if (dX == 0) {
            //view_edit.setVisibility(View.GONE);
            view_delete.setVisibility(View.GONE);


        } else {
            //view_edit.setVisibility(View.VISIBLE);
            view_delete.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        //Log.e("direction2", String.valueOf(direction));
    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        //Log.e("flags", String.valueOf(flags));
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }


    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }


}
