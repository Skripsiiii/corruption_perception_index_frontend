package com.example.corruptionperceptionindex.src.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.screens.main.MainMenu;

public class sixthRegister extends Fragment {

    Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sixth_register, container, false);

        nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainmenuPage = new Intent(getActivity(), MainMenu.class);
                startActivity(mainmenuPage);
                getActivity().finish();
            }
        });

        return view;
    }
}