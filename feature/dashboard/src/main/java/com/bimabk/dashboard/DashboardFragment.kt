package com.bimabk.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bimabk.common.helper.UiState
import com.bimabk.dashboard.databinding.FragmentDashboardBinding
import com.bimabk.dashboard.history.CardFragment
import com.bimabk.dashboard.history.QrisFragment
import com.bimabk.dashboard.model.DashboardUiModel
import com.bimabk.dashboard.utils.DashBoardConstant.CARD
import com.bimabk.dashboard.utils.DashBoardConstant.DAFTAR_TRANSACTION
import com.bimabk.dashboard.utils.DashBoardConstant.FILTER
import com.bimabk.dashboard.utils.DashBoardConstant.QRIS
import com.bimabk.dashboard.utils.DashBoardConstant.TRANSACTION
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        observeData()
        setupTabLayout()
    }

    private fun setupHeader() = with(binding) {
        toolbarTitle.text = DAFTAR_TRANSACTION
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.dashboardState.collect { state ->
                when (state) {
                    is UiState.Init -> {}
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        setupCardInfo(state.data)
                    }

                    is UiState.Error -> {}
                }
            }
        }
    }

    private fun setupCardInfo(data: DashboardUiModel) = with(binding) {
        tvName.text = data.cardName
        tvPrice.text = data.cardBalance
        tvHistory.text = data.cardTransaction
        tvEdc.text = data.edcNumber
        tvChangeOutlet.text = data.cardOutlet
        tvHistoryTools.text = TRANSACTION
        tvFilter.text = FILTER
    }

    private fun setupTabLayout() = with(binding) {
        tabLayout.addTab(tabLayout.newTab().setText(CARD))
        tabLayout.addTab(tabLayout.newTab().setText(QRIS))

        context?.let {
            (tabLayout.getChildAt(0) as ViewGroup).getChildAt(0).background =
                ContextCompat.getDrawable(it, R.drawable.bg_tab_selected)
            (tabLayout.getChildAt(0) as ViewGroup).getChildAt(1).background =
                ContextCompat.getDrawable(it, R.drawable.bg_tab_unselected)
        }

        initFragmentCard()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    initFragmentCard()
                } else {
                    initFragmentQris()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initFragmentCard() = with(binding) {
        childFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, CardFragment())
            .commit()
    }

    private fun initFragmentQris() = with(binding) {
        childFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, QrisFragment())
            .commit()
    }
}
