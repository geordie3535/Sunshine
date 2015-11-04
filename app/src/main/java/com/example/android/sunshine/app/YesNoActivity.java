package com.example.android.sunshine.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class YesNoActivity extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Are you Ok?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Toast toast = Toast.makeText(getActivity(), "That's the way mate !", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Toast toast = Toast.makeText(getActivity(), "Sorry for that mate", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                })
                .setNeutralButton("DON'T KNOW", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Toast toast = Toast.makeText(getActivity(), "Don't worry mate", Toast.LENGTH_LONG);
                        toast.show();
                    }

                })
                .create();
    }

}