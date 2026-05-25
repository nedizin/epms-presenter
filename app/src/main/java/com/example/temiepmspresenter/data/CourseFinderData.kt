package com.example.temiepmspresenter.data

data class DynamicAnswer(
    val text: String,
    val courseScores: Map<String, Int>,
    val nextQuestionKey: String
)

data class DynamicQuestion(
    val key: String,
    val question: String,
    val emoji: String,
    val answers: List<DynamicAnswer>
)

const val RESULTS_KEY = "results"
const val START_QUESTION_KEY = "q_start"
const val ESTIMATED_TOTAL_QUESTIONS = 4

val dynamicQuestions: Map<String, DynamicQuestion> = listOf(

    // ── Q1: broad interest (everyone sees this) ──────────────────────────────
    DynamicQuestion(
        key = "q_start",
        question = "O que mais gostas de fazer nos tempos livres?",
        emoji = "🎯",
        answers = listOf(
            DynamicAnswer(
                "Praticar desporto e atividade física",
                mapOf("desporto" to 2, "auxiliar-saude" to 1),
                "q_sports"
            ),
            DynamicAnswer(
                "Programar, jogar ou explorar tecnologia",
                mapOf("desenvolvimento-software" to 2, "conteudos-interativos" to 1),
                "q_tech"
            ),
            DynamicAnswer(
                "Criar conteúdo, design ou arte digital",
                mapOf("multimedia" to 2, "comunicacao" to 2),
                "q_creative"
            ),
            DynamicAnswer(
                "Cuidar, ajudar ou ensinar outras pessoas",
                mapOf("acao-educativa" to 2, "auxiliar-saude" to 2),
                "q_care"
            ),
            DynamicAnswer(
                "Cozinhar, decorar ou trabalhar com as mãos",
                mapOf("cozinha" to 2, "cabeleireiro" to 1, "esteticista" to 1),
                "q_hands"
            )
        )
    ),

    // ── Branch: Sports ───────────────────────────────────────────────────────
    DynamicQuestion(
        key = "q_sports",
        question = "Que tipo de atividade desportiva preferes?",
        emoji = "⚽",
        answers = listOf(
            DynamicAnswer(
                "Treinar e competir em desportos de equipa",
                mapOf("desporto" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Fitness, ginásio e treino pessoal",
                mapOf("desporto" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Cuidar da saúde e bem-estar físico dos outros",
                mapOf("auxiliar-saude" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Animar e ensinar desporto a crianças e jovens",
                mapOf("acao-educativa" to 2, "desporto" to 2),
                "q_motivation"
            ),
            DynamicAnswer(
                "Organizar e gerir eventos desportivos",
                mapOf("desporto" to 2, "comunicacao" to 1),
                "q_motivation"
            )
        )
    ),

    // ── Branch: Technology ───────────────────────────────────────────────────
    DynamicQuestion(
        key = "q_tech",
        question = "Na tecnologia, o que mais te atrai?",
        emoji = "💻",
        answers = listOf(
            DynamicAnswer(
                "Programar aplicações, jogos ou websites",
                mapOf("desenvolvimento-software" to 3, "conteudos-interativos" to 1),
                "q_motivation"
            ),
            DynamicAnswer(
                "Montar, configurar e manter redes e hardware",
                mapOf("sistemas-redes" to 3, "eletronica-automacao" to 2),
                "q_motivation"
            ),
            DynamicAnswer(
                "Automatizar sistemas e trabalhar com eletrónica",
                mapOf("eletronica-automacao" to 3, "sistemas-redes" to 1),
                "q_motivation"
            ),
            DynamicAnswer(
                "Gerir bases de dados, ERPs e sistemas empresariais",
                mapOf("informatica-gestao" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Criar experiências interativas e conteúdo digital",
                mapOf("conteudos-interativos" to 3, "multimedia" to 1),
                "q_motivation"
            )
        )
    ),

    // ── Branch: Creative ─────────────────────────────────────────────────────
    DynamicQuestion(
        key = "q_creative",
        question = "O que mais gostas de criar?",
        emoji = "🎨",
        answers = listOf(
            DynamicAnswer(
                "Vídeos, filmes e edição audiovisual",
                mapOf("multimedia" to 3, "comunicacao" to 1),
                "q_motivation"
            ),
            DynamicAnswer(
                "Design gráfico, ilustrações e identidade visual",
                mapOf("multimedia" to 2, "comunicacao" to 2),
                "q_motivation"
            ),
            DynamicAnswer(
                "Campanhas de marketing e redes sociais",
                mapOf("comunicacao" to 3, "conteudos-interativos" to 1),
                "q_motivation"
            ),
            DynamicAnswer(
                "Jogos, animações e conteúdo interativo",
                mapOf("conteudos-interativos" to 3, "multimedia" to 2),
                "q_motivation"
            ),
            DynamicAnswer(
                "Eventos, relações públicas e comunicação institucional",
                mapOf("comunicacao" to 3),
                "q_motivation"
            )
        )
    ),

    // ── Branch: Care / People ────────────────────────────────────────────────
    DynamicQuestion(
        key = "q_care",
        question = "Com quem preferes trabalhar e ajudar?",
        emoji = "🤝",
        answers = listOf(
            DynamicAnswer(
                "Crianças e jovens em contexto educativo",
                mapOf("acao-educativa" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Doentes, idosos ou pessoas em recuperação",
                mapOf("auxiliar-saude" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Clientes que querem sentir-se bem e confiantes",
                mapOf("cabeleireiro" to 2, "esteticista" to 2),
                "q_motivation"
            ),
            DynamicAnswer(
                "Atletas e pessoas com objetivos de saúde física",
                mapOf("desporto" to 2, "auxiliar-saude" to 2),
                "q_motivation"
            ),
            DynamicAnswer(
                "Qualquer pessoa que precise de apoio e orientação",
                mapOf("acao-educativa" to 2, "auxiliar-saude" to 2),
                "q_motivation"
            )
        )
    ),

    // ── Branch: Hands-on / Food / Beauty ────────────────────────────────────
    DynamicQuestion(
        key = "q_hands",
        question = "Que tipo de trabalho manual mais te satisfaz?",
        emoji = "🙌",
        answers = listOf(
            DynamicAnswer(
                "Cozinhar e criar pratos criativos",
                mapOf("cozinha" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Cuidar do cabelo e criar penteados únicos",
                mapOf("cabeleireiro" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Tratamentos de beleza e bem-estar da pele",
                mapOf("esteticista" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Montar, reparar e instalar equipamentos",
                mapOf("eletronica-automacao" to 3),
                "q_motivation"
            ),
            DynamicAnswer(
                "Decorar espaços e criar experiências únicas",
                mapOf("cozinha" to 2, "acao-educativa" to 1),
                "q_motivation"
            )
        )
    ),

    // ── Common Q3: Motivation ────────────────────────────────────────────────
    DynamicQuestion(
        key = "q_motivation",
        question = "O que mais te motiva no trabalho?",
        emoji = "✨",
        answers = listOf(
            DynamicAnswer(
                "Ver o impacto direto no bem-estar das pessoas",
                mapOf("auxiliar-saude" to 2, "acao-educativa" to 2, "cabeleireiro" to 1, "esteticista" to 1),
                "q_environment"
            ),
            DynamicAnswer(
                "Criar algo inovador e visualmente impressionante",
                mapOf("multimedia" to 2, "comunicacao" to 2, "conteudos-interativos" to 1),
                "q_environment"
            ),
            DynamicAnswer(
                "Resolver desafios técnicos e complexos",
                mapOf("desenvolvimento-software" to 2, "eletronica-automacao" to 2, "sistemas-redes" to 2),
                "q_environment"
            ),
            DynamicAnswer(
                "Estar em movimento, ativo/a e em competição",
                mapOf("desporto" to 3, "auxiliar-saude" to 1),
                "q_environment"
            ),
            DynamicAnswer(
                "Construir algo do zero e ver o resultado final",
                mapOf("cozinha" to 2, "cabeleireiro" to 1, "desenvolvimento-software" to 1),
                "q_environment"
            )
        )
    ),

    // ── Common Q4: Work environment ──────────────────────────────────────────
    DynamicQuestion(
        key = "q_environment",
        question = "Onde te imaginas a trabalhar daqui a 5 anos?",
        emoji = "🏢",
        answers = listOf(
            DynamicAnswer(
                "Ginásio, clube desportivo ou como treinador/a",
                mapOf("desporto" to 3),
                RESULTS_KEY
            ),
            DynamicAnswer(
                "Empresa de tecnologia, startup ou consultora",
                mapOf("desenvolvimento-software" to 2, "sistemas-redes" to 1, "informatica-gestao" to 1),
                RESULTS_KEY
            ),
            DynamicAnswer(
                "Hospital, escola ou instituição de cuidados",
                mapOf("auxiliar-saude" to 2, "acao-educativa" to 2),
                RESULTS_KEY
            ),
            DynamicAnswer(
                "Salão de beleza, spa ou clínica estética",
                mapOf("cabeleireiro" to 2, "esteticista" to 2),
                RESULTS_KEY
            ),
            DynamicAnswer(
                "Agência criativa, estúdio, restaurante ou freelancer",
                mapOf("multimedia" to 2, "comunicacao" to 2, "cozinha" to 2),
                RESULTS_KEY
            )
        )
    )

).associateBy { it.key }

fun calculateRecommendedCoursesFromScores(
    scores: Map<String, Int>,
    courses: List<Course>
): List<Course> {
    val courseMap = courses.associateBy { it.id }
    return scores.entries
        .sortedByDescending { it.value }
        .mapNotNull { courseMap[it.key] }
        .take(3)
}
