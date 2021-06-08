package com.johnmaricool.mario_designs.utils

import com.johnmaricool.mario_designs.utils.models.QuizEntityModel

sealed class Result {

    object isLoading: Result()

    data class isLoaded(val item: List<QuizEntityModel>): Result()
}