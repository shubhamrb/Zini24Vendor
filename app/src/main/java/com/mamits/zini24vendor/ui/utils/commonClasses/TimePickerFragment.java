package com.mamits.zini24vendor.ui.utils.commonClasses;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    boolean isOpenTime;
    onTimeListener listener;

    public TimePickerFragment(boolean isOpenTime, onTimeListener listener) {
        this.isOpenTime = isOpenTime;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (isOpenTime) {
            listener.onOpeningTimeSelect(hourOfDay, minute);
        } else {
            listener.onClosingTimeSelect(hourOfDay, minute);
        }
    }

    public interface onTimeListener {
        void onOpeningTimeSelect(int hr, int min);

        void onClosingTimeSelect(int hr, int min);
    }
}
