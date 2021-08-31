package com.dominikbitzer.pvl_calc;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.TreeMap;

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
                try {
                    myDataTransferViewModel.editTextsTreeMap = getAllEditText(view);
                } catch (NotAllEditTextsFilledOutException myException) {
                    return;
                }
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

    public TreeMap<Integer, Integer> getAllEditText(View view) throws NotAllEditTextsFilledOutException {

        ConstraintLayout currentConstraintLayout = binding.getRoot();
        TreeMap<Integer, Integer> editTextValuesTreeMap = new TreeMap<>();

        boolean someFieldNotFilledOutYet = false;
        for (int i = 0; i < currentConstraintLayout.getChildCount(); i++) {
            View currentEditText = currentConstraintLayout.getChildAt(i);
            if (currentEditText instanceof EditText) {
                if(TextUtils.isEmpty(((EditText) currentEditText).getText().toString())) {
                    ((EditText) currentEditText).setError(getString(R.string.editText_not_filled_out));
                    someFieldNotFilledOutYet = true;
                } else {
                    editTextValuesTreeMap.put(((EditText)currentEditText).getId(), Integer.parseInt(((EditText)currentEditText).getText().toString()));
                }
            }
        }

        if(someFieldNotFilledOutYet) {
            Snackbar.make(view, getString(R.string.snackbar_please_fill_all_fields), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            throw new NotAllEditTextsFilledOutException("Some field not filled out yet!");
        } else {
            return editTextValuesTreeMap;
        }

    }

    public static class NotAllEditTextsFilledOutException extends Exception {
        public NotAllEditTextsFilledOutException(String errorMessage) {
            super(errorMessage);
        }
    }

}
