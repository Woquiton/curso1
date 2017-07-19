package com.example.android6999.cadastro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android6999.dao.AlunoDAO;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private List<Aluno> alunos;
    ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        //Chama a verificação de permissões
        Permissao.fazPermissao(this);


        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        //REGISTRANDO O MENU DE CONTEXTO
        registerForContextMenu(listaAlunos);


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                /*
                Aluno aluno = (Aluno) adapter.getItemAtPosition(posicao);
                Toast.makeText(ListaAlunosActivity.this, "Nome: " + aluno.getNome() + " Posição:" + posicao, Toast.LENGTH_LONG).show();
                */

                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(posicao);
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                edicao.putExtra("aluno", aluno);
                startActivity(edicao);

            }
        });


        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

              //  Toast.makeText(ListaAlunosActivity.this, "Click Longo", Toast.LENGTH_LONG).show();

                return false;
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.cadastrarAluno);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intencao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                        startActivity(intencao);
                    }
                }
        );
    }

    //Menu de Contexto
    public void onCreateContextMenu (ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);



        MenuItem ligar = menu.add("Ligar");
        MenuItem enviarSMS = menu.add("Enviar SMS");
        MenuItem irParaSite = menu.add("Navegar no site");
        MenuItem mapa = menu.add("Achar no Mapa");
        MenuItem deletar = menu.add("Deletar");

        //MAPA
            Intent irParaMapa = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("geo:0,0?z=" + alunoSelecionado.getEndereco());
                irParaMapa.setData(data);
                mapa.setIntent(irParaMapa);

        //SMS
            Intent sms = new Intent(Intent.ACTION_VIEW);
            sms.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
            sms.putExtra("sms_body", "mensagem de texto");
            enviarSMS.setIntent(sms);

        //LIGAR
            Intent intentLigar = new Intent(Intent.ACTION_CALL);
            intentLigar.setData( Uri.parse("tel:" + alunoSelecionado.getTelefone()));
            ligar.setIntent(intentLigar);

        //SITE
        Intent abrirSite = new Intent(Intent.ACTION_VIEW);

        String url;
        if (alunoSelecionado.getSite().contains("http")){
            url=alunoSelecionado.getSite();

        }else{

            url="http://" + alunoSelecionado.getSite();
        }
        abrirSite.setData(Uri.parse(url));
        irParaSite.setIntent(abrirSite);



       //DELETAR

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Detelar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Quero",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                        dao.deletar(alunoSelecionado);
                                        dao.close();
                                        carregaLista();
                                    }
                                } ).setNegativeButton("Não", null).show();

                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    private void carregaLista() {

        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();
        this.adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1,alunos);
        listaAlunos.setAdapter(adapter);
    }
}
