package com.example.loginactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.adapter.TarefaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavHome extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView tarefaListRecycler;
    private TarefaAdapter adapter;
    private int posicaoItemClick;
    private List<Tarefa> tarefaList;

    public NavHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);
        db = FirebaseFirestore.getInstance();
        tarefaList = new ArrayList<>();
        novaTarefa(view);
        carregarDados(view);
        return view;
    }

    private void carregarDados(View view) {
        db.collection("tarefas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            tarefaList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tarefa tarefa = document.toObject(Tarefa.class);
                                tarefa.setId(document.getId());
                                tarefaList.add(tarefa);
                            }
                            configuraRecycler(view);
                        } else {
                            Log.w("Falha", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void configuraRecycler(View view) {

        tarefaListRecycler = view.findViewById(R.id.tarefaListRecycler);
        tarefaListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TarefaAdapter(tarefaList, getContext());
        tarefaListRecycler.setAdapter(adapter);
    }

    private void novaTarefa(View view) {
        FloatingActionButton button = view.findViewById(R.id.addHomeFab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Adicionar nova tarefa!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.action_navHome_to_navCriarTarefa);
            }
        });
    }



}