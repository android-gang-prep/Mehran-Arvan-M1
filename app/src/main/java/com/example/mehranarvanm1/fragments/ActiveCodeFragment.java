package com.example.mehranarvanm1.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mehranarvanm1.CallBackNetwork;
import com.example.mehranarvanm1.R;
import com.example.mehranarvanm1.RepoNetwork;
import com.example.mehranarvanm1.databinding.ActiveFragmentBinding;

public class ActiveCodeFragment extends Fragment {

/*
    KeyEvent.Callback callback = new KeyEvent.Callback() {
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return false;
        }

        @Override
        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
            return false;
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return false;
        }

        @Override
        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
            return false;
        }
    };*/

    private ActiveFragmentBinding binding;
    private TextView[] textViews = new TextView[4];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActiveFragmentBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViews[0] = binding.code1;
        textViews[1] = binding.code2;
        textViews[2] = binding.code3;
        textViews[3] = binding.code4;

        for (TextView textView : textViews) {
            textView.setOnClickListener(v -> showInput());
        }

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (TextView textView : textViews) {
                    textView.setText("");
                }
                if (s.toString().isEmpty()) return;

                if (s.toString().length() > 4) {
                    binding.editText.setText(s.toString().substring(0, 4));
                }

                int size = s.toString().length();


                for (int i = 0; i < size; i++) {
                    textViews[i].setText(s.toString().substring(i, i + 1));
                }
                if (size == 4) sendActiveCode();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showInput() {
        new Handler().postDelayed(() -> {
            binding.editText.requestFocus();
            binding.editText.requestFocusFromTouch();

            InputMethodManager inputMethodManager = getContext().getSystemService(InputMethodManager.class);
            inputMethodManager.showSoftInput(binding.editText, InputMethodManager.SHOW_FORCED);
            if (!binding.editText.getText().toString().isEmpty())
                binding.editText.setSelection(binding.editText.getText().toString().length());
        }, 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        showInput();
    }

    private void sendActiveCode() {
        RepoNetwork.getInstance().activeCode(binding.editText.getText().toString().trim(), new CallBackNetwork<String>() {
            @Override
            public void onResponse(String s) {
                Login();
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


    private void Login() {
        RepoNetwork.getInstance().Login(getArguments().getString("email"), getArguments().getString("password"), new CallBackNetwork<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    getActivity().runOnUiThread(() -> {
                        try {
                            getActivity().runOnUiThread(() -> {
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_data", 0);
                                sharedPreferences.edit().putString("token", s).apply();
                                Log.i("TAG", "run: "+s);
                                Navigation.findNavController(getView()).navigate(R.id.action_activeCodeFragment_to_startFragment);
                            });
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


}
