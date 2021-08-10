package ru.samsung.itschool.mdev.students.models;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.samsung.itschool.mdev.students.R;
import ru.samsung.itschool.mdev.students.EditActivity;
import ru.samsung.itschool.mdev.students.utils.StudentDBHelper;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Student> studentList;
    private Context context;
    private RecyclerView recyclerView;

    public MyAdapter(List<Student> myDataset, Context context, RecyclerView recyclerView) {
        studentList = myDataset;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Student student = studentList.get(position);
        holder.tvName.setText(String.format("%s: %s", context.getResources().getString(R.string.name), student.getName()));
        holder.tvAge.setText(String.format("%s: %s", context.getResources().getString(R.string.age), student.getAge()));
        holder.tvSchool.setText(String.format("%s: %s", context.getResources().getString(R.string.school), student.getSchool()));
        Picasso.get().load(student.getPhoto()).into(holder.imageView);

        holder.view.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.choose);
            builder.setMessage(R.string.edirorremove);
            builder.setPositiveButton(R.string.edit, (dialog, which) -> {
                gotoupdateactivity(student.getId());
            });
            builder.setNeutralButton(R.string.remove, (dialog, which) -> {
                StudentDBHelper dbHelper = new StudentDBHelper(context);
                dbHelper.deletePerson(student.getId());
                studentList.remove(position);
                recyclerView.removeViewAt(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, studentList.size());
                notifyDataSetChanged();
            });
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
    }

    private void gotoupdateactivity(long personId){
        Intent gotoupdate = new Intent(context, EditActivity.class);
        gotoupdate.putExtra("USERID", personId);
        context.startActivity(gotoupdate);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAge, tvSchool;
        public ImageView imageView;
        public View view;
        public ViewHolder(View v) {
            super(v);
            this.view = v;
            tvName = v.findViewById(R.id.name);
            tvAge =  v.findViewById(R.id.age);
            tvSchool = v.findViewById(R.id.school);
            imageView = v.findViewById(R.id.image);
        }
    }

}