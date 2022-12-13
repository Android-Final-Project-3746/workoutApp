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
import java.util.*

class DeleteDietaryDialogFragment: DialogFragment() {

    companion object {
        const val REQUEST_KEY_CONFIRM = "REQUEST_KEY_CONFIRM_DIETARY"
        const val BUNDLE_KEY_CONFIRM = "BUNDLE_KEY_CONFIRM_DIETARY"
    }

    private val args: DeleteDietaryDialogFragmentArgs by navArgs();

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dietaryID = args.dietaryID;

        return AlertDialog.Builder(requireContext())
            .setMessage("Do you want to delete dietary with ID = ${dietaryID}")
            .setPositiveButton(
                R.string.confirm_deletion,
                DialogInterface.OnClickListener { dialog, id ->
                    // PROCEED TO DELETE
                    val result = DietaryDelConfirmation(
                        confirm = true,
                        dietaryId = dietaryID
                    )
                    setFragmentResult(REQUEST_KEY_CONFIRM, bundleOf(BUNDLE_KEY_CONFIRM to result))
                })
            .setNegativeButton(
                R.string.cancel_deletion,
                DialogInterface.OnClickListener { dialog, id ->
                    // USER CANCEL DELETION
                    val result = DietaryDelConfirmation(
                        confirm = false,
                        dietaryId = dietaryID
                    )
                    setFragmentResult(REQUEST_KEY_CONFIRM, bundleOf(BUNDLE_KEY_CONFIRM to result))
                }).create()
    }
}

data class DietaryDelConfirmation (
    val confirm: Boolean,
    val dietaryId: UUID
) : java.io.Serializable