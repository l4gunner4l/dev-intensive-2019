package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String =
        when(question){
            Question.NAME -> Question.NAME.question
            Question.PROFESSION -> Question.PROFESSION.question
            Question.MATERIAL -> Question.MATERIAL.question
            Question.BDAY -> Question.BDAY.question
            Question.SERIAL -> Question.SERIAL.question
            Question.IDLE -> Question.IDLE.question
        }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>>{
        return if (question.answers.contains(answer)) {
            //status = Status.NORMAL
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            if (status == Status.CRITICAL){
                status = status.nextStatus()
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }

        }
    }

    enum class Status(val color: Triple<Int, Int, Int>){
        NORMAL(Triple(255, 255, 255)), // white color
        WARNING(Triple(255, 120, 0)),  // color
        DANGER(Triple(255, 60, 60)),   // color
        CRITICAL(Triple(255, 0, 0)); // red color

        fun nextStatus():Status{
            return if (this.ordinal < values().lastIndex) values()[this.ordinal + 1]
                 else values()[0]
        }
    }

    enum class Question(val question: String, val answers: List<String>){

        /*Question.NAME -> "Имя должно начинаться с заглавной буквы"

        Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"

        Question.MATERIAL -> "Материал не должен содержать цифр"

        Question.BDAY -> "Год моего рождения должен содержать только цифры"

        Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"

        Question.IDLE -> //игнорировать валидацию*/

        NAME("Как меня зовут?", listOf("бендер", "bender")){
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "iron", "metal", "wood")){
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}