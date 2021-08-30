package com.dominikbitzer.pvl_calc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.TreeMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    private DataTransferViewModel myDataTransferViewModel;
    TextView showCountTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View fragmentSecondLayout = inflater.inflate(R.layout.fragment_second, container, false);
        showCountTextView = fragmentSecondLayout.findViewById(R.id.textview_second);

        return fragmentSecondLayout;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDataTransferViewModel = new ViewModelProvider(requireActivity()).get(DataTransferViewModel.class);
        showMyData(myDataTransferViewModel.editTextsTreeMap);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    public void showMyData(TreeMap<Integer, Integer> myTreeMap) {

        StringBuilder prettyPrinted = new StringBuilder();

        for (Map.Entry<Integer, Integer> loopedEntry:myTreeMap.entrySet()) {
            prettyPrinted.append(getResources().getResourceEntryName(loopedEntry.getKey()) + "  " + loopedEntry.getValue()+"\n");
        }

//        myTreeMap.forEach((k,v) -> Log.e(k.toString(),v.toString()));

        showCountTextView.setText(prettyPrinted);
    }

}