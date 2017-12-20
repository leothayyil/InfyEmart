package com.example.user.infyemart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by USER on 20-12-2017.
 */

public class Fragment_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_1,container,false);
        EditText edtxtPersonName_Fragment = view.findViewById(R.id.edtxtPersonName);
        Button btnSayHi_Fragment = view.findViewById(R.id.btnSayHi);

        String name=edtxtPersonName_Fragment.getText().toString();
        FragmentManager fm=getFragmentManager();
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
