package com.example.loginactivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.adapter.TarefaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

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
        configuraRecycler(view);
        novaTarefa(view);

        return view;
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