package com.bittelasia.vermillion.domain.model.license.item

import com.google.gson.annotations.SerializedName

data class LicenseKey(
    @SerializedName("api_key")
    val apiKey: String
)