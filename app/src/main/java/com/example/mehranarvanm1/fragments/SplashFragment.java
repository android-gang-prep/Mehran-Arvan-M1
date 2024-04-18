package com.example.mehranarvanm1.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mehranarvanm1.R;
import com.example.mehranarvanm1.databinding.SplashBinding;

public class SplashFragment extends Fragment {
    private SplashBinding binding;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }    Runnable runnable = this::PlayVideo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.login.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_splashFragment_to_loginFragment));
        binding.signup.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_splashFragment_to_signUpFragment));

        handler = new Handler();
    }

    @Override
    public void onResume() {
        super.onResume();
        PlayVideo();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        binding.videoView.pause();
    }

    private void PlayVideo() {
        binding.videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.splash));
        binding.videoView.start();
        handler.postDelayed(runnable, 10000);
    }




}
