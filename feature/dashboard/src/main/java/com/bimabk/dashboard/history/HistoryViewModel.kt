package com.bimabk.dashboard.history

import androidx.lifecycle.ViewModel
import com.bimabk.common.helper.UiState
import com.bimabk.dashboard.model.CardUiModel
import kotlinx.coroutines.flow.callbackFlow

class HistoryViewModel : ViewModel() {
    private val dataDummyCard = listOf(
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.110.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.120.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.130.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.140.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.150.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.160.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.170.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.180.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        )
    )

    private val dataDummyQris = listOf(
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.100.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        ),
        CardUiModel(
            cardName = "Kartu Debit Bri",
            cardNumber = "60123******1234",
            cardTransaction = "Rp.100.000",
            cardDate = "31 Nov 2022",
            cardTime = "23:45:99",
            cardStatus = "Purchase"
        )
    )

    val historyStateCard = callbackFlow {
        send(UiState.Loading)
        try {
            send(UiState.Success(dataDummyCard))
        } catch (e: Throwable) {
            send(UiState.Error(e))
        } finally {
            close()
        }
    }

    val historyStateQris = callbackFlow {
        send(UiState.Loading)
        try {
            send(UiState.Success(dataDummyQris))
        } catch (e: Throwable) {
            send(UiState.Error(e))
        } finally {
            close()
        }
    }
}
