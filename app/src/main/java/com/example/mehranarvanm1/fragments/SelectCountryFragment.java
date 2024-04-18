package com.example.mehranarvanm1.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mehranarvanm1.AdapterSelectCountry;
import com.example.mehranarvanm1.R;
import com.example.mehranarvanm1.databinding.SelectCountryFragmentBinding;

import java.io.IOException;

public class SelectCountryFragment extends Fragment {

    AdapterSelectCountry adapterSelectCountry = null;
    private SelectCountryFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectCountryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            adapterSelectCountry = new AdapterSelectCountry(getContext(), countryModel -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", getArguments().getString("name"));
                bundle.putString("email", getArguments().getString("email"));
                bundle.putString("password", getArguments().getString("password"));
                bundle.putString("code", countryModel.getCode());
                Navigation.findNavController(getView()).navigate(R.id.action_selectCountryFragment2_to_enterPhoneNumberFragment, bundle);
            });
            binding.rec.setAdapter(adapterSelectCountry);
            binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.search.setOnClickListener(v -> {
            binding.search.setVisibility(View.GONE);
            binding.title.setVisibility(View.GONE);
            binding.editText.setVisibility(View.VISIBLE);
        });

        binding.back.setOnClickListener(v -> {
            if (binding.search.getVisibility() == View.GONE) {
                binding.search.setVisibility(View.VISIBLE);
                binding.title.setVisibility(View.VISIBLE);
                binding.editText.setVisibility(View.GONE);
                adapterSelectCountry.search("");
            } else {
                try {
                    Navigation.findNavController(v).popBackStack();
                } catch (Exception e) {
                }
            }
        });

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterSelectCountry.search(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}
