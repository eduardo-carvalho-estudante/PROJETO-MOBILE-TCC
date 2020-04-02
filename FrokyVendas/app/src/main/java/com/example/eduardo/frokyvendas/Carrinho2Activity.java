package com.example.eduardo.frokyvendas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.frokyvendas.Config.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carrinho2Activity extends AppCompatActivity {
    public TextView txttotal;
    public TextView txtquant;
    private ProgressDialog progressDialog;
    public Button btnfinalizar;
    int idCompra;
    double valorTotal;
    String totalcompra;
    public static List<ItemCompra> lista = new ArrayList<ItemCompra>();
    public RecyclerView carrinho;

    public static final int PAYPAL_REQUEST_CODE = 7171;

    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);// Sandbox Só para Teste

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho2);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
        final String dataformatada = formatar.format(data);


        carrinho = (RecyclerView) findViewById(R.id.recyclerview_id2);
        RecyclerViewCarrinhoAdapter myAdapter = new RecyclerViewCarrinhoAdapter(this, lista);
        carrinho.setLayoutManager(new GridLayoutManager(this, 1));
        carrinho.setAdapter(myAdapter);


        double total = 0;
        for (ItemCompra item : lista) {
            double totalItem = item.getValorTotalItem();
            total = total + totalItem;
        }
        final double valortotal = total;

        final String totalcompra = Double.toString(total);
        txttotal = (TextView) findViewById(R.id.txttotal);
        txttotal.setText(totalcompra);


        btnfinalizar = (Button) findViewById(R.id.btn_finalizar);
        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Compra compra = new Compra();
                compra.setDataCompra(dataformatada);
                compra.setValorTotal(valortotal);
                criar(compra, lista);
            }
        });


    }

    private void processPayment() {
        totalcompra = txttotal.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(totalcompra)), "BRL",
                "Pagamento para FrokyVendas", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                       String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("paymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", totalcompra)


                      // 3Intent intent = new Intent(Carrinho2Activity.this,PaymentDetails.class);
                        //intent.putExtra("data",confirmation.toJSONObject().toString(4));
                        //intent .putExtra("PaymentAmount", totalcompra);
                        //startActivity(intent);

                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
             else if (requestCode == RESULT_CANCELED)
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
        }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this, "Inválido", Toast.LENGTH_SHORT).show();
        }

    private void criar(ItemCompra i) {
        String comandoSql =
                String.format(
                        "insert into ITEM_COMPRA (FK_PRODUTO,QUANTIDADE,VALOR_UNIT,VALOR_TOTAL,FK_PEDIDO) " + // o id sera gerado pelo banco
                                "values('%s','%s','%s','%s','%s')",
                        i.getProd().getId(),
                        i.getQtd(),
                        i.getValorUnit(),
                        i.getValorTotalItem(),
                        i.getIdCompra());
        new RequisicaoHttp(Carrinho2Activity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        if (dados.contains("comandoExecutado") &&
                                dados.contains("true")) {
                            Toast.makeText(Carrinho2Activity.this, "Inclusão conluída", Toast.LENGTH_LONG);
                            Carrinho2Activity.this.finish();
                        }
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", comandoSql)
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");
    }

    private void criar(Compra c, final List<ItemCompra> lista) {
        String comandoSql =
                String.format(
                        "insert into PEDIDO (DATA_DA_COMPRA,VALOR_TOTAL) " + // o id sera gerado pelo banco
                                "values('%s','%s');select MAX(ID_PEDIDO) from PEDIDO",
                        c.getDataCompra(),
                        c.getValorTotal());
        new RequisicaoHttp(Carrinho2Activity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {

                        buscarIdGerado();
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", comandoSql)
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");
    }

    private void buscarIdGerado() {
        String comandoSql = "select MAX(ID_PEDIDO) as id from PEDIDO";
        new RequisicaoHttp(Carrinho2Activity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        Gson gson = new Gson();
                        Type tipoListadeId = new TypeToken<ArrayList<IdGerado>>(){}.getType();
                        ArrayList<IdGerado> listaId = gson.fromJson(dados, tipoListadeId);
                        IdGerado i = listaId.get(0);
                        idCompra = i.getId();

                        for (ItemCompra item : lista){
                            item.setIdCompra(idCompra);
                            criar(item);
                            atualizar(item);
                        }
                        processPayment();
                       // buscarValorTotalGerado(idCompra);
                        lista.clear();
                      //  Toast.makeText(getApplicationContext(),"Finalizado",Toast.LENGTH_LONG).show();

                    }
                },
                progressDialog)
                .addFormParam("comandoSql", comandoSql)
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");

    }

    private void buscarValorTotalGerado(int idCompra) {
        String comandoSql = "select VALOR_TOTAL from PEDIDO where ID_PEDIDO = '" + idCompra + "'";
        new RequisicaoHttp(Carrinho2Activity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        Gson gson = new Gson();
                        Type tipoListadeValor = new TypeToken<ArrayList<ValorTotalGerado>>(){}.getType();
                        ArrayList<ValorTotalGerado> listavalor = gson.fromJson(dados, tipoListadeValor);
                        ValorTotalGerado v = listavalor.get(0);
                        valorTotal = v.getValorTotal();
                        lista.clear();
                        //  Toast.makeText(getApplicationContext(),"Finalizado",Toast.LENGTH_LONG).show();
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", comandoSql)
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");

    }

    private void atualizar(ItemCompra i) {
        String comandoSql =
                String.format(
                        "update PRODUTO " +
                                "set QUANTIDADE = QUANTIDADE - '" + i.getQtd() + "'" +
                                "where ID_PRODUTO = %s",
                        i.getProd().getId());
        new RequisicaoHttp(Carrinho2Activity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        if (dados.contains("comandoExecutado") &&
                                dados.contains("true")) {
                            Toast.makeText(Carrinho2Activity.this,
                                    "Atualização conluída", Toast.LENGTH_LONG);
                            Carrinho2Activity.this.finish();
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