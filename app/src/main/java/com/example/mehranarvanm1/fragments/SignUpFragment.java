package com.example.mehranarvanm1.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mehranarvanm1.R;
import com.example.mehranarvanm1.databinding.SignUpFragmentBinding;

public class SignUpFragment extends Fragment {

    private SignUpFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SignUpFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.next.setOnClickListener(v -> {
            if (binding.name.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Name is empty", Toast.LENGTH_SHORT).show();
                return;
            }
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

            Bundle bundle = new Bundle();
            bundle.putString("name",binding.name.getText().toString().trim());
            bundle.putString("email",binding.email.getText().toString().trim());
            bundle.putString("password",binding.password.getText().toString().trim());
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_enterPhoneNumberFragment,bundle);
        });
    }
}
