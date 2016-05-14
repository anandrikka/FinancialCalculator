package com.wordpress.techanand.financialcalculator.activities.recurringdeposit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.techanand.financialcalculator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecurringDepositDetailsTab extends Fragment {


    public RecurringDepositDetailsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recurring_deposit_details_tab, container, false);
    }

}
