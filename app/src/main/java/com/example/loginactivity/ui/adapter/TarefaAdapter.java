package com.example.loginactivity.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;

import org.w3c.dom.Text;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {

    private List<Tarefa> tarefas;
    private Context context;

    public TarefaAdapter(List<Tarefa> tarefas, Context context) {
        this.tarefas = tarefas;
        this.context = context;
    }

    @NonNull
    @Override
    public TarefaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_tarefas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaAdapter.ViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.mergeViewData(tarefa);
        //holder.itemView.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView data;
        private TextView descricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.tituloItemTxtView);
            data = itemView.findViewById(R.id.dataItemTxtView);
            descricao = itemView.findViewById(R.id.descItemTxtView);
        }

        void mergeViewData(Tarefa tarefa) {
            titulo.setText(tarefa.getTitulo());
            data.setText(tarefa.getData());
            descricao.setText(tarefa.getDescricao());
        }
    }
}
