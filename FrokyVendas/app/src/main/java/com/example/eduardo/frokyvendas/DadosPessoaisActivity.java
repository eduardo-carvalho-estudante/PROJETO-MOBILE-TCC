package com.example.eduardo.frokyvendas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DadosPessoaisActivity extends AppCompatActivity {

    public EditText edit_email;
    private ProgressDialog progressDialog;
    public TextView txtnome;
    private ArrayList<Usuario> listaPessoas;
    public Button btnvoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);



        btnvoltar = (Button) findViewById(R.id.btnvoltar);
        btnvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DadosPessoaisActivity.this,CatalogoActivity.class);
                startActivity(it);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        int idUsuario = getIntent().getExtras().getInt("idUsuario");
        buscarPessoas(idUsuario);
    }

    private void buscarPessoas(int idUsuario) {
        new RequisicaoHttp(DadosPessoaisActivity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        trataRetornoChamadaListar(dados);
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", "select * from USUARIO where ID_USUARIO = '" + idUsuario + "'")
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");
    }

    public void trataRetornoChamadaListar(String dados) {
        Gson gson = new Gson();
        Type tipoListaPessoas = new TypeToken<ArrayList<Usuario>>(){}.getType();
        listaPessoas = gson.fromJson(dados, tipoListaPessoas);

        Usuario u = listaPessoas.get(0);

        TextView txtNomeUsuario = findViewById(R.id.txtnome);
        txtNomeUsuario.setText(u.getNome());

        TextView txtCpf = findViewById(R.id.txtcpf);
        txtCpf.setText(u.getCpf());

        TextView txtTelefone = findViewById(R.id.txttelefone);
        txtTelefone.setText(u.getTelefone());

        TextView txtemail = findViewById(R.id.txtemail);
        txtemail.setText(u.getEmail());

        TextView txtsenha = findViewById(R.id.txtsenha);
        txtsenha.setText(u.getSenha());

        TextView txtnascimento = findViewById(R.id.txtnascimento);
        txtnascimento.setText(u.getData_de_nascimento());

        TextView txtsexo = findViewById(R.id.txtsexo1);
        txtsexo.setText(u.getSexo());


        /*
        ArrayAdapter<Usuario> adapter =
                new ArrayAdapter<Usuario>(
                        DadosPessoais.this,
                        android.R.layout.simple_list_item_1,
                        listaPessoas);

        listView.setAdapter(adapter);
        */
    }
}