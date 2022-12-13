package project.st991536629_st991576960.trung_yuxiao.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentHomeBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.utils.DateUtil
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.NoSuchElementException

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // initialize viewModel
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = ""

        binding.workoutWebsitesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        val websites = homeViewModel.workoutWebsites;
        val adapter = WorkoutWebsiteAdapter(websites) { url ->
            findNavController().navigate(HomeFragmentDirections.openInWebview(url))
        };
        binding.workoutWebsitesRecyclerView.adapter = adapter;
        // Having the snap animation like in Google Play Store
        val linearSnapHelper: LinearSnapHelper = LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(binding.workoutWebsitesRecyclerView)

        // setup RecyclerView for Today Exercises
        binding.nextExercisesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(binding.nextExercisesRecyclerView);

        // setup pieChart
        setUpPieChartRunning();
        setUpPieChartPushUp()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.exercises.transform<List<Exercise>, List<Exercise>> { value ->
                    if (value.size == 0) {
                        Log.d(TAG, "There is no exercise for today")
                        binding.noExerciseTodayBanner.visibility = View.VISIBLE
                    } else {
                        Log.d(TAG, "There is some exercise for today")
                        binding.noExerciseTodayBanner.visibility = View.GONE
                        val result = value.filter { it -> DateUtil.compareCurrentDateToDate(it.dateTime) }.sortedBy { it.dateTime }
                        Log.d(TAG, result.toString())
                        emit(result)
                    }
                }.collect { exercises ->
                    binding.nextExercisesRecyclerView.adapter = NextExercisesAdapter(exercises) { exerciseID, exerciseType ->
                        findNavController().navigate(HomeFragmentDirections.showExerciseDetails(exerciseID, exerciseType, true))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.runningExercises.collect { exercises ->
                    updateRunningPieChart(exercises);
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.pushUpExercises.collect { exercises ->
                    updatePushUpPieChart(exercises);
                }
            }
        }
    }

    private fun updateRunningPieChart(exercises: List<RunningExercise>) {

        if (exercises.isEmpty()) {
            binding.pieChartRunningGroup.visibility = View.GONE;
            return;
        } else {
            binding.pieChartRunningGroup.visibility = View.VISIBLE;
        }

        var completedDistance: Double = 0.0;
        var totalDistance: Double = 0.0;
        var daysLeft: Long = 0;

        completedDistance = exercises.filter { it.isDone }.toList().sumOf { it.distance }
        totalDistance = exercises.sumOf { it.distance }

        try {
            daysLeft = ChronoUnit.DAYS.between(DateUtil.convertDateToLocalDate(Date()), DateUtil.convertDateToLocalDate(exercises.maxBy { it -> it.dateTime }.dateTime))
        } catch (exception: NoSuchElementException) {
            daysLeft = 0;
        }

        Log.d(TAG, "Completed Distance: ${completedDistance}")
        Log.d(TAG, "Total Distance: ${totalDistance}}")

        binding.apply {
            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(completedDistance.toFloat()))
            entries.add(PieEntry((totalDistance - completedDistance).toFloat()))

            // Set Text for center circle
            pieChartRunning.centerText =
                        "Total distance: ${totalDistance} km\n " +
                        "Completed: ${completedDistance} km\n " +
                        "Days left: ${daysLeft + 1} days"
            pieChartRunning.setCenterTextSize(18f)
            pieChartRunning.setCenterTextTypeface(Typeface.DEFAULT_BOLD)

            val dataSet = PieDataSet(entries, "KM");

            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 20f)
            dataSet.selectionShift = 5f

            val colors: ArrayList<Int> = ArrayList()
            colors.add(resources.getColor(R.color.green))
            colors.add(resources.getColor(R.color.gray))

            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.BLACK)
            pieChartRunning.data = data

            pieChartRunning.highlightValues(null)
            pieChartRunning.invalidate()
        }
    }

    private fun setUpPieChartRunning() {
        binding.apply {
            pieChartRunning.setUsePercentValues(true);
            pieChartRunning.description.isEnabled = false;
            pieChartRunning.setExtraOffsets(10f, 10f, 10f, 5f)

            // Set hole
            pieChartRunning.isDrawHoleEnabled = true;
            pieChartRunning.setHoleColor(Color.WHITE);
            pieChartRunning.setHoleRadius(80f)
            pieChartRunning.setTransparentCircleRadius(61f)

            // Set Center Text
            pieChartRunning.setDrawCenterText(true);

            // Disable rotation
            pieChartRunning.isRotationEnabled = false;
        }
    }

    private fun updatePushUpPieChart(exercises: List<PushUpExercise>) {

        if (exercises.isEmpty()) {
            binding.pieChartPushupGroup.visibility = View.GONE;
            return;
        } else {
            binding.pieChartPushupGroup.visibility = View.VISIBLE;
        }

        var completedTimes: Long = 0;
        var totalTimes: Long = 0;
        var daysLeft: Long = 0;

        completedTimes = exercises.filter { it.isDone }.toList().sumOf { it.times }
        totalTimes = exercises.sumOf { it.times }

        try {
            daysLeft = ChronoUnit.DAYS.between(DateUtil.convertDateToLocalDate(Date()), DateUtil.convertDateToLocalDate(exercises.maxBy { it.dateTime }.dateTime))
        } catch (exception: NoSuchElementException) {
            daysLeft = 0;
        }

        Log.d(TAG, "Completed Distance: ${completedTimes}")
        Log.d(TAG, "Total Distance: ${totalTimes}}")

        binding.apply {
            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(completedTimes.toFloat()))
            entries.add(PieEntry((totalTimes - completedTimes).toFloat()))

            // Set Text for center circle
            pieChartPushup.centerText =
                "Total distance: ${totalTimes} times\n " +
                        "Completed: ${completedTimes} times\n " +
                        "Days left: ${daysLeft + 1} days"
            pieChartPushup.setCenterTextSize(18f)
            pieChartPushup.setCenterTextTypeface(Typeface.DEFAULT_BOLD)

            val dataSet = PieDataSet(entries, "Times");

            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 20f)
            dataSet.selectionShift = 5f

            val colors: ArrayList<Int> = ArrayList()
            colors.add(resources.getColor(R.color.yellow))
            colors.add(resources.getColor(R.color.gray))

            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.BLACK)
            pieChartPushup.data = data

            pieChartPushup.highlightValues(null)
            pieChartPushup.invalidate()
        }
    }

    private fun setUpPieChartPushUp() {
        binding.apply {
            pieChartPushup.setUsePercentValues(true);
            pieChartPushup.description.isEnabled = false;
            pieChartPushup.setExtraOffsets(10f, 10f, 10f, 5f)


            // Set hole
            pieChartPushup.isDrawHoleEnabled = true;
            pieChartPushup.setHoleColor(Color.WHITE);
            pieChartPushup.setHoleRadius(80f)
            pieChartPushup.setTransparentCircleRadius(61f)

            // Set Center Text
            pieChartPushup.setDrawCenterText(true);

            // Disable rotation
            pieChartPushup.isRotationEnabled = false;
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}