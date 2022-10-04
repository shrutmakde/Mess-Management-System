package com.example.mess_management;

import static com.example.mess_management.PollingActivity.sum;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class PollingAdapter extends FirebaseRecyclerAdapter<MainModel, PollingAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PollingAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PollingAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MainModel model) {

        holder.name.setText(model.getName()/*"Bhai yaha pe menu hona chahiye"*/);
        float num = (Integer.parseInt(model.getCount()))*100/sum;
        holder.count.setProgress((int) num);
        holder.poll.setText(String.valueOf((int) num)+"%");

    }

    @NonNull
    @Override
    public PollingAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.polling_item,parent,false);
        return new PollingAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,poll;
        ProgressBar count;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.dishName);
            count = (ProgressBar) itemView.findViewById(R.id.dishPoll);
            poll = (TextView) itemView.findViewById(R.id.pollText);
        }
    }

}
