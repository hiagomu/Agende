package com.example.loginactivity.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.adapter.TarefaAdapter;
import com.example.loginactivity.ui.helper.TarefaItemTouchHelper;
import com.example.loginactivity.ui.listener.TarefaItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NavCategory extends Fragment implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCategoria;
    private RecyclerView recyclerViewCategoria;
    private FirebaseFirestore db;
    private List<Tarefa> tarefaList;
    private TarefaAdapter adapter;

    public NavCategory() {
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
        View view = inflater.inflate(R.layout.fragment_nav_category, container, false);

        createSpinner(view);

        return view;
    }

    private void createSpinner(View view) {
        spinnerCategoria = view.findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);
    }

    private void carregaRecycler(String categoria, View view) {
        tarefaList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            tarefaList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tarefa tarefa = document.toObject(Tarefa.class);
                                tarefa.setId(document.getId());

                                if (categoria.equals("Estudantil")) {
                                    tarefaList.add(tarefa);
                                }
                                if (categoria.equals("Doméstico")) {
                                    tarefaList.add(tarefa);
                                }
                                if (categoria.equals("Outro")) {
                                    tarefaList.add(tarefa);
                                }
                            }

                            Collections.sort(tarefaList);

                            configuraRecycler(view);
                        } else {
                            Log.w("Falha", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void configuraRecycler(View view) {

        recyclerViewCategoria = view.findViewById(R.id.categoryRecycler);
        recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TarefaAdapter(tarefaList, getContext());
        recyclerViewCategoria.setAdapter(adapter);

        adapter.setOnItemClickListener(new TarefaItemClickListener() {
            @Override
            public void itemClick(Tarefa tarefa) {
                Bundle bundle = new Bundle();

                bundle.putString("id", tarefa.getId());
                bundle.putString("titulo", tarefa.getTitulo());
                bundle.putString("dia", Integer.toString(tarefa.getDia()));
                bundle.putString("mes", Integer.toString(tarefa.getMes()));
                bundle.putString("ano", Integer.toString(tarefa.getAno()));
                bundle.putString("categoria", tarefa.getCategoria());
                bundle.putString("descricao", tarefa.getDescricao());


                Navigation.findNavController(view).navigate(R.id.action_navHome_to_navCriarTarefa, bundle);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TarefaItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerViewCategoria);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categoria = parent.getItemAtPosition(position).toString();
        carregaRecycler(categoria, view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}