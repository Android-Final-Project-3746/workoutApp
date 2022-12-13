package project.st991536629_st991576960.trung_yuxiao.ui.workout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.ListItemExerciseBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class ExerciseHolder(
    private val binding: ListItemExerciseBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(exercise: Exercise,
             onExerciseClicked: (id: UUID, exerciseType: ExerciseType) -> Unit,
             onExerciseDeleteClicked: (id: UUID, exerciseType: ExerciseType) -> Unit) {

//        binding.exerciseDateTime.text = exercise.dateTime.toString();
        binding.exerciseDateTime.text = extractDateTime(exercise.dateTime)

        if ( exercise.isDone ) {
            binding.completedText.text = "Completed";
            binding.completedText.setTextColor(Color.parseColor("#46c864"))
        } else {
            binding.completedText.text = "Incompleted";
            binding.completedText.setTextColor(Color.parseColor("#ec1a20"))
        }

        if ( exercise is RunningExercise ) {
            binding.exerciseName.text = "Running"
//            binding.completedText.visibility = if (exercise.isDone) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }

            // Display information that related to running exercise and hide
            // information about the pushup exercise
            binding.exerciseRunningDistance.text = "${exercise.distance.toString()} km";
            binding.exercisePushupTime.visibility = View.GONE

            // Set click listener on the ViewHolder and the delete button
            binding.root.setOnClickListener {
                onExerciseClicked(exercise.id, ExerciseType.RUNNING);
            }

            binding.exerciseDeleteButton.setOnClickListener {
                onExerciseDeleteClicked(exercise.id, ExerciseType.RUNNING);
            }

            binding.exerciseTypeImage.setImageResource(R.drawable.running)

        } else if ( exercise is PushUpExercise ){
            binding.exerciseName.text = "Push-up"
//            binding.completedText.visibility = if (exercise.isDone) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
//            binding.doneCheckmark.visibility = View.VISIBLE


            // Display information that are related to pushup exercise
            // hide any information that are only specific for the running exercise
            binding.exercisePushupTime.text = "${exercise.times.toString()} times";
            binding.exerciseRunningDistance.visibility = View.GONE

            binding.root.setOnClickListener {
                onExerciseClicked(exercise.id, ExerciseType.PUSHUP);
            }

            binding.exerciseDeleteButton.setOnClickListener {
                onExerciseDeleteClicked(exercise.id, ExerciseType.PUSHUP);
            }

            binding.exerciseTypeImage.setImageResource(R.drawable.push_up);
        }
    }


    private fun extractDateTime(dateTime: Date): String {
        val pattern = "EEE, dd MMMM yyyy hh:mm a"
        val localDateTime = convertDateToLocalDate(dateTime)

        return "${localDateTime.format(DateTimeFormatter.ofPattern(pattern))}"
    }

    private fun convertDateToLocalDate(dateTime: Date): LocalDateTime {
        return Instant.ofEpochMilli(dateTime.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}


class WorkoutListAdapter(
    private val exercises: List<Exercise>,
    private val onExerciseClicked: (id: UUID, exerciseType: ExerciseType) -> Unit,
    private val onExerciseDeleteClicked: (id: UUID, exerciseType: ExerciseType) -> Unit
) : RecyclerView.Adapter<ExerciseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val inflater = LayoutInflater.from(parent.context);
        val binding = ListItemExerciseBinding.inflate(inflater, parent, false);
        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        val exercise = exercises[position];
        holder.bind(exercise, onExerciseClicked, onExerciseDeleteClicked);
    }

    override fun getItemCount(): Int {
        return exercises.size;
    }
}


enum class ExerciseType{
    RUNNING,
    PUSHUP,
}