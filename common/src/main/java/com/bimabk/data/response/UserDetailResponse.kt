package com.bimabk.data.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("website") val website: String? = null,
    @SerializedName("address") val address: Address? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("company") val company: Company? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("username") val username: String? = null
) {
    data class Address(
        @SerializedName("zipcode") val zipcode: String? = null,
        @SerializedName("geo") val geo: Geo? = null,
        @SerializedName("suite") val suite: String? = null,
        @SerializedName("city") val city: String? = null,
        @SerializedName("street") val street: String? = null
    ) {
        data class Geo(
            @SerializedName("lng") val lng: String? = null,
            @SerializedName("lat") val lat: String? = null
        )
    }

    data class Company(
        @SerializedName("bs") val bs: String? = null,
        @SerializedName("catchPhrase") val catchPhrase: String? = null,
        @SerializedName("name") val name: String? = null
    )
}
