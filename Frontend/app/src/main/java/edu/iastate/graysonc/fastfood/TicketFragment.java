package edu.iastate.graysonc.fastfood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//fragment_ticket

/**
 * A fragment to allow users to submit new tickets
 */
public class TicketFragment extends Fragment {
    //fragment_ticket
    private static final String TAG = "SubmitTicket";


    public TicketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }



}
