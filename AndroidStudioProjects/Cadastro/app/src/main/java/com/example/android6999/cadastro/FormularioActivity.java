package com.example.android6999.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android6999.dao.AlunoDAO;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //Método para ativiar a Display da ActionBar - Assim botao voltar, etc
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Método Helper - Para ajudar a manipular a tela
        this.helper = new FormularioHelper(this);

        //Recebendo a Edição
        Intent edit = getIntent();
        Aluno aluno = (Aluno) edit.getSerializableExtra("aluno");

            if (aluno != null){
                helper.preencheTela(aluno);
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            //Botão Criado na Actionbar - itens do Menu
            case R.id.menu_formulario_ok:

                if(helper.ISValido()) {
                    Aluno aluno = helper.pegaAlunoDoFormulario();

                   // Toast.makeText(FormularioActivity.this, "Ok - Aluno Salvo com Sucesso", Toast.LENGTH_LONG).show();
                    AlunoDAO alunoDAO = new AlunoDAO(this);

                    if (aluno.getId() == null){
                        alunoDAO.insere(aluno);
                    } else {
                        alunoDAO.altera(aluno);
                    }


                    alunoDAO.close();

                    finish();


                    return true;


                }else{
                    helper.mostraErro();
                    return false;
                }

            // Dando uma ação ao botão voltar da ActionBar
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}

