package project.st991536629_st991576960.trung_yuxiao.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentHomeBinding
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutBinding
import project.st991536629_st991576960.trung_yuxiao.ui.home.HomeViewModel


class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = ""

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}