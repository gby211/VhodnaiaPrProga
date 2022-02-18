package com.example.vhodnaiaprproga.presentation;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vhodnaiaprproga.R;
import com.example.vhodnaiaprproga.databinding.FragmentMainBinding;
import com.example.vhodnaiaprproga.domain.MainViewModel;
import com.example.vhodnaiaprproga.repository.roomDB.CurrencyDTO;

import java.net.InetAddress;
import java.util.ArrayList;


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
                Double convertFromDoub = 0.0;
                String currencyFrom = "";
                String currencyTo = "";
                try{
                    checkFields(binding.editTextTextPersonName);
                } catch (IllegalArgumentException e){
                    binding.editTextTextPersonName.setError("Empty field");
                    return;
                }
                convertFromDoub = Double.parseDouble(binding.editTextTextPersonName.getText().toString());
                currencyFrom = binding.spinner.getSelectedItem().toString();
                currencyTo = binding.spinner2.getSelectedItem().toString();

                Double finalConvertFromDoub = convertFromDoub;
                String finalCurrencyFrom = currencyFrom;
                String finalCurrencyTo = currencyTo;

                if(isInternetAvailable()) {
                    mViewModel.getValue(currencyFrom, currencyTo).observe(this, v -> {
                        double converted = finalConvertFromDoub * v.getConversion_rate();
                        //Toast.makeText(this, Double.toString(converted), Toast.LENGTH_LONG).show();
                        binding.editTextTextPersonName.setText(Double.toString(converted));
                        mViewModel.insertDTO(finalCurrencyFrom, finalCurrencyTo, v.getConversion_rate());
                        Toast.makeText(this, "Write to db", Toast.LENGTH_LONG).show();
                    });
                } else{
                    mainViewModel.getByPair(currencyFrom, currencyTo).observe(this, v -> {
                        double converted = finalConvertFromDoub * v.get(0);

                        binding.etConverted.setText(Double.toString(converted));
                    });
                }





//                Log.d("TAG", "onClick: ");
//
//                String tmp = getResources().getStringArray(R.array.currency)[binding.spinner.getSelectedItemPosition()] + "/" + getResources().getStringArray(R.array.currency)[binding.spinner2.getSelectedItemPosition()];
//                mViewModel.getValue(tmp).observe(getViewLifecycleOwner(), new Observer<Float>() {
//                    @Override
//                    public void onChanged(Float aFloat) {
//                        Float firstValue = Float.valueOf(binding.editTextTextPersonName.getText().toString());
//                        firstValue = firstValue * aFloat;
//                        binding.editTextTextPersonName2.setText(firstValue.toString());
//                    }
//                });
            }
        });


        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    private void setConvertedSpinnerFromDatabase(String currencyFrom){
        mViewModel.getAllConvertToByConvertFrom(currencyFrom).observe(this, v ->{
            ArrayList<String> values = new ArrayList<>();
            for(CurrencyDTO dto : v) {
                if (!values.contains(dto.getConvertTo()))
                    values.add(dto.getConvertTo());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (getContext(), android.R.layout.simple_spinner_item, values);

            binding.spinner.setAdapter(adapter);
            binding.spinner2.setAdapter(adapter);
        });
    }


    private void setSpinnerFromDatabase(){
        binding.spinner.setAdapter(null);
        binding.spinner2.setAdapter(null);

        mViewModel.getAllConvertFrom().observe(this, v -> {
            ArrayList<String> values = new ArrayList<>();
                for(CurrencyDTO dto : v) {
                    if (!values.contains(dto.getConvertFrom()))
                        values.add(dto.getConvertFrom());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>
                        (getContext(), android.R.layout.simple_spinner_item, values);

                binding.spinner.setAdapter(adapter);
            });

            binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    setConvertedSpinnerFromDatabase((String) adapterView.getItemAtPosition(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    private void checkFields(EditText et){
        if(et.getText().toString().isEmpty())
            throw new IllegalArgumentException("Empty field");
    }
}