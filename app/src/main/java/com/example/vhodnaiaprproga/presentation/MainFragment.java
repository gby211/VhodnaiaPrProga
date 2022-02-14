package com.example.vhodnaiaprproga.presentation;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vhodnaiaprproga.R;
import com.example.vhodnaiaprproga.databinding.FragmentMainBinding;
import com.example.vhodnaiaprproga.domain.MainViewModel;


public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    MainViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.spinner.setPrompt("Выберите валюту");
        binding.spinner2.setPrompt("Выберите валюту");
        binding.spinner.setSelection(0);
        binding.spinner2.setSelection(0);

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: ");

                String tmp = getResources().getStringArray(R.array.currency)[binding.spinner.getSelectedItemPosition()] + "/" + getResources().getStringArray(R.array.currency)[binding.spinner2.getSelectedItemPosition()];
                mViewModel.getValue(tmp).observe(getViewLifecycleOwner(), new Observer<Float>() {
                    @Override
                    public void onChanged(Float aFloat) {
                        Float firstValue = Float.valueOf(binding.editTextTextPersonName.getText().toString());
                        firstValue = firstValue * aFloat;
                        binding.editTextTextPersonName2.setText(firstValue.toString());
                    }
                });
            }
        });


        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_main, container, false);
    }
}