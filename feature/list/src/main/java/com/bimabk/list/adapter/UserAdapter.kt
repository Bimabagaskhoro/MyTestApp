package com.bimabk.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bimabk.common.helper.Constant.AT
import com.bimabk.data.uimodel.UserUiModel
import com.bimabk.list.databinding.ItemUserBinding

class UserAdapter(
    private val onItemClick: (UserUiModel) -> Unit = {}
) : PagingDataAdapter<UserUiModel, UserAdapter.UserViewHolder>(
    USER_COMPARATOR
) {

    private val usersList = mutableListOf<UserUiModel>()
    private var useManualList = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (useManualList) {
            holder.bind(usersList[position])
        } else {
            val user = getItem(position)
            if (user != null) {
                holder.bind(user)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (useManualList) usersList.size else super.getItemCount()
    }

    fun submitManualList(users: List<UserUiModel>) {
        useManualList = true
        usersList.clear()
        usersList.addAll(users)
        notifyDataSetChanged()
    }

    fun addUsers(newUsers: List<UserUiModel>) {
        useManualList = true
        val startPosition = usersList.size
        usersList.addAll(newUsers)
        notifyItemRangeInserted(startPosition, newUsers.size)
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserUiModel) {
            with(binding) {
                tvName.text = user.name
                tvUsername.text = AT.plus(user.username)
                tvEmail.text = user.email
                tvPhone.text = user.phone

                root.setOnClickListener {
                    onItemClick(user)
                }
            }
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserUiModel>() {
            override fun areItemsTheSame(oldItem: UserUiModel, newItem: UserUiModel): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(oldItem: UserUiModel, newItem: UserUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}