package com.bimabk.dashboard.history.adapter

import android.view.View
import com.bimabk.base.BaseListAdapter
import com.bimabk.dashboard.databinding.ItemCardBinding
import com.bimabk.dashboard.model.CardUiModel

class HistoryAdapter(
) : BaseListAdapter<CardUiModel, ItemCardBinding>
    (ItemCardBinding::inflate) {
    override fun onItemBind(): (CardUiModel, ItemCardBinding, View, Int) -> Unit =
        { item, binding, _, _ ->
            with(binding) {
                tvNameCard.text = item.cardName
                tvNumberCard.text = item.cardNumber
                tvPriceTransaction.text = item.cardTransaction
                tvDate.text = item.cardDate
                tvHour.text = item.cardTime
                tvStatus.text = item.cardStatus
            }
        }
}
