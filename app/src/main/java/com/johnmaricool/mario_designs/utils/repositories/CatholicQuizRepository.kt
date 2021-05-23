package com.johnmaricool.mario_designs.utils.repositories

import com.johnmaricool.mario_designs.database.QuizDao
import com.johnmaricool.mario_designs.utils.models.QuizEntityModel

class CatholicQuizRepository
constructor(var dao: QuizDao){
       fun getQuizByPart(part: String): List<QuizEntityModel>{
          return dao.getQuizWherePart(part)
       }
}