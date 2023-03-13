package com.example.appclimatecheckwarehouse;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclimatecheckwarehouse.activity.TemperatureSettingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    private List<Room> roomList;
    private List<Room> roomListOld;
    private OnItemClickListener mListener;
    public MyAdapter(List<Room> roomList) {

        this.roomList = roomList;
        this.roomListOld = roomList;
    }

    public List<Room> getRoomList() {
        return this.roomList;
    }
    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_room,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Room room = roomList.get(position);
        holder.name_room.setText(room.getRoomName());
        holder.temperature.setText(String.valueOf(room.getTemperature()));
        holder.humidity.setText(String.valueOf(room.getHumidity()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TemperatureSettingActivity.class);
                intent.putExtra("roomName", room.getRoomName());
                intent.putExtra("roomTemp", room.getTemperature());
                intent.putExtra("roomHumid", room.getHumidity());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return roomList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchFilter = constraint.toString();
                if(searchFilter.isEmpty()){
                    roomList = roomListOld;
                }else {
                    List<Room> list = new ArrayList<>();
                    for (Room room : roomListOld){
                        if(room.getRoomName().toLowerCase().contains(searchFilter.toLowerCase())){
                            list.add(room);
                        }
                    }
                    roomList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = roomList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                roomList = (List<Room>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public interface OnItemClickListener {

        void onItemClick(Room room);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name_room,temperature,humidity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_room = itemView.findViewById(R.id.tv_title);
            temperature = itemView.findViewById(R.id.textview_temperature);
            humidity = itemView.findViewById(R.id.textview_humidity);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }


}
