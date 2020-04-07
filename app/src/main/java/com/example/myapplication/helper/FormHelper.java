package com.example.myapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.myapplication.FormularioActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Aluno;

public class FormHelper {
    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private SeekBar nota;
    private ImageView foto;
    private Aluno aluno;

    public FormHelper(FormularioActivity form) {
        nome = form.findViewById(R.id.nome);
        telefone = form.findViewById(R.id.telefone);
        endereco = form.findViewById(R.id.endereco);
        site = form.findViewById(R.id.site);
        nota = form.findViewById(R.id.nota);
        foto = form.findViewById(R.id.foto);

        aluno = new Aluno();
    }

    public Aluno getAlunoFromForm() {
        aluno.setNome(nome.getEditableText().toString());
        aluno.setTelefone(telefone.getEditableText().toString());
        aluno.setEndereco(endereco.getEditableText().toString());
        aluno.setSite(site.getEditableText().toString());
        aluno.setNota((double) nota.getProgress());

        return aluno;
    }

    public void setAlunoOnForm(Aluno aluno) {
        this.aluno = aluno;

        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        nota.setProgress((int) Math.round(aluno.getNota()));

        if (aluno.getFoto() != null) {
            this.loadFoto(aluno.getFoto());
        }
    }

    private void loadFoto(String path) {
        Bitmap imagem = BitmapFactory.decodeFile(path);
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100,100, true);

        aluno.setFoto(path);
        foto.setImageBitmap(imagemReduzida);
    }
}
