package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.myapplication.dao.AlunoDAO;
import com.example.myapplication.helper.AlunoAdapter;
import com.example.myapplication.model.Aluno;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listaAlunos;
    protected Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAlunos = findViewById(R.id.lista_alunos);

        onLoadListAlunos();
        onLoadAddListeners();
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

    private void onLoadListAlunos(){
        AlunoDAO dao = new AlunoDAO(this);
        ArrayList<Aluno> alunos = dao.onListAlunos();
        dao.close();

        AlunoAdapter adapter = new AlunoAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);
    }

    public void getAlunoSelecionado(int position) {
        alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(position);
    }

    private void onLoadAddListeners(){
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getAlunoSelecionado(position);
                PopupMenu menu = new PopupMenu(MainActivity.this, view);
                menu.getMenu().add("Editar");
                menu.getMenu().add("Remover");
                menu.getMenu().add("Ligar");
                menu.getMenu().add("Mensagem");
                menu.getMenu().add("Ver no mapa");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        popUpMenuActions(item);
                        return true;
                    }
                });
                menu.show();
            }
        });

    }

    public void popUpMenuActions(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Editar":
                // @todo - show form and set field values
                break;
            case "Remover":
                alunoDelete();
                break;
            case "Ligar":
                //@todo - call send call method
                break;
            case "Mensagem":
                //@todo - call send message method
                break;
            case "Ver no mapa":
                //@todo - call method show on map
                break;
            default:
                Toast.makeText(MainActivity.this, "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
        }
    }

    private void alunoDelete() {
        AlunoDAO alunoDAO = new AlunoDAO(MainActivity.this);
        alunoDAO.onDeleteAluno(alunoSelecionado);
        onLoadListAlunos();
    }

}
