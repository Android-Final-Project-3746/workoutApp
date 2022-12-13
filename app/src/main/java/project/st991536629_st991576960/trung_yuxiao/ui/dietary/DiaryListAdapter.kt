package project.st991536629_st991576960.trung_yuxiao.ui.dietary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.st991536629_st991576960.trung_yuxiao.databinding.ListItemDiaryBinding
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import project.st991536629_st991576960.trung_yuxiao.utils.DateUtil
import java.util.*


class DietaryHolder(
    private val binding: ListItemDiaryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dietary: DietModel, onDietaryClicked: (dietaryId: UUID) -> Unit, onDeleteClicked: (dietaryId: UUID) -> Unit) {
        binding.diaryFood.text = dietary.food
        binding.diaryQuantity.text = dietary.quantity
        binding.diaryDateTime.text = DateUtil.extractDateTime(dietary.dateTime);

        binding.root.setOnClickListener {
            onDietaryClicked(dietary.id);
        }

        binding.dietaryDeleteButton.setOnClickListener {
            onDeleteClicked(dietary.id);
        }
    }
}


class DiaryListAdapter(
    private val dietaries: List<DietModel>,
    private val onDietaryClicked: (dietaryId: UUID) -> Unit,
    private val onDeleteClicked: (dietaryId: UUID) -> Unit
) : RecyclerView.Adapter<DietaryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietaryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDiaryBinding.inflate(inflater, parent, false)
        return DietaryHolder(binding)
    }

    override fun onBindViewHolder(holder: DietaryHolder, position: Int) {
        val dietary = dietaries[position]
        holder.bind(dietary, onDietaryClicked, onDeleteClicked)
    }

    override fun getItemCount(): Int {
        return dietaries.size;
    }
}