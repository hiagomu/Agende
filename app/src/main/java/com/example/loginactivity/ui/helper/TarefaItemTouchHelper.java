package com.example.loginactivity.ui.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.ui.adapter.TarefaAdapter;

public class TarefaItemTouchHelper extends ItemTouchHelper.Callback {

    private TarefaAdapter adapter;

    public TarefaItemTouchHelper(TarefaAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int movimentSwipe = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movimentDrag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(movimentDrag, movimentSwipe);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.alertarAcao(viewHolder.getAdapterPosition(), adapter);
    }
}
