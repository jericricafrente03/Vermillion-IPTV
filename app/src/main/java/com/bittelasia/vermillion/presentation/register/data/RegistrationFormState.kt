package com.bittelasia.vermillion.presentation.register.data

import com.bittelasia.vermillion.data.repository.stbpref.data.STB

data class RegistrationFormState(
    val ip: String ="",
    val ipErr: String? =null,
    val port: String = STB.PORT,
    val portErr: String? =null,
    val room: String ="",
    val roomErr: String? =null
)