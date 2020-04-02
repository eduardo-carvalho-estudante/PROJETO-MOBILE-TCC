package com.example.eduardo.frokyvendas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.frokyvendas.Config.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FinalizacaoActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    TextView txtidpedido;
    String totalcompra;
    String totaldacompra;

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
        setContentView(R.layout.activity_finalizacao);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        //int idCompra = getIntent().getExtras().getInt("idCompra");
        //txtidpedido = findViewById(R.id.txtidcompra);
        //String idcompra1 = Integer.toString(idCompra);
        //txtidpedido.setText("Identificação do Pedido: " + idcompra1);



        Double totalcompra2 = getIntent().getExtras().getDouble("valorTotalGerado");

        totalcompra = Double.toString(totalcompra2);

        totaldacompra = totalcompra;

        Button btncontinuar = (Button) findViewById(R.id.btncontinuar);

        btncontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });
    }
    private void processPayment() {
        totalcompra = totaldacompra;
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(totalcompra)), "BRL",
                "Pagamento para Froky", PayPalPayment.PAYMENT_INTENT_SALE);
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
                if (confirmation == null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", totalcompra)


                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Inválido", Toast.LENGTH_SHORT).show();

    }
}
