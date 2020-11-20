package com.example.loginactivity.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class NavCriarTarefa extends Fragment {

    private EditText tituloNovaTarefa;
    private TextView dataNovaTarefa;
    private EditText categNovaTarefa;
    private EditText descNovaTarefa;
    private Button salvarNovaTarefa;
    private Button calendarioNovaTarefa;
    private FirebaseFirestore db;
    private String id;
    private Tarefa tarefa;
    private Calendar c;
    private DatePickerDialog dpd;

    public NavCriarTarefa() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_criar_tarefa, container, false);
        carregarCampos(view);

        mostrarCalendario();

        if (getArguments() != null) {
            dadosEdicao(view);
        } else {
            salvarTarefa(view);
        }

        return view;
    }


    private void mostrarCalendario() {
        calendarioNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dataNovaTarefa.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });
    }

    private void salvarTarefa(View view) {
        salvarNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarForm()) {
                    extrairDados();
                    goToNavHome(view);
                }
            }
        });
    }

    private void dadosEdicao(View view) {
        Toast.makeText(getContext(), "Editar tarefa!", Toast.LENGTH_LONG).show();

        tituloNovaTarefa.setText(getArguments().getString("titulo"));
        dataNovaTarefa.setText(getArguments().getString("data"));
        categNovaTarefa.setText(getArguments().getString("categoria"));
        descNovaTarefa.setText(getArguments().getString("descricao"));



        salvarNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                updateTarefa();
                db.collection("tarefas").document(tarefa.getId()).set(tarefa);
                Navigation.findNavController(view).navigate(R.id.action_navCriarTarefa_to_navHome);
            }
        });

    }

    private void updateTarefa() {
        String titulo = tituloNovaTarefa.getText().toString();
        String data = dataNovaTarefa.getText().toString();
        String categoria = categNovaTarefa.getText().toString();
        String descricao = descNovaTarefa.getText().toString();
        String id = getArguments().getString("id");

        //Preciso pegar a tarefa clicada no NavHome
        tarefa = new Tarefa(titulo, data, categoria, descricao);
        tarefa.setId(id);
    }

    private void goToNavHome(View view) {
        db = FirebaseFirestore.getInstance();
        db.collection("tarefas")
                .add(tarefa)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Sucesso ao adicionar", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Falha ao adicionar", "Error adding document", e);
                    }
                });
        Navigation.findNavController(view).navigate(R.id.action_navCriarTarefa_to_navHome);
    }

    private void extrairDados() {
        String title = tituloNovaTarefa.getText().toString();
        String data = dataNovaTarefa.getText().toString();
        String categoria = categNovaTarefa.getText().toString();
        String descricao = descNovaTarefa.getText().toString();

        tarefa = new Tarefa(title, data, categoria, descricao);
    }

    private boolean validarForm() {
        if (tituloNovaTarefa.getText().toString().isEmpty()) {
            tituloNovaTarefa.setError("Informe o título");
            tituloNovaTarefa.requestFocus();
            return false;
        }
        if (categNovaTarefa.getText().toString().isEmpty()) {
            categNovaTarefa.setError("Informe a categoria");
            categNovaTarefa.requestFocus();
            return false;
        }
        if (descNovaTarefa.getText().toString().isEmpty()) {
            descNovaTarefa.setError("Informe a descrição");
            descNovaTarefa.requestFocus();
            return false;
        }
        if (dataNovaTarefa.getText().toString().isEmpty()) {
            dataNovaTarefa.setError("Informe a data");
            dataNovaTarefa.requestFocus();
            return false;
        }
        return true;
    }


    private void carregarCampos(View view) {
        tituloNovaTarefa = view.findViewById(R.id.tituloNovaTarefaEditTxt);
        dataNovaTarefa = view.findViewById(R.id.dataNovaTarefaTxtView);
        categNovaTarefa = view.findViewById(R.id.categNovaTarefaEditTxt);
        descNovaTarefa = view.findViewById(R.id.descNovaTarefaEditTxt);
        salvarNovaTarefa = view.findViewById(R.id.salvarNovaTarefaButton);
        calendarioNovaTarefa = view.findViewById(R.id.dataNovaTarefaButton);
    }


}