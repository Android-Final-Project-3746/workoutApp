package project.st991536629_st991576960.trung_yuxiao.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.NextExerciseItemBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType
import project.st991536629_st991576960.trung_yuxiao.utils.DateUtil
import java.util.*

class NextExerciseHolder(
    private val binding: NextExerciseItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(exercise: Exercise, onExerciseClicked: (exerciseID: UUID, exerciseType: ExerciseType) -> Unit) {
        binding.apply {
            exercisePlannedTime.text = "AT " + DateUtil.getTimeStringFromDateTime(exercise.dateTime);
            completedText.visibility = if (exercise.isDone) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if ( exercise is RunningExercise ) {
                exerciseIcon.setImageResource(R.drawable.running_home);
                exerciseRunningDistance.text = "${exercise.distance.toString()} km"
                exercisePushupTimes.visibility = View.GONE;

                root.setOnClickListener {
                    onExerciseClicked(exercise.id, ExerciseType.RUNNING)
                }
            } else if ( exercise is PushUpExercise ) {
                exerciseIcon.setImageResource(R.drawable.pushup_home);
                exercisePushupTimes.text = "${exercise.times.toString()} times"
                exerciseRunningDistance.visibility = View.GONE;

                root.setOnClickListener {
                    onExerciseClicked(exercise.id, ExerciseType.PUSHUP)
                }
            }
        }
    }
}

class NextExercisesAdapter(
    private val exercises: List<Exercise>,
    private val onExerciseClicked: (exerciseID: UUID, exerciseType: ExerciseType) -> Unit
) : RecyclerView.Adapter<NextExerciseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextExerciseHolder {
        val inflater = LayoutInflater.from(parent.context);
        val binding = NextExerciseItemBinding.inflate(inflater, parent, false)
        //binding.root.layoutParams.width = Math.floor(( parent.width * 0.45 )).toInt();
        return NextExerciseHolder(binding);
    }

    override fun onBindViewHolder(holder: NextExerciseHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise, onExerciseClicked)
    }

    override fun getItemCount(): Int {
        return exercises.size;
    }


}