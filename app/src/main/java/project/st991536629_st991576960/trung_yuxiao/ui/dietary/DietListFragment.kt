package project.st991536629_st991576960.trung_yuxiao.ui.dietary


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentDietListBinding
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import java.time.LocalDateTime
import java.util.*


class DietListFragment : Fragment(), MenuProvider {

    // for debug
    val TAG = "DietListFragment"

    private var _binding: FragmentDietListBinding? = null
    private val binding get() = _binding!!

    // initialize the ViewModel
    private val dietListViewModel: DietListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietListBinding.inflate(inflater, container, false)

        binding.dietRecyclerView.layoutManager = LinearLayoutManager(context);

        // Create option menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dietListViewModel.dietaries.collect { dietaries ->
                    binding.dietRecyclerView.adapter = DiaryListAdapter(dietaries) { dietaryId ->
                        Log.d(TAG, "Navigate to Dietary Detail Fragment");
                        findNavController().navigate(DietListFragmentDirections.showDietaryDetail(dietaryId));
                    }
                }
            }
        }
    }

    // Inflating option menu
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_list_diary, menu);
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.new_diary -> {
                // Navigate to Fragment_diary_detail
                showNewCrime();
                true
            }
            else -> super.onOptionsItemSelected(menuItem);
        }
    }

    private fun showNewCrime() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newDietary = DietModel(
                id = UUID.randomUUID(),
                dateTime = Date(),
                food = "",
                quantity = "",
            )

            dietListViewModel.addDietary(newDietary);
            findNavController().navigate(DietListFragmentDirections.showDietaryDetail(newDietary.id))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}