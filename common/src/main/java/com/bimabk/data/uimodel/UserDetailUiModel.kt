package com.bimabk.data.uimodel

data class UserDetailUiModel(
    val userId: Int,
    val website: String,
    val phone: String,
    val name: String,
    val email: String,
    val username: String,
    val companyBs: String,
    val companyCatchPhrase: String,
    val companyName: String,
    val addressZipcode: String,
    val addressGeoLng: String,
    val addressGeoLat: String,
    val addressSuite: String,
    val addressCity: String,
    val addressStreet: String
)
