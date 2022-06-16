package com.github.yeeun_yun97.toy.linksaver.ui.fragment.basic

import android.widget.CompoundButton
import androidx.databinding.ViewDataBinding
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.linksaver.R
import com.github.yeeun_yun97.toy.linksaver.data.model.SjTag
import com.github.yeeun_yun97.toy.linksaver.data.model.SjTagGroupWithTags
import com.github.yeeun_yun97.toy.linksaver.ui.component.SjTagChip
import com.github.yeeun_yun97.toy.linksaver.ui.component.TagValue
import com.google.android.material.chip.ChipGroup

abstract class SjBasicFragment<T : ViewDataBinding> : DataBindingBasicFragment<T>() {
    protected val RESULT_SUCCESS = 0
    protected val RESULT_FAILED = 1
    protected val RESULT_CANCELED = 2

    override fun fragmentContainer(): Int = R.id.fragmentContainer

    protected fun setTagsToChipGroupChildren(
        defaultGroup: SjTagGroupWithTags?,
        groups: List<SjTagGroupWithTags>?,
        isCheckedOperation: (SjTag) -> Boolean,
        chipGroup: ChipGroup,
        onCheckedChangeListener: CompoundButton.OnCheckedChangeListener
    ) {
        // clear chipGroup child
        chipGroup.removeAllViews()

        // add tags default group
        if (defaultGroup != null) {
            for (def in defaultGroup.tags) {
                val chip = SjTagChip(context!!)
                chip.setTagValue(TagValue(def))
                chip.isChecked = isCheckedOperation(def)
                chip.setCheckableMode(onCheckedChangeListener)
                chipGroup.addView(chip)
            }
        }

        // add tags with group
        if (groups != null) {
            for (group in groups) {
                for (tag in group.tags) {
                    val chip = SjTagChip(context!!)
                    chip.setTagValue(TagValue(tag, group.tagGroup.name))
                    chip.isChecked = isCheckedOperation(tag)
                    chip.setCheckableMode(onCheckedChangeListener)
                    chipGroup.addView(chip)
                }
            }
        }
    }

}