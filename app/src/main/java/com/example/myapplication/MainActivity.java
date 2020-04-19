package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
    protected void onResume() {
        super.onResume();
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
                Intent intent = new Intent(
                        MainActivity.this,
                        FormularioActivity.class
                );
                startActivity(intent);
                return false;
            default:
                Toast.makeText(
                        MainActivity.this,
                        "NOT IMPLEMENTED YET",
                        Toast.LENGTH_LONG
                ).show();
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
                menu.getMenu().add("Mostrar Site");
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
                alunoUpdate();
                break;
            case "Remover":
                alunoDelete();
                break;
            case "Ligar":
                alunoCall();
                break;
            case "Mensagem":
                alunoSendMessage();
                break;
            case "Ver no mapa":
                alunoMap();
                break;
            case "Mostrar Site":
                alunoSite();
                break;
            default:
                Toast.makeText(
                        MainActivity.this,
                        "NOT IMPLEMENTED YET",
                        Toast.LENGTH_LONG
                ).show();
        }
    }

    private void alunoDelete() {
        AlunoDAO alunoDAO = new AlunoDAO(MainActivity.this);
        alunoDAO.onDeleteAluno(alunoSelecionado);
        Toast.makeText(
                MainActivity.this,
                "Aluno removido com sucesso",
                Toast.LENGTH_LONG
        ).show();
        onLoadListAlunos();
    }

    private void alunoUpdate() {
        Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
        intent.putExtra("aluno_selecionado", alunoSelecionado);
        startActivity(intent);
        onLoadListAlunos();
    }

    private void alunoSendMessage() {
        if (alunoSelecionado.getTelefone().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Aluno não possui telefone cadastrado",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent sms = new Intent(Intent.ACTION_VIEW);
            sms.setType("vnd.android-dir/mms-sms");
            sms.setData(Uri.parse("smsto:" + Uri.encode(alunoSelecionado.getTelefone())));
            sms.putExtra("sms_body", "Mensagem");
            startActivity(sms);
        }
    }

    private void alunoMap() {
        if (alunoSelecionado.getEndereco().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Aluno não possui endereço cadastrado",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Uri googleMapUrl = Uri.parse("geo:0,0?q=" + alunoSelecionado.getEndereco());
            Intent gmapUrl = new Intent(Intent.ACTION_VIEW, googleMapUrl);
            gmapUrl.setPackage("com.google.android.apps.maps");
            startActivity(gmapUrl);
        }
    }

    private void alunoCall() {
        if (alunoSelecionado.getTelefone().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Aluno não possui telefone cadastrado",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent intent = new Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", alunoSelecionado.getTelefone(), null)
            );
            startActivity(intent);
        }
    }

    private void alunoSite() {
        if (alunoSelecionado.getSite().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Aluno não possui telefone cadastrado",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent navegador = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://" + alunoSelecionado.getSite())
            );
            startActivity(navegador);
        }
    }
}
