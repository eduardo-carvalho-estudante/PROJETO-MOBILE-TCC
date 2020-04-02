package com.example.eduardo.frokyvendas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private Usuario usuario;
    public static List<Usuario> usuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText edit_nascimento = (EditText) findViewById(R.id.datadenascimento);
        final EditText edit_cpf2 = (EditText) findViewById(R.id.Cpf);
        EditText edit_cpf = (EditText) findViewById(R.id.Cpf);
        final EditText edit_telefone = (EditText) findViewById(R.id.telefone);
        final EditText edit_senha = (EditText) findViewById(R.id.senha);
        final EditText edit_email = (EditText) findViewById(R.id.email);
        final EditText edit_nome = (EditText) findViewById(R.id.editText3);
        Button btncadastar = (Button) findViewById(R.id.btncadastrar);
        final Spinner sexo = (Spinner) findViewById(R.id.spinner);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastro");

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(edit_nascimento, smf);
        edit_nascimento.addTextChangedListener(maskTextWatcher);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskTextWatcher1 = new MaskTextWatcher(edit_cpf, smf2);
        edit_cpf.addTextChangedListener(maskTextWatcher1);

        SimpleMaskFormatter smf3 = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher2 = new MaskTextWatcher(edit_telefone, smf3);
        edit_telefone.addTextChangedListener(maskTextWatcher2);

        btncadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_nome.getText().toString().length() == 0){
                    edit_nome.setError("Digite o Nome");
                }else if(edit_cpf2.getText().toString().length() == 0) {
                    edit_cpf2.setError("Digite o Cpf");
                }else if(edit_telefone.getText().toString().length() == 0) {
                    edit_telefone.setError("Digite o Telefone");
                }else if(edit_nascimento.getText().toString().length() == 0) {
                    edit_nascimento.setError("Digite a Data de Nascimento");
                }else if(edit_email.getText().toString().length() == 0) {
                    edit_email.setError("Digite o Email");
                }else if(edit_senha.getText().toString().length() == 0) {
                    edit_senha.setError("Digite a Senha");
                }else{
                    usuario = new Usuario();
                    usuario.setCpf(edit_cpf2.getText().toString());
                    usuario.setNome(edit_nome.getText().toString());
                    usuario.setSexo(sexo.getSelectedItem().toString());
                    usuario.setTelefone(edit_telefone.getText().toString());
                    usuario.setEmail(edit_email.getText().toString());
                    usuario.setSenha(edit_senha.getText().toString());
                    usuario.setData_de_nascimento(edit_nascimento.getText().toString());
                    criar(usuario);

                }



            }
        });
    }

    private void criar(Usuario u) {
        String comandoSql =
                String.format(
                        "insert into USUARIO (CPF,NOME,SEXO,TELEFONE,E_MAIL,SENHA,DATA_DE_NASCIMENTO) " + // o id sera gerado pelo banco
                                "values('%s','%s','%s','%s','%s','%s','%s')",
                        u.getCpf(),
                        u.getNome(),
                        u.getSexo(),
                        u.getTelefone(),
                        u.getEmail(),
                        u.getSenha(),
                        u.getData_de_nascimento());
        new RequisicaoHttp(CadastroActivity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        if (dados.contains("comandoExecutado") &&
                                dados.contains("true")) {
                            Toast.makeText(CadastroActivity.this, "Inclusão conluída", Toast.LENGTH_LONG);
                            CadastroActivity.this.finish();
                        }
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", comandoSql)
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, LoginActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}


