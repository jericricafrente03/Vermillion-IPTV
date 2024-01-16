package com.bittelasia.vermillion.presentation.register.validation

class ValidateRoom {
    fun getRoomErrorIdOrNull(input: String) : ValidationResult {
        if (input.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The Room can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}