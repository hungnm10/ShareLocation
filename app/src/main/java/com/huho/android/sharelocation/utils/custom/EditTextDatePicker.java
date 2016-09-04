
package com.huho.android.sharelocation.utils.custom;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sev_user on 5/19/2016.
 */
public class EditTextDatePicker extends EditText implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, View.OnFocusChangeListener, View.OnClickListener {
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog datePickerDialog = null;

    TimePickerDialog mTimePicker = null;

    public EditTextDatePicker(Context context) {
        this(context, null);
    }

    public EditTextDatePicker(Context context, AttributeSet attrs) {
        this(context, attrs,
                Resources.getSystem().getIdentifier("editTextStyle", "attr", "android"));
    }

    public EditTextDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public EditTextDatePicker(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnFocusChangeListener(this);
        setOnClickListener(this);
        setShowSoftInputOnFocus(false);
        datePickerDialog = new DatePickerDialog(context, this, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        mTimePicker = new TimePickerDialog(context, this, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if (mTimePicker != null)
            mTimePicker.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        if (hasFocus) {
            if (!datePickerDialog.isShowing())
                datePickerDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        if (!datePickerDialog.isShowing())
            datePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        String myFormat = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        setText(sdf.format(myCalendar.getTime()));
    }
}
