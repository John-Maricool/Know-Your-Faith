package com.johnmaricool.mario_designs.utils

import com.johnmaricool.mario_designs.interfaces_kids.ModelMapper
import com.johnmaricool.mario_designs.utils.models.QuizEntityModel
import com.johnmaricool.mario_designs.utils.models.QuizModel
import javax.inject.Inject

class QuizMapper
@Inject
constructor(): ModelMapper<QuizEntityModel, QuizModel> {
    override fun mapFromModel(model: QuizModel): QuizEntityModel {
        return QuizEntityModel(
                question = model.question,
                firstOption = model.firstOption,
                secondOption = model.secondOption,
                correctOption = model.correctOption,
                timer = model.timer,
                thirdOption = model.thirdOption,
                quiz = model.quiz
        )
    }

    override fun mapToModel(model: QuizEntityModel): QuizModel {
        return QuizModel(model.question, model.quiz, model.firstOption, model.secondOption, model.thirdOption, model.correctOption, model.timer)
    }

    fun convertToCacheList(modelList: List<QuizModel>): List<QuizEntityModel>{
        return modelList.map { mapFromModel(it) }
    }



}