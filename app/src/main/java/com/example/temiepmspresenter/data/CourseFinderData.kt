package com.example.temiepmspresenter.data

data class QuizAnswer(
    val text: String,
    val courseScores: Map<String, Int>
)

data class QuizQuestion(
    val question: String,
    val emoji: String,
    val answers: List<QuizAnswer>
)

val quizQuestions = listOf(
    QuizQuestion(
        question = "O que mais gostas de fazer nos tempos livres?",
        emoji = "🎯",
        answers = listOf(
            QuizAnswer(
                "Praticar desporto e atividades físicas",
                mapOf("desporto" to 3, "acao-educativa" to 1)
            ),
            QuizAnswer(
                "Jogar videojogos ou programar",
                mapOf("desenvolvimento-software" to 3, "conteudos-interativos" to 2, "informatica-gestao" to 1)
            ),
            QuizAnswer(
                "Criar conteúdos (fotos, vídeos, arte)",
                mapOf("multimedia" to 3, "conteudos-interativos" to 2, "comunicacao" to 2)
            ),
            QuizAnswer(
                "Cozinhar, cuidar de alguém ou decorar",
                mapOf("cozinha" to 2, "cabeleireiro" to 2, "esteticista" to 2, "acao-educativa" to 1)
            )
        )
    ),
    QuizQuestion(
        question = "Qual é a tua disciplina favorita na escola?",
        emoji = "📚",
        answers = listOf(
            QuizAnswer(
                "Educação Física",
                mapOf("desporto" to 3, "acao-educativa" to 1)
            ),
            QuizAnswer(
                "Informática ou Matemática",
                mapOf("desenvolvimento-software" to 2, "informatica-gestao" to 2, "eletronica-automacao" to 2, "sistemas-redes" to 1)
            ),
            QuizAnswer(
                "Artes Visuais ou Línguas",
                mapOf("comunicacao" to 2, "multimedia" to 2, "conteudos-interativos" to 1, "cabeleireiro" to 1)
            ),
            QuizAnswer(
                "Ciências ou Biologia",
                mapOf("auxiliar-saude" to 3, "acao-educativa" to 2, "esteticista" to 1)
            )
        )
    ),
    QuizQuestion(
        question = "Como preferes trabalhar?",
        emoji = "💼",
        answers = listOf(
            QuizAnswer(
                "Em equipa, ajudando pessoas diretamente",
                mapOf("acao-educativa" to 2, "auxiliar-saude" to 2, "desporto" to 1, "cozinha" to 1)
            ),
            QuizAnswer(
                "De forma criativa, com ferramentas digitais",
                mapOf("comunicacao" to 3, "multimedia" to 2, "conteudos-interativos" to 2)
            ),
            QuizAnswer(
                "Com as mãos, de forma técnica e prática",
                mapOf("cabeleireiro" to 3, "esteticista" to 3, "eletronica-automacao" to 2, "cozinha" to 2)
            ),
            QuizAnswer(
                "A analisar e resolver problemas técnicos",
                mapOf("eletronica-automacao" to 2, "desenvolvimento-software" to 2, "sistemas-redes" to 2, "informatica-gestao" to 2)
            )
        )
    ),
    QuizQuestion(
        question = "O que mais te motiva no trabalho?",
        emoji = "✨",
        answers = listOf(
            QuizAnswer(
                "Fazer as pessoas sentirem-se bem e confiantes",
                mapOf("cabeleireiro" to 3, "esteticista" to 3, "acao-educativa" to 1, "auxiliar-saude" to 1)
            ),
            QuizAnswer(
                "Criar algo visualmente impactante",
                mapOf("comunicacao" to 3, "multimedia" to 3, "conteudos-interativos" to 2)
            ),
            QuizAnswer(
                "Estar em movimento e ser ativo/a",
                mapOf("desporto" to 3, "auxiliar-saude" to 1)
            ),
            QuizAnswer(
                "Preparar experiências gastronómicas únicas",
                mapOf("cozinha" to 3, "acao-educativa" to 1)
            )
        )
    ),
    QuizQuestion(
        question = "Onde te imaginas a trabalhar no futuro?",
        emoji = "🏢",
        answers = listOf(
            QuizAnswer(
                "Num salão de beleza, spa ou clínica",
                mapOf("cabeleireiro" to 3, "esteticista" to 3)
            ),
            QuizAnswer(
                "Num hospital, escola ou centro de saúde",
                mapOf("auxiliar-saude" to 3, "acao-educativa" to 3)
            ),
            QuizAnswer(
                "Numa empresa de tecnologia ou estúdio criativo",
                mapOf(
                    "desenvolvimento-software" to 2,
                    "multimedia" to 2,
                    "comunicacao" to 2,
                    "informatica-gestao" to 1,
                    "eletronica-automacao" to 1,
                    "sistemas-redes" to 1,
                    "conteudos-interativos" to 1
                )
            ),
            QuizAnswer(
                "Num restaurante, ginásio ou clube desportivo",
                mapOf("cozinha" to 3, "desporto" to 3)
            )
        )
    ),
    QuizQuestion(
        question = "Como te descreves melhor?",
        emoji = "🌟",
        answers = listOf(
            QuizAnswer(
                "Comunicativo/a e criativo/a",
                mapOf("comunicacao" to 3, "multimedia" to 2, "conteudos-interativos" to 1)
            ),
            QuizAnswer(
                "Analítico/a e metódico/a",
                mapOf("desenvolvimento-software" to 2, "informatica-gestao" to 2, "eletronica-automacao" to 2, "sistemas-redes" to 2)
            ),
            QuizAnswer(
                "Energético/a e competitivo/a",
                mapOf("desporto" to 3, "auxiliar-saude" to 1)
            ),
            QuizAnswer(
                "Empático/a e prestativo/a",
                mapOf("auxiliar-saude" to 3, "acao-educativa" to 2, "cozinha" to 1)
            )
        )
    )
)

fun calculateRecommendedCourses(
    selectedAnswers: List<Int>,
    courses: List<Course>
): List<Course> {
    val scores = mutableMapOf<String, Int>()
    selectedAnswers.forEachIndexed { qIndex, answerIndex ->
        val question = quizQuestions.getOrNull(qIndex) ?: return@forEachIndexed
        val answer = question.answers.getOrNull(answerIndex) ?: return@forEachIndexed
        answer.courseScores.forEach { (courseId, score) ->
            scores[courseId] = (scores[courseId] ?: 0) + score
        }
    }
    val courseMap = courses.associateBy { it.id }
    return scores.entries
        .sortedByDescending { it.value }
        .mapNotNull { courseMap[it.key] }
        .take(3)
}
