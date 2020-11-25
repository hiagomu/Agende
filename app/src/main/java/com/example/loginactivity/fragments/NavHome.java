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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.adapter.TarefaAdapter;
import com.example.loginactivity.ui.helper.TarefaItemTouchHelper;
import com.example.loginactivity.ui.listener.TarefaItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NavHome extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView tarefaListRecycler;
    private TarefaAdapter adapter;
    private List<Tarefa> tarefaList;
    private TextView tarefasHojeEditTxt;
    private SearchView procurarTarefa;

    private long diaDeHoje = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + (Calendar.getInstance().get(Calendar.MONTH)+1)*30 + (Calendar.getInstance().get(Calendar.YEAR))*365;
    private int tarefasHoje;

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
        pesquisarTarefa(view);
        return view;
    }

    private void carregarDados(View view) {
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
                                tarefaList.add(tarefa);

                                if (tarefa.allTimeThis() == diaDeHoje) {
                                    tarefasHoje++;
                                }
                            }

                            //Quantas tarefas tem no no dia
                            configuraContador(view);
                            // Organizar tarefas pras mais recentes
                            Collections.sort(tarefaList);

                            configuraRecycler(view);
                        } else {
                            Log.w("Falha", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void pesquisarTarefa(View view) {

        procurarTarefa = view.findViewById(R.id.procurarHomeSearchView);
        procurarTarefa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
                                        if ((tarefa.getTitulo().toLowerCase()).contains(query.toLowerCase()) ||
                                                (tarefa.getDescricao().toLowerCase()).contains(query.toLowerCase()) ||
                                                (Integer.toString(tarefa.getDia()).toLowerCase()).contains(query.toLowerCase()) ||
                                                (Integer.toString(tarefa.getMes()).toLowerCase()).contains(query.toLowerCase()) ||
                                                (Integer.toString(tarefa.getAno()).toLowerCase()).contains(query.toLowerCase())){
                                            tarefaList.add(tarefa);
                                            if (tarefa.allTimeThis() == diaDeHoje) {
                                                tarefasHoje++;
                                            }
                                        }
                                    }

                                    //Quantas tarefas tem no no dia
                                    configuraContador(view);
                                    // Organizar tarefas pras mais recentes
                                    Collections.sort(tarefaList);

                                    configuraRecycler(view);
                                } else {
                                    Log.w("Falha", "Error getting documents.", task.getException());
                                }
                            }
                        });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                carregarDados(view);
                return false;
            }
        });
    }

    private void configuraContador(View view) {
        tarefasHojeEditTxt = view.findViewById(R.id.tarefasHojeHomeTxtView);
        tarefasHojeEditTxt.setText(Integer.toString(tarefasHoje));
        tarefasHoje = 0;
    }

    private void configuraRecycler(View view) {

        tarefaListRecycler = view.findViewById(R.id.tarefaListRecycler);
        tarefaListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TarefaAdapter(tarefaList, getContext());
        tarefaListRecycler.setAdapter(adapter);

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
        itemTouchHelper.attachToRecyclerView(tarefaListRecycler);

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