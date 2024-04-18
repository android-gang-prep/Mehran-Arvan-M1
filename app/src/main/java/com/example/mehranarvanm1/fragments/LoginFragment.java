package com.example.mehranarvanm1.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.example.mehranarvanm1.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.login.setOnClickListener(v -> {
            if (binding.email.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString().trim()).matches()) {
                Toast.makeText(getContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.password.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Password is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.password.getText().toString().length() < 6) {
                Toast.makeText(getContext(), "password most more then 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            RepoNetwork.getInstance().Login(binding.email.getText().toString().trim(), binding.password.getText().toString(), new CallBackNetwork<String>() {
                @Override
                public void onResponse(String s) {

                    try {
                        getActivity().runOnUiThread(() -> {
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_data", 0);
                            sharedPreferences.edit().putString("token", s).apply();
                            Log.i("TAG", "run: "+s);
                            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_startFragment);
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
        });
    }
}
