package com.example.mehranarvanm1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mehranarvanm1.CallBackNetwork;
import com.example.mehranarvanm1.R;
import com.example.mehranarvanm1.RepoNetwork;
import com.example.mehranarvanm1.databinding.SignupPhoneBinding;
import com.example.mehranarvanm1.mdoel.CountryModel;
import com.example.mehranarvanm1.mdoel.SignUpPostModel;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnterPhoneNumberFragment extends Fragment {

    private SignupPhoneBinding binding;
    private CountryModel countryModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SignupPhoneBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.countryCode.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", getArguments().getString("name"));
            bundle.putString("email", getArguments().getString("email"));
            bundle.putString("password", getArguments().getString("password"));
            Navigation.findNavController(v).navigate(R.id.action_enterPhoneNumberFragment_to_selectCountryFragment2, bundle);
        });
        countryModel = new CountryModel("Afghanistan", "\uD83C\uDDE6\uD83C\uDDEB", "AF", "+93");

        if (getArguments().getString("code") != null) try {
            countryModel = getCountry(getArguments().getString("code"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.countryCode.setText(countryModel.getFlag() + "  " + countryModel.getDial_code());
        binding.login.setOnClickListener(v -> {
            if (binding.phone.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Phone is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            SignUP();

        });
    }

    private void SignUP() {
        RepoNetwork.getInstance().SignUp(new SignUpPostModel(getArguments().getString("email"), getArguments().getString("name"), getArguments().getString("password"), countryModel.getDial_code() + binding.phone.getText().toString().trim()), new CallBackNetwork<String>() {
            @Override
            public void onResponse(String s) {
                sendSMS();
            }

            @Override
            public void onFail(Throwable throwable, boolean internet) {
                try {
                    getActivity().runOnUiThread(() -> {
                        if (internet) {
                            Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                }
            }
        });
    }

    private void sendSMS() {
        RepoNetwork.getInstance().sendSMS(countryModel.getDial_code(), binding.phone.getText().toString().trim(), new CallBackNetwork<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    getActivity().runOnUiThread(() -> {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("name", getArguments().getString("name"));
                            bundle.putString("email", getArguments().getString("email"));
                            bundle.putString("password", getArguments().getString("password"));
                            Navigation.findNavController(getView()).navigate(R.id.action_enterPhoneNumberFragment_to_activeCodeFragment, bundle);
                        } catch (Exception e) {
                        }

                    });
                } catch (Exception e) {
                }
            }

            @Override
            public void onFail(Throwable throwable, boolean internet) {
                try {
                    getActivity().runOnUiThread(() -> {
                        if (internet) {
                            Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                }

            }
        });
    }

    private CountryModel getCountry(String code) throws IOException {

        List<CountryModel> countryModels = new ArrayList<>(Arrays.asList(new Gson().fromJson(new String(ByteStreams.toByteArray(getContext().getAssets().open("countries.json"))), CountryModel[].class)));

        for (int i = 0; i < countryModels.size(); i++) {

            if (code.equals(countryModels.get(i).getCode())) return countryModels.get(i);
        }

        return null;

    }
}
