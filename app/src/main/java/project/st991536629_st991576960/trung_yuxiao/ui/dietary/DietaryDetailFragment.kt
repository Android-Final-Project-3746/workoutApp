package project.st991536629_st991576960.trung_yuxiao.ui.dietary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentDietaryDetailBinding
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.DatePickerFragment
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.TimePickerFragment
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DietaryDetailFragment : Fragment() {

    private var _binding: FragmentDietaryDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: DietaryDetailFragmentArgs by navArgs();

    private val dietaryDetailViewModel: DietaryDetailViewModel by viewModels {
        DietaryDetailViewModelFactory(args.dietaryId);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietaryDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Direction of update <From Fragment to ViewModel>
        binding.apply {
            foodName.doOnTextChanged { text, start, before, count ->
                dietaryDetailViewModel.updateDietary { oldValue ->
                    oldValue.copy(food = text.toString())
                }
            }

            quantity.doOnTextChanged { text, start, before, count ->
                dietaryDetailViewModel.updateDietary { oldValue ->
                    oldValue.copy( quantity = text.toString() )
                }
            }

            dietaryEditBtn.setOnClickListener {
                dietaryDetailViewModel.updateToDatabase()
            }
        }

        // Listen for result
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { requestKey, bundle ->
            val newDate = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date;
            dietaryDetailViewModel.updateDietary { oldValue -> oldValue.copy(dateTime = newDate) }
        }

        setFragmentResultListener(
            TimePickerFragment.REQUEST_KEY_TIME
        ) { requestKey, bundle ->
            val newDate = bundle.getSerializable(TimePickerFragment.BUNDLE_KEY_TIME) as Date;
            dietaryDetailViewModel.updateDietary { oldValue -> oldValue.copy(dateTime = newDate) }
        }

        // Listen for change in the ViewModel.dietary
        // <FROM ViewModel to Fragment>
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dietaryDetailViewModel.dietary.collect() { dietary ->
                    dietary?.let { updateUI(it) }
                }
            }
        }
    }

    private fun updateUI(dietary: DietModel) {
        binding.apply {
            if ( this.foodName.text.toString() != dietary.food ) {
                this.foodName.setText(dietary.food);
            }

            if ( this.quantity.text.toString() != dietary.quantity ) {
                this.quantity.setText(dietary.quantity);
            }

            dietaryDatePicker.text = extractDate(dietary.dateTime);

            dietaryTimePicker.text = extractTime(dietary.dateTime);

            dietaryDatePicker.setOnClickListener {
                findNavController().navigate(DietaryDetailFragmentDirections.selectDateDietary(dietary.dateTime))
            }

            dietaryTimePicker.setOnClickListener {
                findNavController().navigate(DietaryDetailFragmentDirections.selectTimeDietary(dietary.dateTime));
            }
        }
    }

    private fun extractDate(dateTime: Date): String {
        val pattern = "EE, dd LL yyyy "

        val localDateTime = convertDateToLocalDate(dateTime)
        val result: String = "${localDateTime.dayOfWeek} - ${localDateTime.month} ${localDateTime.dayOfMonth}, ${localDateTime.year}"

        return result;
    }

    private fun extractTime(dateTime: Date): String {
        val pattern = "hh:mm a";

        val localDateTime = convertDateToLocalDate(dateTime)
        val localTime = localDateTime.toLocalTime();
        val result : String = "${localTime.format(DateTimeFormatter.ofPattern(pattern))}"

        return result;
    }

    private fun convertDateToLocalDate(dateTime: Date): LocalDateTime {
        return Instant.ofEpochMilli(dateTime.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}