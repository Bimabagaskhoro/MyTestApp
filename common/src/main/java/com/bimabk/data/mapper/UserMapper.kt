package com.bimabk.data.mapper

import com.bimabk.data.response.UserDetailResponse
import com.bimabk.data.response.UsersResponse.UsersResponseItem
import com.bimabk.data.uimodel.UserDetailUiModel
import com.bimabk.data.uimodel.UserUiModel

fun mapToUiModel(
    users: List<UsersResponseItem>,
): List<UserUiModel> {
    return users.map {
        UserUiModel(
            userId = it.id ?: 0,
            username = it.username.orEmpty(),
            name = it.name.orEmpty(),
            email = it.email.orEmpty(),
            phone = it.phone.orEmpty(),
            page = 0
        )
    }
}

fun mapToUiModelMediator(
    listUsers: List<UsersResponseItem>,
    page: Int = 1
): List<UserUiModel> {
    return listUsers.map {
        UserUiModel(
            userId = it.id ?: 0,
            username = it.username.orEmpty(),
            name = it.name.orEmpty(),
            email = it.email.orEmpty(),
            phone = it.phone.orEmpty(),
            page = page
        )
    }
}

fun mapToDetailUiModel(user: UserDetailResponse?): UserDetailUiModel {
    return UserDetailUiModel(
        userId = user?.id ?: 0,
        website = user?.website.orEmpty(),
        phone = user?.phone.orEmpty(),
        name = user?.name.orEmpty(),
        email = user?.email.orEmpty(),
        username = user?.username.orEmpty(),
        companyBs = user?.company?.bs.orEmpty(),
        companyCatchPhrase = user?.company?.catchPhrase.orEmpty(),
        companyName = user?.company?.name.orEmpty(),
        addressZipcode = user?.address?.zipcode.orEmpty(),
        addressGeoLng = user?.address?.geo?.lng.orEmpty(),
        addressGeoLat = user?.address?.geo?.lat.orEmpty(),
        addressSuite = user?.address?.suite.orEmpty(),
        addressCity = user?.address?.city.orEmpty(),
        addressStreet = user?.address?.street.orEmpty()
    )
}
