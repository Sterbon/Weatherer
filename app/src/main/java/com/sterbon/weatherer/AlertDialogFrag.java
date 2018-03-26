package com.sterbon.weatherer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Utsav on 8/26/2017.
 */

public class AlertDialogFrag extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context mcontext= getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext)
                                      .setTitle(R.string.error_title)
                                      .setMessage(R.string.error_msg)
                                      .setPositiveButton(R.string.error_resp,null);

        AlertDialog dialog = builder.create();
        return dialog;
    }


}
