package com.example.android6999.cadastro;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.RatingBar;

/**
 * Created by android6999 on 18/07/17.
 */

public class FormularioHelper {

    private Aluno aluno;

    private EditText nome;
    private EditText telefone;
    private EditText site;
    private EditText endereco;
    private TextInputLayout nomeLabel;
    private RatingBar nota;

    public FormularioHelper(FormularioActivity activity){

        this.aluno = new Aluno();

        this.nome = (EditText) activity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        this.site = (EditText) activity.findViewById(R.id.formulario_site);
        this.endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        this.nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        this.nomeLabel = (TextInputLayout) activity.findViewById(R.id.nomeLabel);

    }

    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getRating()));

        return aluno;

    }

   public boolean ISValido(){
        boolean isvazio = nome.getText().toString().isEmpty();
        return ! isvazio;

    }

    public void mostraErro(){
      nome.setError("Nome deve ser preenchido!");

        nomeLabel.setError("Nome deve ser preenchido!");

   }

    public void preencheTela(Aluno aluno) {

        this.nome.setText(aluno.getNome());
        this.endereco.setText(aluno.getEndereco());
        this.site.setText(aluno.getSite());
        this.telefone.setText(aluno.getTelefone());
        this.nota.setRating((float) aluno.getNota());

        this.aluno = aluno;
    }
}
