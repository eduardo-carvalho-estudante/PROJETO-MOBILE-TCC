package com.example.eduardo.frokyvendas;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequisicaoHttp extends AsyncTask<String, Void, String> {
    private Activity activity;
    private TratadorRetornoChamada tratadorRetorno;
    private ProgressDialog progressDialog;
    private Map<String, String> formParans = new HashMap<>();

    public interface TratadorRetornoChamada {
        public void trataRetornoChamada(String dados);
    }

    public RequisicaoHttp(Activity activity,
                          TratadorRetornoChamada tratadorRetorno,
                          ProgressDialog progressDialog) {
        this.activity = activity;
        this.tratadorRetorno = tratadorRetorno;
        this.progressDialog = progressDialog;
    }

    public RequisicaoHttp addFormParam(String chave, String valor) {
        this.formParans.put(chave, valor);
        return this;
    }

    @Override
    protected String doInBackground(String... params) {
        // Esta etapa é usada para executar a tarefa em background ou fazer a chamada para o webservice
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            String urlStr = montarUrl(params[0]);
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //urlConnection.connect();
                   InputStream inputStream = urlConnection.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(inputStream));
                   String linha;
                   StringBuffer buffer = new StringBuffer();
                   while((linha = reader.readLine()) != null) {
                       buffer.append(linha);
                       buffer.append("\n");
                   }
                   return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }


    private String montarUrl(String base) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder(base);
        if (formParans.size() > 0) {
            url.append("?");
            Set<Map.Entry<String, String>> entradas = formParans.entrySet();

        for (Map.Entry<String, String> entrada : entradas) {

            url.append(entrada.getKey()).append("=").append(entrada.getValue()).append("&");

        }

        }

        String urlComParametros = url.substring(0, url.length() - 1).toString();
        //urlComParametros = URLEncoder.encode(urlComParametros, "UTF-8");
           return urlComParametros;
    }
    @Override
    protected void onPreExecute(){
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: "+Thread.currentThread().getName());
        this.progressDialog = ProgressDialog.show((Activity)this.activity, "Por favor Aguarde ...",
                "Carregando ...");
    }
    @Override
    protected void onPostExecute(String dados) {
        // Faça alguma coisa com os dados
        if (this.tratadorRetorno != null) {
            this.tratadorRetorno.trataRetornoChamada(dados);
        }         this.progressDialog.dismiss();
    }
}


