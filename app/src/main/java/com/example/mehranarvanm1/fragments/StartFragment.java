package com.example.mehranarvanm1.fragments;

import android.os.Bundle;
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
import com.example.mehranarvanm1.databinding.FinishFragmentBinding;
import com.example.mehranarvanm1.databinding.SelectCountryFragmentBinding;

import java.io.IOException;

public class StartFragment extends Fragment {

    private FinishFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FinishFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
