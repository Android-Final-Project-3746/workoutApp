package project.st991536629_st991576960.trung_yuxiao.ui.pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.util.*

class DatePickerFragment : DialogFragment() {

    companion object {
        const val REQUEST_KEY_DATE = "REQUEST_KEY_DATE";
        const val BUNDLE_KEY_DATE = "BUNDLE_KEY_DATE";
    }

    private val args: DatePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance();
        calendar.time = args.dietaryDate;
        val initialYear = calendar.get(Calendar.YEAR);
        val initialMonth = calendar.get(Calendar.MONTH);
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val chosenHour = calendar.get(Calendar.HOUR);
        val chosenMinute = calendar.get(Calendar.MINUTE);

        val dateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->

            val resultDate = GregorianCalendar(year, month, day, chosenHour, chosenMinute).time

            /**
             * sent the result back to the CrimeDetailFragment
             *
             * Once you have the date, it needs to be sent back to CrimeDetailFragment.
             * To pass data between fragments, you need to package your results inside a
             * key-value Bundle.
             */
            setFragmentResult(REQUEST_KEY_DATE, bundleOf(BUNDLE_KEY_DATE to resultDate))
        }

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

}