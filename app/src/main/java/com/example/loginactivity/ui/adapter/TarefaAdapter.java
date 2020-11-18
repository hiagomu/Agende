package com.example.loginactivity.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.listener.TarefaItemClickListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {

    private List<Tarefa> tarefas;
    private Context context;
    private TarefaItemClickListener onItemClickListener;
    private AlertDialog alerta;
    private boolean manterOuRemover;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClick(tarefa);
            }
        });
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

    public void setOnItemClickListener(TarefaItemClickListener tarefaItemClickListener) {
        this.onItemClickListener = tarefaItemClickListener;
    }

    public void removerTarefa(int adapterPosition) {
        alertarAcao();
        if (manterOuRemover){
            Tarefa tarefa = tarefas.get(adapterPosition);
            FirebaseFirestore.getInstance()
                    .collection("tarefas")
                    .document(tarefa.getId())
                    .delete();
            tarefas.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
        }
    }

    private void alertarAcao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Remover tarefa");
        builder.setMessage("Deseja remover a tarefa?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Removido", Toast.LENGTH_LONG).show();
                manterOuRemover = true;
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                manterOuRemover = false;
            }
        });

        alerta = builder.create();
        alerta.show();
    }
}
