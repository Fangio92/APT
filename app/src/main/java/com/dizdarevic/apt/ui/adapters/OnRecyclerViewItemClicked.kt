package com.dizdarevic.apt.ui.adapters

import com.dizdarevic.apt.models.User

interface OnRecyclerViewItemClicked {
    fun onItemClick(user: User)
}