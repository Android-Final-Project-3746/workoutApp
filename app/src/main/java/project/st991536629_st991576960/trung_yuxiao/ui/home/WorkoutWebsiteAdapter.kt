package project.st991536629_st991576960.trung_yuxiao.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.st991536629_st991576960.trung_yuxiao.databinding.WorkoutWebsiteItemBinding
import project.st991536629_st991576960.trung_yuxiao.domain.WorkoutWebsiteModel


class WebsiteItemViewHolder(
    private val binding: WorkoutWebsiteItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WorkoutWebsiteModel, onItemClicked: (url: String) -> Unit) {
        binding.websiteImage.setImageResource(item.imageID);
        binding.websiteImage.setBackgroundColor(item.imageBackgroundColor)
        binding.websiteName.text = item.websiteName;
        binding.websiteDescription.text = item.description;

        binding.root.setOnClickListener {
            onItemClicked(item.url);
        }
    }
}


class WorkoutWebsiteAdapter(
    private val items: List<WorkoutWebsiteModel>,
    private val onItemClicked: (url: String) -> Unit
) : RecyclerView.Adapter<WebsiteItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteItemViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val binding = WorkoutWebsiteItemBinding.inflate(inflater, parent, false);
        return WebsiteItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebsiteItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClicked);
    }

    override fun getItemCount(): Int {
        return items.size;
    }
}