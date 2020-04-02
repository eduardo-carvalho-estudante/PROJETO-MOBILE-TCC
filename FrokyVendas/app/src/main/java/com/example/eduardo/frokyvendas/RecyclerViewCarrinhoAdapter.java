package com.example.eduardo.frokyvendas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewCarrinhoAdapter extends RecyclerView.Adapter<RecyclerViewCarrinhoAdapter.MyViewHolder> {
    private Context mContext;
    private List<ItemCompra> listaItens;

    public RecyclerViewCarrinhoAdapter(Context mContext, List<ItemCompra> mData) {
        this.mContext = mContext;
        this.listaItens = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_carrinho,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        int quantidade = listaItens.get(position).getQtd();
        String quantidade1 = Integer.toString(quantidade);

        double valorttotalitem = listaItens.get(position).getValorTotalItem();
        String valortotalitem1 = Double.toString(valorttotalitem);


        holder.tv_item_title.setText(listaItens.get(position).getProd().getTitle());
        holder.tv_price_item.setText(valortotalitem1);
        holder.img_item_thumbnail.setImageResource(listaItens.get(position).getProd().getThumbnail());
        holder.tv_quantidade.setText("quantidade: " + quantidade1 + " itens");
    }
    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_item_title;
        ImageView img_item_thumbnail;
        CardView cardview_carrinho;
        TextView tv_price_item;
        TextView tv_quantidade;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_item_title = (TextView) itemView.findViewById(R.id.produto_title_id1);
            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.produto_imagem_id1);
            cardview_carrinho = (CardView) itemView.findViewById(R.id.cardview_id_carrinho);
            tv_price_item = (TextView) itemView.findViewById(R.id.produto_price_id1);
            tv_quantidade = (TextView) itemView.findViewById(R.id.txtquant);
        }
    }
}
