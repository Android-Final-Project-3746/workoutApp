package project.st991536629_st991576960.trung_yuxiao.ui.pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.util.*

class TimePickerFragment : DialogFragment() {
    companion object {
        const val REQUEST_KEY_TIME = "REQUEST_KEY_TIME";
        const val BUNDLE_KEY_TIME = "BUNDLE_KEY_TIME";
    }

    private val args: TimePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance();
        calendar.time = args.dietaryTime;
        val initialHour = calendar.get(Calendar.HOUR);
        val initialMinute = calendar.get(Calendar.MINUTE);

        val chosenYear = calendar.get(Calendar.YEAR);
        val chosenMonth = calendar.get(Calendar.MONTH);
        val chosenDate = calendar.get(Calendar.DAY_OF_MONTH);

        val timeListener = TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDate: Int, minute: Int ->

            val resultTime = GregorianCalendar(chosenYear, chosenMonth, chosenDate, hourOfDate, minute).time

            /**
             * sent the result back to the CrimeDetailFragment
             *
             * Once you have the time, it needs to be sent back to CrimeDetailFragment.
             * To pass data between fragments, you need to package your results inside a
             * key-value Bundle.
             */
            setFragmentResult(REQUEST_KEY_TIME, bundleOf(BUNDLE_KEY_TIME to resultTime))
        }

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMinute,
            false
        )
    }
}