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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Produto> listaProdutos;

    public RecyclerViewAdapter(Context mContext, List<Produto> mData) {
        this.mContext = mContext;
        this.listaProdutos = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_produto_title.setText(listaProdutos.get(position).getTitle());
        holder.tv_price_title.setText( listaProdutos.get(position).getPrice());
        holder.img_produto_thumbnail.setImageResource(listaProdutos.get(position).getThumbnail());
        holder.cardview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,ProdutoActivity.class);
                intent.putExtra("Id", listaProdutos.get(position).getId());
                intent.putExtra("Title", listaProdutos.get(position).getTitle());
                intent.putExtra("Descricao", listaProdutos.get(position).getDescricao());
                intent.putExtra("Preço", listaProdutos.get(position).getPrice());
                intent.putExtra("Thumbnail", listaProdutos.get(position).getThumbnail());
                intent.putExtra("Category", listaProdutos.get(position).getCategory());
                mContext.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_produto_title;
        ImageView img_produto_thumbnail;
        CardView cardview;
        TextView tv_price_title;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_produto_title = (TextView) itemView.findViewById(R.id.produto_title_id);
            img_produto_thumbnail = (ImageView) itemView.findViewById(R.id.produto_imagem_id);
            cardview = (CardView) itemView.findViewById(R.id.cardview_id);
            tv_price_title = (TextView) itemView.findViewById(R.id.produto_price_id);
        }
    }
}
