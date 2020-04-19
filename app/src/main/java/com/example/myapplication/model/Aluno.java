package com.example.myapplication.model;

import java.io.Serializable;

public class Aluno implements Serializable {
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private String foto;
    private Double nota;

    public static class AlunoSerializer implements Serializable {
        private Long id;
        private String nome;
        private String endereco;
        private String telefone;
        private String site;
        private String foto;
        private Double nota;

        public AlunoSerializer(Aluno aluno) {
            id = aluno.getId();
            nome = aluno.getNome();
            endereco = aluno.getEndereco();
            telefone = aluno.getTelefone();
            site = aluno.getSite();
            foto = aluno.getFoto();
            nota = aluno.getNota();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", site='" + site + '\'' +
                ", foto='" + foto + '\'' +
                ", nota=" + nota +
                '}';
    }
}
