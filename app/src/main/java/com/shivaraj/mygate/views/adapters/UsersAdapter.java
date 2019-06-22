package com.shivaraj.mygate.views.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shivaraj.mygate.R;
import com.shivaraj.mygate.databinding.ItemBinding;
import com.shivaraj.mygate.model.local.UserModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    List<UserModel> mJobsList;

    public UsersAdapter(List<UserModel> jobs){
        mJobsList = jobs;
    }

    public void setJobsList(List<UserModel> newJobs) {
        mJobsList.addAll(newJobs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item,
                        viewGroup, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {

        Picasso.get().load(mJobsList.get(position).getUrl())
                .into(userViewHolder.binding.imageView);
        UserModel model = mJobsList.get(position);
        model.setName("User" + position);
        userViewHolder.binding.setUser(model);
        //userViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mJobsList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        final ItemBinding binding;

        public UserViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
