package com.example.loginactivity.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NavDados extends Fragment {

    private FirebaseFirestore db;

    private long diaDeOntem = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1) + (Calendar.getInstance().get(Calendar.MONTH)+1)*30 + (Calendar.getInstance().get(Calendar.YEAR))*365;
    private long diaDeHoje = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + (Calendar.getInstance().get(Calendar.MONTH)+1)*30 + (Calendar.getInstance().get(Calendar.YEAR))*365;
    private long diaDeAmanha = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1) + (Calendar.getInstance().get(Calendar.MONTH)+1)*30 + (Calendar.getInstance().get(Calendar.YEAR))*365;


    public NavDados() {
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
        View view = inflater.inflate(R.layout.fragment_nav_dados, container, false);

        db = FirebaseFirestore.getInstance();
        carregarDados(view);


        return view;
    }

    private void carregarDados(View view) {
        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int contOntem = 0;
                            int contHoje = 0;
                            int contAmanha = 0;
                            int contEstudo = 0;
                            int contDomestico = 0;
                            int contOutro = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tarefa tarefa = document.toObject(Tarefa.class);

                                long tempoDias = tarefa.allTimeThis();
                                String categoriaTarefa = tarefa.getCategoria();
                                System.out.println(tarefa.getCategoria());

                                if (tempoDias == diaDeOntem) {
                                    contOntem++;
                                }
                                if (tempoDias == diaDeHoje) {
                                    contHoje++;
                                }
                                if (tempoDias == diaDeAmanha) {
                                    contAmanha++;
                                }
                                if (categoriaTarefa.equals("Estudantil")) {
                                    contEstudo++;
                                }
                                if (categoriaTarefa.equals("Doméstico")) {
                                    contDomestico++;
                                }
                                if (categoriaTarefa.equals("Outro")) {
                                    contOutro++;
                                }
                            }
                            System.out.println(contDomestico);
                            System.out.println(contEstudo);

                            //Configurando Pie Charts
                            configuraGraficoDia(view, contOntem, contHoje, contAmanha);
                            configuraGraficoCategoria(view, contEstudo, contDomestico, contOutro);

                        } else {
                            Log.w("Falha", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    private void configuraGraficoDia(View view, int nOntem, int nHoje, int nAmanha) {
        int quantDia[] = {nOntem, nHoje, nAmanha};
        String dias[] = {"Ontem", "Hoje", "Amanhã"};

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < quantDia.length; i++) {
            pieEntries.add(new PieEntry(quantDia[i], dias[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Quantidades de tarefas por dia");
        dataSet.setDrawValues(false);
        dataSet.setColors(Color.rgb(179, 136, 255), Color.rgb(67,151, 245), Color.rgb(155, 80, 255));
        PieData data = new PieData(dataSet);

        PieChart chart = view.findViewById(R.id.graficoDia);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setData(data);
        chart.setCenterText("Dias");
        chart.setCenterTextSize(18);
        chart.animateY(1200);
        chart.invalidate();
    }

    private void configuraGraficoCategoria(View view, int nEstudo, int nDomestico, int nOutro) {
        int quantCategoria[] = {nEstudo, nDomestico, nOutro};
        String categorias[] = {"Estudantil", "Doméstico", "Outro"};

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < quantCategoria.length; i++) {
            pieEntries.add(new PieEntry(quantCategoria[i], categorias[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Quantidades de tarefas por categoria");
        dataSet.setDrawValues(false);
        dataSet.setColors(Color.rgb(179, 136, 255), Color.rgb(67,151, 245), Color.rgb(155, 80, 255));
        PieData data = new PieData(dataSet);

        PieChart chart = view.findViewById(R.id.graficoCategoria);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setData(data);
        chart.setCenterText("Categorias");
        chart.setCenterTextSize(18);
        chart.animateY(1200);
        chart.invalidate();
    }

}















