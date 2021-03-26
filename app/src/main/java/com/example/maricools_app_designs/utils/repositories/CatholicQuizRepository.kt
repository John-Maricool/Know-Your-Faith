package com.example.maricools_app_designs.utils.repositories

import com.example.maricools_app_designs.database.QuizDao
import com.example.maricools_app_designs.utils.models.QuizEntityModel

class CatholicQuizRepository
constructor(var dao: QuizDao){
       fun getQuizByPart(part: String): List<QuizEntityModel>{
          return dao.getQuizWherePart(part)
       }
}