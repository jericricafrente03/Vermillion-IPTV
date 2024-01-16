package com.bittelasia.vermillion.presentation.register.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
