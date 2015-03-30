package com.wasihaider.viewpagerpreview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Test extends Fragment {

    private FragmentEventListener mListener;
    View v;
    int pos;
    // TODO: Rename and change types and number of parameters
    public static Test newInstance(int pos) {
        Test fragment = new Test();
        fragment.pos = pos;
        return fragment;
    }

    public Test() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v  = inflater.inflate(R.layout.fragment_test, container, false);
        int r = (int)(Math.random()*255);
        int g = (int)(Math.random()*255);
        int b = (int)(Math.random()*255);
        v.setBackgroundColor(Color.rgb(r, g, b));

        TextView tv = (TextView)v.findViewById(R.id.textview);
        tv.setText(Integer.toString(pos));
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentEventListener) activity;
            mListener.onFragmentAttached(pos);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FragmentEventListener {
        // TODO: Update argument type and name
        public void onFragmentAttached(int pos);
    }

}
