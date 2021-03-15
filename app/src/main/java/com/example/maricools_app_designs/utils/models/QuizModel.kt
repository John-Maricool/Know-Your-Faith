package com.example.maricools_app_designs.utils.models

 data class QuizModel(var question: String, var firstOption: String, var secondOption: String, var thirdOption: String, var correctOption: String, var timer: Long){
     constructor() : this("", "", "", "", "", 20)
}