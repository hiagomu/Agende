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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Tarefa;
import com.example.loginactivity.ui.MainActivity;
import com.example.loginactivity.ui.NavActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class NavCriarTarefa extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText tituloNovaTarefa;
    private TextView dataNovaTarefa;
    private EditText categNovaTarefa;
    private EditText descNovaTarefa;
    private Button salvarNovaTarefa;
    private Button calendarioNovaTarefa;
    private Spinner categoriaNovaTarefaSpin;
    private FirebaseFirestore db;
    private String id;
    private Tarefa tarefa;
    private Calendar c;
    private DatePickerDialog dpd;
    private String categoria;
    private int totalDias;

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
                        totalDias = year*365 + (month+1)*30 + dayOfMonth;
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
        descNovaTarefa.setText(getArguments().getString("descricao"));
        String categoria = getArguments().getString("categoria");

        if (categoria.equals("Estudantil"))
            categoriaNovaTarefaSpin.setSelection(0);
        else if (categoria.equals("Doméstico"))
            categoriaNovaTarefaSpin.setSelection(1);
        else if (categoria.equals("Outro"))
            categoriaNovaTarefaSpin.setSelection(2);

        salvarNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                updateTarefa();
                db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(tarefa.getId()).set(tarefa);
                Navigation.findNavController(view).navigate(R.id.action_navCriarTarefa_to_navHome);
            }
        });

    }

    private void updateTarefa() {
        String titulo = tituloNovaTarefa.getText().toString();
        String data = dataNovaTarefa.getText().toString();
        String descricao = descNovaTarefa.getText().toString();
        String id = getArguments().getString("id");

        //Preciso pegar a tarefa clicada no NavHome
        tarefa = new Tarefa(titulo, data, categoria, descricao, totalDias);
        tarefa.setId(id);
    }

    private void goToNavHome(View view) {
        db = FirebaseFirestore.getInstance();
        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
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
        String descricao = descNovaTarefa.getText().toString();

        tarefa = new Tarefa(title, data, categoria, descricao, totalDias);
    }

    private boolean validarForm() {
        if (tituloNovaTarefa.getText().toString().isEmpty()) {
            tituloNovaTarefa.setError("Informe o título");
            tituloNovaTarefa.requestFocus();
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
        descNovaTarefa = view.findViewById(R.id.descNovaTarefaEditTxt);
        salvarNovaTarefa = view.findViewById(R.id.salvarNovaTarefaButton);
        calendarioNovaTarefa = view.findViewById(R.id.dataNovaTarefaButton);

        //Select para input da categoria
        categoriaNovaTarefaSpin = view.findViewById(R.id.categNovaTarefaSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaNovaTarefaSpin.setAdapter(adapter);
        categoriaNovaTarefaSpin.setOnItemSelectedListener(this);
    }

    // Métodos para pegar item selecionado na lista
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoria = parent.getItemAtPosition(position).toString();
        if (categoria == "Outro") {

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}