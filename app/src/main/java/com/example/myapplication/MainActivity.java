package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.dao.AlunoDAO;
import com.example.myapplication.helper.AlunoAdapter;
import com.example.myapplication.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaAlunos;
    protected Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAlunos = findViewById(R.id.lista_alunos);

        onLoadListAlunos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_novo:
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
                return false;
            default:
                Toast.makeText(MainActivity.this, "Not Implemented Yet!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onLoadListAlunos(){
        AlunoDAO dao = new AlunoDAO(this);
        ArrayList<Aluno> alunos = dao.onListAlunos();
        dao.close();

        AlunoAdapter adapter = new AlunoAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);
    }
}
