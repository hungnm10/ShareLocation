package com.huho.android.sharelocation.utils.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SomeDialog extends DialogFragment {

	private String title;
	private String message;
	private String negativeTitle;
	private String positiveTitle;
	private OnClickListener onClickListener;

	public SomeDialog(String aTitle, String aMessage, String aNegativeTitle, String aPositiveTitle, OnClickListener aOnclick){
		this.title = aTitle;
		this.message = aMessage;
		this.negativeTitle = aNegativeTitle;
		this.positiveTitle = aPositiveTitle;
		this.onClickListener = aOnclick;
	}
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
    	alert.setTitle(this.title);
    	alert.setMessage(this.message);
    	alert.setButton(DialogInterface.BUTTON_POSITIVE,
				negativeTitle, onClickListener);
    	alert.setCancelable(true);
    	alert.setButton(DialogInterface.BUTTON_NEGATIVE,
    			positiveTitle, new OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// dismiss dialog here
					}
				});
    	return alert;
    }           
}