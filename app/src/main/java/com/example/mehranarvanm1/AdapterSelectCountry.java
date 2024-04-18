package com.example.mehranarvanm1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranarvanm1.databinding.ItemCountryBinding;
import com.example.mehranarvanm1.mdoel.CountryModel;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterSelectCountry extends RecyclerView.Adapter<AdapterSelectCountry.ViewHolder> {
    private List<CountryModel> countryModels;
    private List<CountryModel> search;
    private CallBackOnClick<CountryModel> countryModelCallBackOnClick;

    public AdapterSelectCountry(Context context, CallBackOnClick<CountryModel> countryModelCallBackOnClick) throws IOException {
        InputStream inputStream = context.getAssets().open("countries.json");
        String str = new String(ByteStreams.toByteArray(inputStream));
        search = new ArrayList<>(Arrays.asList(new Gson().fromJson(str, CountryModel[].class)));
        countryModels = new ArrayList<>(Arrays.asList(new Gson().fromJson(str, CountryModel[].class)));
        this.countryModelCallBackOnClick = countryModelCallBackOnClick;
    }

    public void search(String text) {

        search.clear();
        if (text.isEmpty()) {
            search.addAll(countryModels);
            return;
        }
        for (int i = 0; i < countryModels.size(); i++) {
            if (countryModels.get(i).getName().toLowerCase().contains(text.toLowerCase()))
                search.add(countryModels.get(i));
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterSelectCountry.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelectCountry.ViewHolder holder, int position) {
        holder.binding.name.setText(search.get(position).getFlag() + "  " + search.get(position).getName());
        holder.binding.codeNumber.setText(search.get(position).getDial_code());
        holder.binding.getRoot().setOnClickListener(v -> countryModelCallBackOnClick.onCLick(search.get(position)));
    }

    @Override
    public int getItemCount() {
        return search.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCountryBinding binding;

        public ViewHolder(@NonNull ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
