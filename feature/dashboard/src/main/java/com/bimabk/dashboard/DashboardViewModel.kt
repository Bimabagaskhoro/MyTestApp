package com.bimabk.dashboard

import androidx.lifecycle.ViewModel
import com.bimabk.common.helper.UiState
import com.bimabk.dashboard.model.DashboardUiModel
import kotlinx.coroutines.flow.callbackFlow

class DashboardViewModel : ViewModel() {

    private val dummyDataDashboardUiModel = DashboardUiModel(
        cardName = "Bakso Mat solar cabang 1",
        cardBalance = "Rp. 1.000.000.000",
        cardTransaction = "1200",
        edcNumber = "EDC 12345678",
        cardOutlet = "Ubah Outlet"

    )

    val dashboardState = callbackFlow {
        send(UiState.Loading)
        try {
            send(UiState.Success(dummyDataDashboardUiModel))
        } catch (e: Throwable) {
            send(UiState.Error(e))
        } finally {
            close()
        }
    }
}
