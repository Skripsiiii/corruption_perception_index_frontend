package com.example.corruptionperceptionindex.fragments;

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
import com.example.corruptionperceptionindex.items.ScreenItem;

public class IntroFragment extends Fragment {

    private static final String ARG_SCREEN_ITEM = "screenItem";
    private ScreenItem mScreenItem;
    public static IntroFragment newInstance(ScreenItem screenItem) {
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
            mScreenItem = (ScreenItem) getArguments().getSerializable(ARG_SCREEN_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_screen, container, false);

        ImageView imageView = rootView.findViewById(R.id.intro_image);
        TextView titleTextView = rootView.findViewById(R.id.intro_title);
        TextView descriptionTextView = rootView.findViewById(R.id.intro_description);

        if (mScreenItem != null) {
            imageView.setImageResource(mScreenItem.getScreenImg());
            titleTextView.setText(mScreenItem.getTitle());
            descriptionTextView.setText(mScreenItem.getDescription());
        }

        return rootView;
    }
}
