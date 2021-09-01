package com.dominikbitzer.pvl_calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.androidplot.xy.XYPlot;

import java.util.TreeMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    private DataTransferViewModel myDataTransferViewModel;
    TextView showInputParametersTextView;
    TextView showCalculatedResultsTextView;
    TextView showResult1TextView;
    private XYPlot heartPlotView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View fragmentSecondLayout = inflater.inflate(R.layout.fragment_second, container, false);
        showInputParametersTextView = fragmentSecondLayout.findViewById(R.id.textview_second_fragment_input_parameters);
        showCalculatedResultsTextView = fragmentSecondLayout.findViewById(R.id.textview_second_fragment_calculated_results);
        showResult1TextView = fragmentSecondLayout.findViewById(R.id.result_1_sum_bpm_and_volume);

        heartPlotView = (XYPlot) fragmentSecondLayout.findViewById(R.id.my_heart_flow_plot);

        return fragmentSecondLayout;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDataTransferViewModel = new ViewModelProvider(requireActivity()).get(DataTransferViewModel.class);
        TreeMap<String, Float> myCalculatedResults = calculateResults(myDataTransferViewModel.editTextsTreeMap);

        showInputParametersTextView.setText(
                prettyPrintTreeMap(myDataTransferViewModel.editTextsTreeMap)
                        // Add a label in front of the results
                        .insert(0, "Input-parameters:\n"));

        showResult1TextView.setText("Calculated hear flow results: \n" + myCalculatedResults.toString().replace(",", "\n"));

        HeartFlowGraphCreator.setGraph(myCalculatedResults, heartPlotView, this.getContext());

/* Deactivated button
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    public TreeMap<String, Float> calculateResults(TreeMap<Integer, Integer> myTreeMap) {
        TreeMap<String, Float> calculatedResults = new TreeMap<>();
        calculatedResults.put("E_Nd(avg)",
                (float)myTreeMap.get(R.id.input_1_preejection_period));
        calculatedResults.put("E_Nd(est)",
                (float)myTreeMap.get(R.id.input_2_systolic_period));
        calculatedResults.put("E_es(eb)",
                (float)myTreeMap.get(R.id.input_3a_end_systolic_volume));
        return calculatedResults;
    }

/* Alternative implementation for matching results with field IDs
    public TreeMap<Integer, Integer> calculateResults_alt2(TreeMap<Integer, Integer> myTreeMap) {
        TreeMap<Integer, Integer> calculatedResults = new TreeMap<>();
        calculatedResults.put(R.id.result_1_sum_bpm_and_volume,
                myTreeMap.get(R.id.input_1_preejection_period)
        );
        return calculatedResults;
    }
*/

    public StringBuilder prettyPrintTreeMap(TreeMap<Integer, Integer> myTreeMap) {

        StringBuilder prettyPrinted = new StringBuilder();

        for (Map.Entry<Integer, Integer> loopedEntry:myTreeMap.entrySet()) {
            prettyPrinted
                    .append(getResources().getResourceEntryName(loopedEntry.getKey())).append("\t   ")
                    .append(loopedEntry.getValue()).append("\n");
        }

        return prettyPrinted;
    }

}