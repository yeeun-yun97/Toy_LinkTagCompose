package com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.setting

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.yeeun_yun97.toy.linksaver.R
import com.github.yeeun_yun97.toy.linksaver.application.RESULT_FAILED
import com.github.yeeun_yun97.toy.linksaver.application.RESULT_SUCCESS
import com.github.yeeun_yun97.toy.linksaver.data.model.SettingItemValue
import com.github.yeeun_yun97.toy.linksaver.databinding.FragmentSettingBinding
import com.github.yeeun_yun97.toy.linksaver.ui.activity.LockActivity
import com.github.yeeun_yun97.toy.linksaver.ui.adapter.recycler.SettingListAdapter
import com.github.yeeun_yun97.clone.ynmodule.ui.component.YnConfirmBaseDialogFragment
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.basic.SjBasicFragment
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.setting.app_info.AppInfoFragment
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.setting.domain.ListDomainFragment
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.setting.personal.PersonalSettingFragment
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.setting.tag.ListGroupFragment
import com.github.yeeun_yun97.toy.linksaver.viewmodel.setting.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment @Inject constructor() : SjBasicFragment<FragmentSettingBinding>() {
    private val viewModel: SettingViewModel by activityViewModels()

    @Inject lateinit var groupFragment : ListGroupFragment
    @Inject lateinit var appInfoFragment : AppInfoFragment
    @Inject lateinit var personalSettingFragment : PersonalSettingFragment
    @Inject lateinit var domainFragment : ListDomainFragment

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun layoutId(): Int = R.layout.fragment_setting

    override fun onCreateView() {
        binding.settingRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SettingListAdapter()
        binding.settingRecyclerView.adapter = adapter
        adapter.setList(getSettingList())

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_SUCCESS) {
                    moveToPersonalSettingWithOutPassword()
                } else if(it.resultCode == RESULT_FAILED) {
                   val messageDialog =  YnConfirmBaseDialogFragment("??????","??????????????? ???????????????.",null)
                    messageDialog.show(childFragmentManager,"???????????? ??????")
                }
            }
    }

    private fun getSettingList(): List<SettingItemValue> {
        return mutableListOf(
            SettingItemValue("????????? ??????", ::moveToPersonalSetting),
            SettingItemValue("?????? ?????? ??????", ::moveToViewTagGroups),
//            SettingItemValue("????????? ??????", ::moveToViewDomains),
//            SettingItemValue("??????????????????", ::moveToViewPlayLists),
            SettingItemValue("??? ?????? ??????", ::moveToViewData),
        )
    }



    private fun moveToPersonalSettingWithOutPassword() {
        moveToOtherFragment(personalSettingFragment)
    }

    private fun moveToPersonalSetting() {
        lifecycleScope.launch(Dispatchers.IO) {
            val password = viewModel.passwordFlow.first()
            if (password.isEmpty()) {
                moveToPersonalSettingWithOutPassword()
            } else {
                val intent = Intent(activity, LockActivity::class.java)
                activityResultLauncher.launch(intent)
            }
        }

    }

    private fun moveToViewDomains() {
        this.moveToOtherFragment(domainFragment)
    }

    private fun moveToViewTagGroups() {
        this.moveToOtherFragment(groupFragment)
    }

    private fun moveToViewData() {
        this.moveToOtherFragment(appInfoFragment)
    }

    private fun moveToViewPlayLists() {
        Toast.makeText(requireContext(), "?????? ?????????????????? ????????? ???????????????.", Toast.LENGTH_LONG).show()
    }
}