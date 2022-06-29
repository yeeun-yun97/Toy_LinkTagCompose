package com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.search

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.yeeun_yun97.clone.ynmodule.ui.component.DataState
import com.github.yeeun_yun97.clone.ynmodule.ui.component.ViewVisibilityUtil
import com.github.yeeun_yun97.toy.linksaver.R
import com.github.yeeun_yun97.toy.linksaver.data.model.SjLinksAndDomainsWithTags
import com.github.yeeun_yun97.toy.linksaver.databinding.FragmentListLinkBinding
import com.github.yeeun_yun97.toy.linksaver.ui.activity.EditLinkActivity
import com.github.yeeun_yun97.toy.linksaver.ui.adapter.recycler.LinkSearchListAdapter
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.basic.SjBasicFragment
import com.github.yeeun_yun97.toy.linksaver.ui.fragment.main.search.detail_link.DetailLinkFragment
import com.github.yeeun_yun97.toy.linksaver.viewmodel.SettingViewModel
import com.github.yeeun_yun97.toy.linksaver.viewmodel.detail_link.DetailLinkViewModel
import com.github.yeeun_yun97.toy.linksaver.viewmodel.search.SearchLinkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

// FIXME 바텀 내비를 클릭하여 들어갈 시, 이미지가 사라지는 오류가 있음
@AndroidEntryPoint
class ListLinkFragment @Inject constructor() : SjBasicFragment<FragmentListLinkBinding>() {
    private val searchViewModel: SearchLinkViewModel by activityViewModels()
    private val settingViewModel: SettingViewModel by activityViewModels()

    // fragments
    private val detailFragment = DetailLinkFragment()
    private val detailViewModel: DetailLinkViewModel by activityViewModels()
    private val searchFragment = SearchFragment()

    // control view visibility
    private lateinit var viewUtil: ViewVisibilityUtil

    // for recyclerView
    private lateinit var adapter: LinkSearchListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun layoutId(): Int = R.layout.fragment_list_link

    override fun onStart() {
        super.onStart()
        viewUtil.state = DataState.LOADING
        searchViewModel.isPrivateMode=settingViewModel.isPrivateMode.value ?: false
        searchViewModel.refreshData()
    }

    override fun onCreateView() {
        // set binding variable
        binding.viewModel = searchViewModel

        settingViewModel.isPrivateMode.observe(viewLifecycleOwner){
            searchViewModel.isPrivateMode = it
        }

        // set recycler view
        initRecyclerView()

        // set view Util
        viewUtil = ViewVisibilityUtil(
            loadingView = binding.shimmer,
            loadedView = binding.recyclerView,
            emptyView = binding.emptyGroup
        )

        // handle user click event
        setOnClickListeners()

        // set adapter list
        searchViewModel.links.observe(viewLifecycleOwner) {
            if (it != null) setAdapterList(it)
        }
    }

    private fun setAdapterList(list: List<SjLinksAndDomainsWithTags>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.setList(list)
            delay(500)
            if (list.isEmpty()) {
                viewUtil.state = DataState.EMPTY
            } else {
                viewUtil.state = DataState.LOADED
            }
        }
    }

    override fun initRecyclerView() {
        this.adapter = LinkSearchListAdapter(detailOperation = ::moveToDetailFragment)
        this.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = this.adapter
        binding.recyclerView.layoutManager = this.layoutManager
    }


    // functions for user event
    override fun setOnClickListeners() {
        binding.floatingActionView.setOnClickListener { startEditActivity() }
        binding.searchEditText.setOnClickListener { moveToSearchFragment() }
        binding.cancelSearchSetImageView.setOnClickListener { searchViewModel.clearSearchSet() }
    }

    private fun moveToSearchFragment() {
        moveToOtherFragment(searchFragment)
    }

    private fun moveToDetailFragment(lid: Int) {
        detailViewModel.lid = lid
        moveToOtherFragment(detailFragment)
    }

    private fun startEditActivity() {
        val intent = Intent(requireContext(), EditLinkActivity::class.java)
        startActivity(intent)
    }


}