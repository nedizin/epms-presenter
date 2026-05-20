package com.example.temiepmspresenter.data

data class ChatMessage(val text: String, val isBot: Boolean)

data class ChatOption(val question: String, val answer: String)

val chatOptions = listOf(
    ChatOption(
        question = "Fala-me sobre a Escola",
        answer = "A EPMS é uma Escola Profissional dedicada à formação de jovens qualificados para o mercado de trabalho e para o ensino superior. 🏫\n\nOferecemos cursos profissionais de Nível 4 do QNQ com dupla certificação — equivalência ao 12.º ano e qualificação profissional reconhecida.\n\nA nossa missão é proporcionar uma formação prática, moderna e centrada no futuro de cada aluno!"
    ),
    ChatOption(
        question = "Quantos cursos existem?",
        answer = "A EPMS oferece 13 cursos profissionais em áreas diversas:\n\n💆 Beleza e Bem-estar\n🏥 Saúde e Educação\n🍳 Cozinha e Restauração\n📢 Comunicação e Marketing\n⚽ Desporto\n💻 Tecnologia e Informática\n🎬 Multimédia e Conteúdos Digitais\n\nToca em qualquer curso no ecrã para saberes mais!"
    ),
    ChatOption(
        question = "Como me inscrevo?",
        answer = "As inscrições estão abertas! 🎉\n\nCandidatas-te através do portal E-Community em epms.pt, ou visitas-nos pessoalmente.\n\nDocumentos necessários:\n• Cartão de Cidadão\n• Certificado de habilitações\n• Formulário de candidatura online\n\nPara mais informações, consulta epms.pt!"
    ),
    ChatOption(
        question = "Posso ir para a universidade?",
        answer = "Sim! 🎓 Os cursos da EPMS conferem dupla certificação:\n\n✅ Equivalência ao 12.º ano de escolaridade\n✅ Qualificação profissional de Nível 4\n✅ Acesso a exames nacionais\n✅ Candidatura ao ensino superior por concurso especial\n\nO nosso objetivo é abrir-te todas as portas para o futuro!"
    )
)
