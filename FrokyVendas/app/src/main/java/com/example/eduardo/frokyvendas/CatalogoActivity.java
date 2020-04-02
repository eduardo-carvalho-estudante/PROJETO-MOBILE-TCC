package com.example.eduardo.frokyvendas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static List<Produto> catalogo = new ArrayList<>();
    public RecyclerView myrv;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, catalogo);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }


    protected void onResume() {
        super.onResume();
        buscarProdutos();
    }

    private void buscarProdutos() {

        new RequisicaoHttp(CatalogoActivity.this,
                new RequisicaoHttp.TratadorRetornoChamada() {
                    @Override
                    public void trataRetornoChamada(String dados) {
                        trataRetornoChamadaListar(dados);
                    }
                },
                progressDialog)
                .addFormParam("comandoSql", "select ID_PRODUTO,NOME, DESCRICAO, QUANTIDADE, PRECO from PRODUTO")
                .execute("http://192.168.43.142:7070/PonteABD/PonteServlet");

    }

    public void trataRetornoChamadaListar(String dados) {
        Gson gson = new Gson();
        Type tipoListaProdutos = new TypeToken<ArrayList<Produto>>(){}.getType();
        ArrayList<Produto> listaProd = gson.fromJson(dados, tipoListaProdutos);
        catalogo.clear();
        catalogo.addAll(listaProd);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, listaProd);
        myrv.setAdapter(myAdapter);
    }


        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catalogo, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SobreActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this,Carrinho2Activity.class);
            startActivity(intent);

       //     Intent intent = new Intent(this,DadosPessoaisActivity.class);
       //     startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,SobreActivity.class);
            startActivity(intent);


        }
        //else if (id == R.id.nav_manage) {


        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    }
