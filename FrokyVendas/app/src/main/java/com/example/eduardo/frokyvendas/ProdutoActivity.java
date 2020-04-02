package com.example.eduardo.frokyvendas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProdutoActivity extends AppCompatActivity {

    private TextView tvtitle,tvdescricao;
    private ImageView img;
    public Button btnadcCarrinho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão


        tvtitle = (TextView) findViewById(R.id.txttitle) ;
        tvdescricao = (TextView) findViewById(R.id.txtdescricao) ;
        img = (ImageView) findViewById(R.id.produtothumbnail) ;
        btnadcCarrinho = (Button) findViewById(R.id.btnteste);




        Intent intent = getIntent();
        final int idProd = intent.getExtras().getInt("Id");
        Produto prod = null;
        for( Produto p : CatalogoActivity.catalogo) {
            if (p.getId() == idProd) {
                prod = p;
                break;
            }
        }
        final Produto finalProd = prod;


        btnadcCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_quant = (EditText) findViewById(R.id.editquant);
                if(edit_quant.getText().toString().length() == 0){
                    edit_quant.setError("Digite a Quantidade");
                }else {
                    final String quantidade = edit_quant.getText().toString();
                    final int quanti = Integer.parseInt(quantidade);
                    final double preco = Double.parseDouble(finalProd.getPrice());
                    final double valorTotal = (preco * quanti);


                    // ItemCompra itemCompra = new ItemCompra(finalProd,quanti,preco,valorTotal);
                    ItemCompra itemCompra = new ItemCompra();
                    itemCompra.setProd(finalProd);
                    itemCompra.setQtd(quanti);
                    itemCompra.setValorUnit(preco);
                    itemCompra.setValorTotalItem(valorTotal);
                    Carrinho2Activity.lista.add(itemCompra);
                    Toast.makeText(getApplicationContext(),"Produto Adicionado ao Carrinho",Toast.LENGTH_LONG).show();
                    //Intent it = new Intent(ProdutoActivity.this,Carrinho2Activity.class);
                    //startActivity(it);
                }
            }
        });
        String Title = intent.getExtras().getString("Title");
        String Descricao = intent.getExtras().getString("Descricao");
        int Image = intent.getExtras().getInt("Thumbnail");

        tvtitle.setText(Title);
        tvdescricao.setText(Descricao);
        img.setImageResource(Image);
        getSupportActionBar().setTitle(Title);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, CatalogoActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
