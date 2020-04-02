package com.example.eduardo.frokyvendas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    public Button btn_entrar;
    public EditText edit_email;
    public EditText edit_senha;
    private ProgressDialog progressDialog;
    public TextView txtnome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        Button cadastre_se = (Button)
                findViewById(R.id.cadastre_se);

        cadastre_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this,CadastroActivity.class);
                startActivity(it);
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    edit_email = (EditText) findViewById(R.id.E_MAIL);
                    String email = edit_email.getText().toString();
                    buscarUsuarios(email);
                }
        });
        }
        public void onBackPressed() {
       // startActivity(new Intent(this,CatalogoActivity.class));
       // finishAffinity();
        return;
        }
    private void buscarUsuarios(String email) {

        new RequisicaoHttp(LoginActivity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        trataRetornoChamadaListar(dados);
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", "select ID_USUARIO,E_MAIL,SENHA from USUARIO where E_MAIL = '" + email + "'")
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");

    }
    public void trataRetornoChamadaListar(String dados) {
        edit_senha = (EditText) findViewById(R.id.SENHA);
        String senha = edit_senha.getText().toString();
        Gson gson = new Gson();
        Type tipoListaProdutos = new TypeToken<ArrayList<Usuario>>(){}.getType();
        ArrayList<Usuario> listaUsuario = gson.fromJson(dados, tipoListaProdutos);
        if(listaUsuario == null || listaUsuario.size() == 0){
            Toast.makeText(getApplicationContext(),"Usuário e/ou Senha inválidos",Toast.LENGTH_LONG).show();
        }else {
            Usuario u = listaUsuario.get(0);
            if(u.getSenha().equals(senha)){
                Intent it = new Intent(LoginActivity.this,DadosPessoaisActivity.class);
               it.putExtra("idUsuario", u.getId());
                startActivity(it);

            }else{
                Toast.makeText(getApplicationContext(),"Usuário e/ou Senha inválidos",Toast.LENGTH_LONG).show();
            }
        }
    }
}
