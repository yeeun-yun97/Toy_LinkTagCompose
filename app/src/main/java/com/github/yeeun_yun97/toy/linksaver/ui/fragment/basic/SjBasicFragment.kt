package com.github.yeeun_yun97.toy.linksaver.ui.fragment.basic

import android.util.Log
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
    // organize code here (not must)
    protected open fun initRecyclerView(){}
    protected open fun setOnClickListeners(){}

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
                chip.setCheckableMode(onCheckedChangeListener,isCheckedOperation(def))
                chipGroup.addView(chip)
                Log.d("태그 그룹","${chip.text} ${isCheckedOperation(def)}")
            }
        }

        // add tags with group
        if (groups != null) {
            for (group in groups) {
                for (tag in group.tags) {
                    val chip = SjTagChip(context!!)
                    chip.setTagValue(TagValue(tag, group.tagGroup.name))
                    chip.setCheckableMode(onCheckedChangeListener,isCheckedOperation(tag))
                    chipGroup.addView(chip)
                    Log.d("태그 그룹","태그 ${chip.text} ${isCheckedOperation(tag)}")
                }
            }
        }
    }


}