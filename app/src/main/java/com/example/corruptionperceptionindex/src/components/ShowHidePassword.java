package com.example.corruptionperceptionindex.src.components;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;

public class ShowHidePassword {
    public static void onViewIconClick(EditText passwordTxt, ImageView showHide) {
        boolean isPasswordVisible = passwordTxt.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showHide.setImageResource(R.drawable.hide_icon);
        } else {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showHide.setImageResource(R.drawable.view_icon);
        }

        passwordTxt.setSelection(passwordTxt.getText().length());
    }

    public static void onViewIconClick2(EditText confirmPasswordTxt, ImageView showHide) {
        boolean isConfirmPasswordVisible = confirmPasswordTxt.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);;
        isConfirmPasswordVisible = !isConfirmPasswordVisible;

        if (isConfirmPasswordVisible) {
            confirmPasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showHide.setImageResource(R.drawable.hide_icon);
        } else {
            confirmPasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showHide.setImageResource(R.drawable.view_icon);
        }

        confirmPasswordTxt.setSelection(confirmPasswordTxt.getText().length());
    }
}
