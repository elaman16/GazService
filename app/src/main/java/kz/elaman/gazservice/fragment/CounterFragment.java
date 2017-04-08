package kz.elaman.gazservice.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.glomadrian.codeinputlib.CodeInput;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;

import kz.elaman.gazservice.R;
import kz.elaman.gazservice.model.Counter;
import kz.elaman.gazservice.utils.PrefHelper;


public class CounterFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CounterFragment() {

    }


    public static CounterFragment newInstance(String param1, String param2) {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private DatabaseReference mDatabase;
    private DatabaseReference mCounters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mCounters = mDatabase.child("Показания счетчика");
    }

    private Button btnSendCounter;
    CodeInput cInput;
    NumberPicker np1, np2, np3, np4, np5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_send_indicate, container, false);

        np1 = (NumberPicker) view.findViewById(R.id.number_picker);
        np2 = (NumberPicker) view.findViewById(R.id.number_picker1);
        np3 = (NumberPicker) view.findViewById(R.id.number_picker2);
        np4 = (NumberPicker) view.findViewById(R.id.number_picker3);
        np5 = (NumberPicker) view.findViewById(R.id.number_picker4);




        view.findViewById(R.id.btn_send_counter).setOnClickListener(this);

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_counter:{
                uploadCounterReading();
            }
        }
    }

    private void uploadCounterReading() {
        String key = mCounters.push().getKey();



        String codeString = String.valueOf(np1.getValue())+String.valueOf(np2.getValue())+String.valueOf(np3.getValue())+
                String.valueOf(np4.getValue())+String.valueOf(np5.getValue());

        Counter counter = new Counter();
        counter.setId(key);
        counter.setEmail(new PrefHelper(getActivity()).getUserEmail());
        counter.setCounter(codeString);

        mCounters.child(key).setValue(counter);
        Toast.makeText(getContext(), "Показания передпны удачно!", Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
