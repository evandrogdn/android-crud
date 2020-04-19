package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dao.AlunoDAO;
import com.example.myapplication.helper.FormHelper;
import com.example.myapplication.model.Aluno;


public class FormularioActivity extends AppCompatActivity {

    private Button btnGravar;
    private Aluno alunoToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Aluno aluno = (Aluno) getIntent().getSerializableExtra("aluno_selecionado");
            if (aluno != null) {
                this.alunoToUpdate = aluno;
                (new FormHelper(FormularioActivity.this)).setAlunoOnForm(aluno);
            }
        }


        btnGravar = findViewById(R.id.botao);

        addListeners();
    }

    private void addListeners() {
        btnGravar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (alunoToUpdate == null) {
                    saveAluno();
                } else {
                    updateAluno();
                }
                finish();
            }
        });
    }

    private void saveAluno(){
        FormHelper fh = new FormHelper(this);
        Aluno aluno = fh.getAlunoFromForm();

        (new AlunoDAO(this)).onInsertAluno(aluno);
        Toast.makeText(
                FormularioActivity.this,
                "Aluno inserido com sucesso",
                Toast.LENGTH_LONG
        ).show();
    }

    private void updateAluno()
    {
        Aluno aluno = (new FormHelper(this)).getAlunoFromForm();
        aluno.setId(alunoToUpdate.getId());

        (new AlunoDAO(this)).onUpdateAluno(aluno);

        Toast.makeText(
                FormularioActivity.this,
                "Aluno atualizado com sucesso",
                Toast.LENGTH_LONG
        ).show();
    }
}
