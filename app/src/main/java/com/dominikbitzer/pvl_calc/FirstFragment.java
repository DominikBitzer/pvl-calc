package com.dominikbitzer.pvl_calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.dominikbitzer.pvl_calc.databinding.FragmentFirstBinding;

import java.util.HashMap;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private DataTransferViewModel myDataTransferViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDataTransferViewModel = new ViewModelProvider(requireActivity()).get(DataTransferViewModel.class);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataTransferViewModel.editTextsHashMap = getAllEditText();
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public HashMap<Integer, Integer> getAllEditText() {

        ConstraintLayout currentConstraintLayout = binding.getRoot();
        HashMap<Integer, Integer> editTextValuesHashMap = new HashMap<>();

        for (int i = 0; i < currentConstraintLayout.getChildCount(); i++) {
            if (currentConstraintLayout.getChildAt(i) instanceof EditText) {
                EditText loopedEditText = (EditText)currentConstraintLayout.getChildAt(i);
                editTextValuesHashMap.put(loopedEditText.getId(), Integer.parseInt(loopedEditText.getText().toString()));
            }
        }

        return editTextValuesHashMap;

    }

}