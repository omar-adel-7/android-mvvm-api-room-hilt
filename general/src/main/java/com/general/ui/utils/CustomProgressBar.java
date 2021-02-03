package com.general.ui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.general.R;

import javax.inject.Inject;


public class CustomProgressBar {

    @Inject
    public CustomProgressBar() {

    }


    private Dialog dialog;
    View view;
    TextView txt_message;
    ProgressBar pbar_normal;
    ProgressBar pbar_progress;
    ViewGroup containerWhenNotDialog;
    boolean showInDialog;//if false then hasProgress will be ignored as we will consider it as true
    int dialogThemeResId;

    public void show(AppCompatActivity activity, boolean showInDialog, ViewGroup containerWhenNotDialog, String message, boolean cancelable,
                     DialogInterface.OnCancelListener cancelListener, boolean hasProgress, int dialogLayout, int dialogThemeResId) {
        this.showInDialog = showInDialog;
        this.containerWhenNotDialog = containerWhenNotDialog;
        this.dialogThemeResId = dialogThemeResId;
        if (showInDialog) {
            if (containerWhenNotDialog != null) {
                containerWhenNotDialog.setVisibility(View.GONE);
            }
            dialog = new Dialog(activity, dialogThemeResId);
            if (activity.isDestroyed()) {
                return;
            }
            if (dialogLayout == 0) {
                dialogLayout = R.layout.dg_progress_bar;
            } else {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            LayoutInflater inflator = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(dialogLayout, null);
            txt_message = view.findViewById(R.id.id_txt_message);
            pbar_normal = view.findViewById(R.id.id_pbar_normal);
            pbar_progress = view.findViewById(R.id.id_pbar_progress);

            if (message != null) {
                txt_message.setText(message);
            } else {
                txt_message.setVisibility(View.GONE);
            }
            dialog.setContentView(view);
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(cancelListener);
            if (hasProgress) {
                pbar_progress.setVisibility(View.VISIBLE);
                pbar_normal.setVisibility(View.GONE);
            } else {
                pbar_progress.setVisibility(View.GONE);
                pbar_normal.setVisibility(View.VISIBLE);
            }
            dialog.show();
        } else {
            if (containerWhenNotDialog != null) {
                containerWhenNotDialog.setVisibility(View.VISIBLE);
                txt_message = containerWhenNotDialog.findViewById(R.id.id_txt_message);
                pbar_progress = containerWhenNotDialog.findViewById(R.id.id_pbar_progress);
                if (message != null) {
                    txt_message.setText(message);
                } else {
                    txt_message.setVisibility(View.GONE);
                }
            }
        }

    }


    public void dismissProgress(Activity activity) {
        if (activity.isDestroyed()) {
            return;
        }
        if (showInDialog) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } else {
            if (containerWhenNotDialog != null) {
                containerWhenNotDialog.setVisibility(View.GONE);
            }
        }


    }

    public void setMessage(String message) {
        if (txt_message != null) {
            txt_message.setText(message);
        }
    }

    public void setProgress(int progress) {
        if (pbar_progress != null) {
            pbar_progress.setProgress(progress);
        }
    }

    public void setIndeterminateForProgress(boolean indeterminate) {
        if (pbar_progress != null) {
            pbar_progress.setIndeterminate(indeterminate);
        }
    }
}
