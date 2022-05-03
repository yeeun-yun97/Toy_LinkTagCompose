package com.github.yeeun_yun97.toy.linksaver.ui.activity

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.github.yeeun_yun97.toy.linksaver.databinding.ActivityEditLinkBinding
import com.github.yeeun_yun97.toy.linksaver.ui.activity.basic.SjBasicActivity
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.EditLinkFragment

class EditLinkActivity : SjBasicActivity<ActivityEditLinkBinding>() {

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityEditLinkBinding =
        ActivityEditLinkBinding.inflate(layoutInflater)

    override fun homeFragment(): Fragment {
        val lid = intent.getIntExtra("lid", -1)
        return if (lid == -1) {
            EditLinkFragment()
        } else {
            EditLinkFragment.newInstance(lid)
        }
    }

    override fun onCreate() {}

}