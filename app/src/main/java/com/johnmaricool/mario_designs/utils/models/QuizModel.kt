package com.johnmaricool.mario_designs.utils.models

 data class QuizModel(var question: String, var quiz: String, var firstOption: String, var secondOption: String, var thirdOption: String, var correctOption: String, var timer: Long){
     constructor() : this("","", "", "", "", "", 20)
}