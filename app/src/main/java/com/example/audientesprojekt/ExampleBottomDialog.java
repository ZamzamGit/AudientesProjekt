package com.example.audientesprojekt;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExampleBottomDialog extends BottomSheetDialogFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    public interface OnInputSelected {
        void sendInput(String input);
    }

    private OnInputSelected selected;
    private RadioGroup radioGroup;
    private String input;
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private EditText editText;
    private TextView mActionOK;
    private int shortAnimationDuration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container, false);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MaterialComponents_BottomSheetDialog);
        radioGroup = v.findViewById(R.id.group);
        rb1 = v.findViewById(R.id.one);
        rb2 = v.findViewById(R.id.two);
        rb3 = v.findViewById(R.id.three);
        rb4 = v.findViewById(R.id.four);
        rb5 = v.findViewById(R.id.custom);
        editText = v.findViewById(R.id.five);
        mActionOK = v.findViewById(R.id.OK);
        radioGroup.setOnCheckedChangeListener(this);
        editText.setVisibility(View.GONE);
        mActionOK.setOnClickListener(this);
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            selected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void onClick(View v) {
        /*input = editText.getText().toString();
        if (!input.equals("")) {
            selected.sendInput(input);
            getDialog().dismiss();
            Toast.makeText(getActivity(), "Lyden stopper om " + input + " minutter", Toast.LENGTH_SHORT).show();
        }*/
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.one:
                Toast.makeText(getActivity(), "Vælg venligst et tidspunkt" + input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.two:
                input = rb2.getText().toString();
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Afspilleren stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.three:
                input = rb3.getText().toString();
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Afspilleren stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.four:
                input = rb4.getText().toString();
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Afspilleren stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            default:
                input = editText.getText().toString();
                if (!input.equals("")) {
                    selected.sendInput(input);
                    getDialog().dismiss();
                    Toast.makeText(getActivity(), "Afspilleren stopper om " + input + " minutter", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Vælg venligst et tidspunkt ", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.custom){
            editText.setAlpha(0f);
            editText.setVisibility(View.VISIBLE);
            editText.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);
        }

        /*switch (group.getCheckedRadioButtonId()) {
            case R.id.one:
                input = rb1.getText().toString();
                rb1.setChecked(true);
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Lyden stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.two:
                input = rb2.getText().toString();
                rb2.setChecked(true);
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Lyden stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.three:
                input = rb3.getText().toString();
                rb3.setChecked(true);
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Lyden stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.four:
                input = rb4.getText().toString();
                rb4.setChecked(true);
                selected.sendInput(input);
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Lyden stopper om " + input, Toast.LENGTH_SHORT).show();
                break;
            }
        }*/
    }
}

