package project.st991536629_st991576960.trung_yuxiao.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType
import java.util.*

class DeleteConfirmationDialogFragment : DialogFragment() {

    companion object {
        const val REQUEST_KEY_CONFIRM = "REQUEST_KEY_CONFIRM"
        const val BUNDLE_KEY_CONFIRM = "BUNDLE_KEY_CONFIRM"
    }

    private val args: DeleteConfirmationDialogFragmentArgs by navArgs();

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val exerciseID = args.exerciseID;
        val exerciseType = args.exerciseType;

        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.dialog_delete_confirmation)
            .setPositiveButton(R.string.confirm_deletion,
                    DialogInterface.OnClickListener { dialog, id ->
                        // PROCEED TO DELETE
                        val result = Confirmation(
                            confirm = true,
                            exerciseID = exerciseID,
                            exerciseType = exerciseType
                        )
                        setFragmentResult(REQUEST_KEY_CONFIRM, bundleOf(BUNDLE_KEY_CONFIRM to result))
                    })
            .setNegativeButton(R.string.cancel_deletion,
                    DialogInterface.OnClickListener { dialog, id ->
                        // USER CANCEL DELETION
                        val result = Confirmation(
                            confirm = false,
                            exerciseID = exerciseID,
                            exerciseType = exerciseType
                        )
                        setFragmentResult(REQUEST_KEY_CONFIRM, bundleOf(BUNDLE_KEY_CONFIRM to result))
                    }).create()
    }
}

data class Confirmation (
    val confirm: Boolean,
    val exerciseID: UUID,
    val exerciseType: ExerciseType
) : java.io.Serializable

