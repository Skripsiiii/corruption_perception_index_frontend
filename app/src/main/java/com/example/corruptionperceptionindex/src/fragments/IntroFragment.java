package com.example.corruptionperceptionindex.src.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.items.IntroItem;

public class IntroFragment extends Fragment {

    private static final String ARG_SCREEN_ITEM = "screenItem";
    private IntroItem mIntroItem;
    public static IntroFragment newInstance(IntroItem screenItem) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCREEN_ITEM, screenItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIntroItem = (IntroItem) getArguments().getSerializable(ARG_SCREEN_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_intro, container, false);

        ImageView imageView = rootView.findViewById(R.id.intro_image);
        TextView titleTextView = rootView.findViewById(R.id.intro_title);
        TextView descriptionTextView = rootView.findViewById(R.id.intro_description);

        if (mIntroItem != null) {
            imageView.setImageResource(mIntroItem.getScreenImg());
            titleTextView.setText(mIntroItem.getTitle());
            descriptionTextView.setText(mIntroItem.getDescription());
        }

        return rootView;
    }
}
