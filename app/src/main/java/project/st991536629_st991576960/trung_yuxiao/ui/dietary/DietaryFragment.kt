package project.st991536629_st991576960.trung_yuxiao.ui.dietary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentDietaryBinding
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutBinding


class DietaryFragment : Fragment() {

    private var _binding: FragmentDietaryBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDietaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}