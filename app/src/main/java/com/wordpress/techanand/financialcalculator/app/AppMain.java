package com.wordpress.techanand.financialcalculator.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anand Rikka on 5/11/2016.
 */
public class AppMain {

    public final static String LOG = "app_log";
    public final static int TRUE = 1;
    public final static int FALSE = 0;

    public static void hideKeyboard(Activity activity, View view){
        InputMethodManager inputMethodManager = (InputMethodManager)activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void debug(String value){
        Log.d(LOG, value);
    }

    public static void info(String value){
        Log.i(LOG, value);
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String message, String buttonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        /*TextView textMessage = new TextView(context);
        textMessage.setText("Fill all fields !");
        textMessage.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(textMessage);*/
        builder.setMessage("Fill all fields !");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder;
    }

    public static void toast(Context context, String message, int toastLength){
        Toast.makeText(context, message, toastLength).show();
    }

    public static String getResource(Context context, int id){
        return context.getResources().getString(id);
    }

}
