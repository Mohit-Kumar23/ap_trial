package com.hp.appoindone;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class dialog {
    Activity activity;
    AlertDialog dialog;

    dialog(Activity myactivity){
        activity = myactivity;
    }

    void startloadingdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    void dismissdialog(){
        dialog.dismiss();
    }
}
