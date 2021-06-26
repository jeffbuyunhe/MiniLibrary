package com.example.minilibrary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MenuRecViewAdapter extends RecyclerView.Adapter<MenuRecViewAdapter.ViewHolder> {

    private ArrayList<MenuItem> menuItemArrayList;
    private Context context;

    public MenuRecViewAdapter(Context context){
        this.context = context;
        menuItemArrayList = Utils.getInstance(context).getMenuItemArrayList();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MenuRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MenuRecViewAdapter.ViewHolder holder, int position) {
        initCard(holder, position);
        setCardListeners(holder, position);
    }

    private void setCardListeners(@NonNull @org.jetbrains.annotations.NotNull MenuRecViewAdapter.ViewHolder holder, int position) {
        holder.menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, menuItemArrayList.get(position).getActivity());
                context.startActivity(intent);
            }
        });
    }

    private void initCard(@NonNull @org.jetbrains.annotations.NotNull MenuRecViewAdapter.ViewHolder holder, int position) {
        holder.menuImage.setImageResource(menuItemArrayList.get(position).getActionIcon());
        holder.menuActionName.setText(menuItemArrayList.get(position).getActionName());

        holder.menuItem.setStrokeColor(context.getResources().getColor(menuItemArrayList.get(position).getActionColour()));
        holder.menuActionName.setTextColor(context.getResources().getColor(menuItemArrayList.get(position).getActionColour()));
        holder.menuImage.setColorFilter(context.getResources().getColor(menuItemArrayList.get(position).getActionColour()));
    }

    @Override
    public int getItemCount() {
        return menuItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView menuItem;
        private ImageView menuImage;
        private TextView menuActionName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            menuItem = itemView.findViewById(R.id.menuItem);
            menuImage = itemView.findViewById(R.id.menuImage);
            menuActionName = itemView.findViewById(R.id.menuActionName);
        }
    }
}
