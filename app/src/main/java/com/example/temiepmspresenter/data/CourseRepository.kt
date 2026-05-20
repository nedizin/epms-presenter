package com.example.temiepmspresenter.data

object CourseRepository {
    val courses = listOf(
        Course(
            id = "cabeleireiro",
            name = "Cabeleireiro/a",
            shortDescription = "Corte, coloração, tratamentos capilares e gestão de salão",
            fullDescription = "Curso profissional que prepara profissionais qualificados para a prestação de serviços de cuidado, tratamento e embelezamento capilar para uma clientela diversificada. O programa integra competências técnicas, prática real, normas de higiene e segurança, comunicação com clientes e gestão de serviços, alinhado com o Quadro Nacional de Qualificações.",
            competencies = listOf(
                "Lavagem e preparação capilar",
                "Execução de cortes em diversos estilos e demografias",
                "Técnicas de coloração e descoloração química",
                "Tratamentos e massagens capilares",
                "Criação de penteados (brushing, ondas, caracóis)",
                "Aplicação de postiços e extensões capilares",
                "Atendimento e aconselhamento ao cliente",
                "Ética e gestão profissional em salão"
            ),
            careerPaths = listOf(
                "Cabeleireiro/a em salão (feminino, masculino ou misto)",
                "Proprietário/a de salão ou barbearia",
                "Gestor/a técnico de espaço de beleza",
                "Consultor/a de produtos profissionais de cosmética",
                "Formador/a em cursos profissionais de cabeleireiro",
                "Profissional freelance (moda, cinema, teatro, eventos)"
            ),
            icon = "✂️",
            category = CourseCategory.BEAUTY
        ),
        Course(
            id = "esteticista",
            name = "Esteticista",
            shortDescription = "Tratamentos faciais, corporais e técnicas de estética e bem-estar",
            fullDescription = "Curso profissional que prepara profissionais para planear, executar e avaliar tratamentos estéticos faciais e corporais utilizando técnicas manuais, equipamentos tecnológicos e produtos cosméticos profissionais.",
            competencies = listOf(
                "Diagnóstico estético da pele e do corpo",
                "Tratamentos faciais (limpeza profunda, esfoliação, máscaras, massagens)",
                "Tratamentos corporais (drenagem linfática, anti-celulite, modelagem)",
                "Técnicas de depilação (cera, luz pulsada, laser)",
                "Operação de equipamentos tecnológicos (radiofrequência, ultrassons, LED, microcorrente)",
                "Aplicação de produtos cosméticos profissionais",
                "Massagem terapêutica e de bem-estar",
                "Atendimento e orientação personalizada ao cliente"
            ),
            careerPaths = listOf(
                "Centros e clínicas de estética e beleza",
                "Spas, hotéis e resorts com serviços de bem-estar",
                "Salões de beleza com secção de estética",
                "Clínicas dermatológicas e médico-estéticas",
                "Empresas de cosméticos (demonstrador ou formador de marca)",
                "Prática freelance ou independente",
                "Navios de cruzeiro, termas e centros de saúde e lazer"
            ),
            icon = "💆",
            category = CourseCategory.BEAUTY
        ),
        Course(
            id = "acao-educativa",
            name = "Técnico/a de Ação Educativa",
            shortDescription = "Apoio ao desenvolvimento de crianças e jovens em contextos educativos",
            fullDescription = "Curso profissional que prepara profissionais qualificados para cuidar, apoiar, vigiar e acompanhar crianças e jovens em contextos educativos e de lazer, colaborando com educadores e especialistas no desenvolvimento inclusivo e integral.",
            totalHours = "3.300 horas",
            components = listOf(
                CurriculumComponent(
                    "Componente Sociocultural", 1000,
                    listOf("Português", "Inglês", "Área de Integração", "Educação Física", "Gestão de Projetos e Desenvolvimento Pessoal")
                ),
                CurriculumComponent(
                    "Componente Científica", 500,
                    listOf("Matemática", "Sociologia", "Psicologia")
                ),
                CurriculumComponent(
                    "Componente Técnica", 725,
                    listOf("Saúde Infantil", "Expressão Plástica", "Movimento, Drama e Música", "Técnicas de Intervenção Educativa")
                ),
                CurriculumComponent(
                    "Formação em Contexto de Trabalho", 700,
                    listOf("Estágio em contexto real de educação")
                )
            ),
            competencies = listOf(
                "Técnicas de animação e expressão plástica, dramática, musical e motora",
                "Gestão comportamental e mediação de conflitos",
                "Apoio ao desenvolvimento infantil e juvenil",
                "Trabalho em equipa com educadores e especialistas",
                "Promoção da inclusão e desenvolvimento integral das crianças"
            ),
            careerPaths = listOf(
                "Pré-escolas, escolas e creches",
                "ATL e centros de atividades de tempos livres",
                "Instituições sociais e residências de menores",
                "ONG e empresas de animação sociocultural",
                "Serviços de apoio domiciliário a crianças e jovens",
                "Trabalho freelance em educação e animação"
            ),
            icon = "👩‍🏫",
            category = CourseCategory.HEALTH_EDUCATION
        ),
        Course(
            id = "auxiliar-saude",
            name = "Técnico/a de Auxiliar de Saúde",
            shortDescription = "Apoio a profissionais de saúde em cuidados, higiene e serviços a doentes",
            fullDescription = "Curso profissional que prepara assistentes de saúde qualificados para apoiar profissionais de saúde na prestação de cuidados a doentes, colheita de amostras biológicas, higiene e desinfeção, e apoio logístico e administrativo em unidades de saúde.",
            totalHours = "3.375 horas",
            components = listOf(
                CurriculumComponent(
                    "Componente Sociocultural", 1000,
                    listOf("Português", "Inglês", "Educação Física", "Gestão de Projetos")
                ),
                CurriculumComponent(
                    "Componente Científica", 350,
                    listOf("Matemática", "Física e Química")
                ),
                CurriculumComponent(
                    "Componente Técnica", 1325,
                    listOf("Biologia", "Saúde", "Serviços Gerais de Cuidados e Segurança", "Comunicação Interpessoal", "Higiene e Segurança")
                ),
                CurriculumComponent(
                    "Formação em Contexto de Trabalho", 700,
                    listOf("Estágio em unidade de saúde")
                )
            ),
            competencies = listOf(
                "Auxílio na prestação de cuidados de higiene, conforto e alimentação",
                "Prevenção de infeções e controlo de higiene hospitalar",
                "Manuseamento de amostras biológicas",
                "Cuidados pós-morte e suporte emocional",
                "Suporte logístico e administrativo em unidades de saúde",
                "Prática ética e deontológica nos cuidados de saúde"
            ),
            careerPaths = listOf(
                "Hospitais públicos e privados, centros de saúde",
                "Lares e residências de idosos",
                "Centros de reabilitação e cuidados paliativos",
                "Empresas de cuidados domiciliários",
                "Instituições sociais e de saúde (IPSS, misericórdias)"
            ),
            icon = "🏥",
            category = CourseCategory.HEALTH_EDUCATION
        ),
        Course(
            id = "cozinha",
            name = "Técnico/a de Cozinha e Restauração",
            shortDescription = "Gastronomia portuguesa e internacional e gestão de cozinha",
            fullDescription = "Curso profissional que prepara cozinheiros qualificados para planear, preparar e executar criações culinárias portuguesas e internacionais, gerindo operações de cozinha e serviços de restauração em estabelecimentos hoteleiros e de restauração.",
            competencies = listOf(
                "Planeamento de menus e fichas técnicas de produção",
                "Cozinha portuguesa tradicional e internacional",
                "Segurança alimentar e implementação do sistema HACCP",
                "Gestão de inventário e controlo de custos alimentares",
                "Trabalho em equipa em cozinhas profissionais",
                "Serviço e atendimento ao cliente em restauração",
                "Operação segura de equipamentos de cozinha profissional"
            ),
            careerPaths = listOf(
                "Restaurantes, hotéis e empresas de catering",
                "Empresas de hotelaria e turismo",
                "Cozinhas industriais, padarias e pastelarias",
                "Restauração tradicional ou internacional",
                "Administração pública e catering para eventos",
                "Empreendimentos culinários independentes"
            ),
            icon = "🍳",
            category = CourseCategory.HOSPITALITY
        ),
        Course(
            id = "comunicacao",
            name = "Técnico/a de Comunicação",
            fullName = "Técnico/a de Comunicação - Marketing, Relações Públicas e Publicidade",
            shortDescription = "Marketing, relações públicas, publicidade e comunicação",
            fullDescription = "Curso profissional que prepara especialistas para desenvolver estratégias de marketing e comunicação para organizações, gerir comunicações internas e externas, aplicar técnicas de relações públicas e publicidade, e criar conteúdo gráfico e audiovisual.",
            totalHours = "3.300 horas",
            components = listOf(
                CurriculumComponent(
                    "Componente Sociocultural", 1000,
                    listOf("Português (320h)", "Inglês (220h)", "Área de Integração (220h)", "Educação Física (140h)", "Gestão de Projetos (100h)")
                ),
                CurriculumComponent(
                    "Componente Científica", 500,
                    listOf("Matemática (100h)", "História da Cultura e das Artes (200h)", "Psicologia e Sociologia (200h)")
                ),
                CurriculumComponent(
                    "Componente Técnica", 900,
                    listOf("Marketing (225h)", "Comunicação Publicitária e Criatividade (225h)", "Técnicas e Práticas de Comunicação e RP (200h)", "Comunicação Gráfica e Audiovisual (450h)")
                ),
                CurriculumComponent(
                    "Formação em Contexto de Trabalho", 700,
                    listOf("Estágio em empresa de comunicação ou marketing")
                )
            ),
            competencies = listOf(
                "Desenvolvimento de estratégias de marketing",
                "Análise e investigação de mercado",
                "Design e gestão de campanhas publicitárias",
                "Atividades de relações públicas e assessoria de imprensa",
                "Planeamento e organização de eventos",
                "Criação de conteúdo gráfico, audiovisual e digital",
                "Marketing digital e gestão de redes sociais"
            ),
            careerPaths = listOf(
                "Agências de publicidade, marketing e comunicação",
                "Departamentos de marketing e relações públicas em empresas",
                "Consultoras de marketing e gestão de eventos",
                "Gabinetes de imprensa e assessoria de comunicação",
                "ONG e administração pública",
                "Startups digitais e agências de comunicação online",
                "Criação de conteúdo freelance e gestão de redes sociais"
            ),
            icon = "📢",
            category = CourseCategory.COMMUNICATION
        ),
        Course(
            id = "desporto",
            name = "Técnico/a de Desporto",
            shortDescription = "Planeamento e organização de treinos e atividades desportivas",
            fullDescription = "Curso profissional que prepara profissionais qualificados para planear, organizar e desenvolver treinos desportivos em modalidades individuais e coletivas, além de organizar atividades físicas e de lazer para diferentes públicos.",
            totalHours = "3.400 horas",
            components = listOf(
                CurriculumComponent(
                    "Componente Sociocultural", 980,
                    listOf("Português", "Inglês", "Área de Integração", "Educação Física", "Gestão de Projetos")
                ),
                CurriculumComponent(
                    "Componente Científica", 500,
                    listOf("Matemática", "Estudos do Movimento", "Psicologia")
                ),
                CurriculumComponent(
                    "Componente Técnica", 1000,
                    listOf("Desporto e Treino Desportivo", "Modalidades Coletivas e Individuais", "Atividades de Ginásio", "Atividades de Ar Livre e Aventura")
                ),
                CurriculumComponent(
                    "Formação em Contexto de Trabalho", 700,
                    listOf("Estágio em clube desportivo ou ginásio")
                )
            ),
            competencies = listOf(
                "Planeamento e organização de sessões de treino desportivo",
                "Adaptação de programas de treino a diferentes níveis e públicos",
                "Avaliação e monitorização da condição física",
                "Prevenção de lesões desportivas",
                "Organização e gestão de eventos desportivos",
                "Promoção de estilos de vida saudáveis e inclusão desportiva"
            ),
            careerPaths = listOf(
                "Clubes e associações desportivas",
                "Centros de fitness, ginásios e spas",
                "Serviços municipais de desporto",
                "Escolas e programas educativos desportivos",
                "Trabalho independente como treinador ou animador desportivo",
                "Organizações de gestão e produção de eventos desportivos"
            ),
            icon = "⚽",
            category = CourseCategory.SPORTS
        ),
        Course(
            id = "desenvolvimento-software",
            name = "Técnico/a de Desenvolvimento de Software",
            shortDescription = "Programação, desenvolvimento web, mobile e bases de dados",
            fullDescription = "Curso profissional que prepara profissionais qualificados para a conceção, especificação, desenho, implementação e teste de software em todas as fases do ciclo de desenvolvimento, abrangendo aplicações web, desktop e mobile.",
            competencies = listOf(
                "Conceção e implementação de algoritmos em diversas linguagens de programação",
                "Desenvolvimento de aplicações web, desktop e mobile",
                "Desenho e administração de bases de dados relacionais e não-relacionais",
                "Aplicação de metodologias de desenvolvimento e controlo de versões",
                "Integração de sistemas e produção de documentação técnica"
            ),
            careerPaths = listOf(
                "Empresas de desenvolvimento de software e tecnologia",
                "Departamentos de TI em organizações públicas e privadas",
                "Agências de desenvolvimento web e soluções mobile",
                "Consultoras tecnológicas e auditoria de TI",
                "Suporte técnico e manutenção de aplicações informáticas",
                "Empreendedorismo freelance ou criação de startup tecnológica"
            ),
            icon = "💻",
            category = CourseCategory.TECHNOLOGY
        ),
        Course(
            id = "eletronica-automacao",
            name = "Técnico/a de Eletrónica e Automação",
            shortDescription = "Instalação, manutenção e programação de sistemas eletrónicos industriais",
            fullDescription = "Curso profissional que prepara técnicos qualificados para instalação, configuração, manutenção, reparação e programação de equipamentos eletrónicos e sistemas de automação industrial em diversas indústrias.",
            competencies = listOf(
                "Instalação e teste de equipamentos eletrónicos e sistemas de controlo",
                "Manutenção preventiva e corretiva de circuitos e dispositivos eletrónicos",
                "Programação de PLCs/CLPs, interfaces homem-máquina e sistemas SCADA",
                "Desenho de circuitos eletrónicos com software técnico especializado",
                "Diagnóstico de avarias com instrumentos de medição",
                "Implementação de medidas de segurança em instalações elétricas",
                "Otimização de processos industriais através da automação"
            ),
            careerPaths = listOf(
                "Indústria transformadora, automóvel, metalomecânica, alimentar e farmacêutica",
                "Técnico de manutenção industrial e especialista em automação",
                "Integrador de sistemas de automação",
                "Técnico freelance de eletrónica e automação",
                "Centros de investigação e desenvolvimento tecnológico",
                "Administração pública e serviços de infraestrutura"
            ),
            icon = "⚡",
            category = CourseCategory.TECHNOLOGY
        ),
        Course(
            id = "informatica-gestao",
            name = "Técnico/a de Informática de Gestão",
            shortDescription = "Aplicações informáticas de gestão, sistemas de informação e bases de dados",
            fullDescription = "Curso profissional que forma profissionais qualificados para instalar, configurar e utilizar aplicações informáticas em contextos empresariais, com foco em sistemas de gestão, análise de dados e suporte técnico.",
            totalHours = "3.380 horas",
            components = listOf(
                CurriculumComponent(
                    "Componente Sociocultural", 1000,
                    listOf("Português (320h)", "Inglês (220h)", "Área de Integração (220h)", "Educação Física (140h)", "Gestão de Projetos (100h)")
                ),
                CurriculumComponent(
                    "Componente Científica", 500,
                    listOf("Matemática (300h)", "Economia (200h)")
                ),
                CurriculumComponent(
                    "Componente Técnica", 1180,
                    listOf("Linguagens de Programação (458h)", "Aplicações de Organização e Gestão Empresarial (287h)", "Sistemas de Informação (252h)", "Aplicações Informáticas e Sistemas Operativos (183h)")
                ),
                CurriculumComponent(
                    "Formação em Contexto de Trabalho", 700,
                    listOf("Estágio em empresa ou departamento de TI")
                )
            ),
            competencies = listOf(
                "Instalação e manutenção de sistemas informáticos",
                "Operação de software de gestão empresarial (ERP, faturação, processamento de salários)",
                "Análise de dados para apoio à tomada de decisão",
                "Desenvolvimento de pequenas soluções de software",
                "Gestão e administração de bases de dados",
                "Resolução de problemas técnicos e suporte informático",
                "Aplicação de normas de segurança e proteção de dados"
            ),
            careerPaths = listOf(
                "Departamentos de TI em empresas públicas e privadas",
                "Consultoras de implementação de software de gestão",
                "Empresas de contabilidade e apoio às PME",
                "Centros de serviços partilhados e administração pública",
                "Suporte técnico freelance e consultoria informática"
            ),
            icon = "🖥️",
            category = CourseCategory.TECHNOLOGY
        ),
        Course(
            id = "multimedia",
            name = "Técnico/a de Multimédia",
            shortDescription = "Criação e produção de conteúdos audiovisuais, animação e design digital",
            fullDescription = "Curso profissional que prepara profissionais para conceber, produzir, tratar e integrar conteúdos multimédia em diversas plataformas digitais, respeitando padrões de qualidade, requisitos técnicos e normas legais.",
            totalHours = "3.300 horas",
            components = listOf(
                CurriculumComponent(
                    "Componente Sociocultural", 1000,
                    listOf("Português (320h)", "Inglês (220h)", "Área de Integração (220h)", "Educação Física (140h)", "Gestão de Projetos (100h)")
                ),
                CurriculumComponent(
                    "Componente Científica", 300,
                    listOf("Matemática (200h)", "Física (100h)")
                ),
                CurriculumComponent(
                    "Componente Técnica", 1300,
                    listOf("História da Arte (200h)", "Sistemas de Informação (250h)", "Design Audiovisual (300h)", "Técnicas Multimédia (450h)", "Projeto/Produção Multimédia (100h)")
                ),
                CurriculumComponent(
                    "Formação em Contexto de Trabalho", 700,
                    listOf("Estágio em empresa multimédia ou agência criativa")
                )
            ),
            competencies = listOf(
                "Criação de imagem digital, vetorial e animação",
                "Produção e edição de vídeo e áudio profissional",
                "Desenvolvimento de conteúdo interativo e multimédia",
                "Design de interfaces para web e plataformas mobile",
                "Programação multimédia e scripting",
                "Gestão e manutenção de hardware multimédia",
                "Aplicação de técnicas de marketing digital"
            ),
            careerPaths = listOf(
                "Empresas de produção multimédia e audiovisual",
                "Agências de publicidade e comunicação",
                "Estúdios de design gráfico e criativo",
                "Empresas de desenvolvimento web e digital",
                "Produção audiovisual para televisão, cinema e internet",
                "Criação de conteúdo para redes sociais e plataformas digitais",
                "Instalações interativas, museus e centros culturais"
            ),
            icon = "🎬",
            category = CourseCategory.TECHNOLOGY
        ),
        Course(
            id = "conteudos-interativos",
            name = "Técnico/a de Produção de Conteúdos Interativos",
            shortDescription = "Produção e publicação de conteúdo multimédia interativo para web e mobile",
            fullDescription = "Curso profissional que prepara especialistas em conceber, produzir, editar e publicar conteúdos multimédia interativos, integrando imagem, som, animação, programação básica e design interativo, com respeito pelas normas legais e de direitos de autor.",
            competencies = listOf(
                "Design e conceção de conteúdo interativo e multimédia",
                "Produção e edição de imagens bitmap e vetoriais, áudio e vídeo",
                "Programação básica e implementação de algoritmos",
                "Modelação e gestão de bases de dados",
                "Utilização de técnicas de storytelling e narrativa interativa",
                "Garantia de conformidade legal e de direitos de autor",
                "Teste e publicação de conteúdo em plataformas web e mobile"
            ),
            careerPaths = listOf(
                "Empresas de desenvolvimento multimédia e agências digitais",
                "Empresas de publicidade, marketing e comunicação digital",
                "Startups de EdTech e estúdios de gaming",
                "Departamentos de comunicação corporativa e institucional",
                "Trabalho freelance independente em multimédia e design interativo"
            ),
            icon = "🎮",
            category = CourseCategory.TECHNOLOGY
        ),
        Course(
            id = "sistemas-redes",
            name = "Técnico/a de Sistemas de Computação e Redes",
            shortDescription = "Instalação, configuração e manutenção de sistemas e redes informáticas",
            fullDescription = "Curso profissional que prepara técnicos qualificados para instalar, configurar e manter ferramentas, equipamentos, sistemas e redes informáticas em diferentes plataformas e sistemas operativos, garantindo a otimização e a segurança dos sistemas.",
            competencies = listOf(
                "Instalação, configuração e manutenção de computadores em rede",
                "Configuração de equipamentos de rede, fibra ótica e redes wireless",
                "Configuração de sistemas operativos Windows e Linux",
                "Configuração de protocolos e serviços de rede, administração de servidores web",
                "Gestão, monitorização e manutenção de redes locais",
                "Implementação de segurança da informação e cibersegurança",
                "Programação de scripts e microcontroladores",
                "Resolução de problemas técnicos e suporte a utilizadores"
            ),
            careerPaths = listOf(
                "Empresas de serviços de TI e reparação de equipamentos informáticos",
                "Departamentos de TI e suporte técnico em setores público e privado",
                "Operadores de telecomunicações e fornecedores de serviços de internet",
                "Data centers e infraestrutura de administração pública",
                "Consultoras de TI e integração de sistemas",
                "Startups tecnológicas e trabalho freelance em redes e sistemas"
            ),
            icon = "🌐",
            category = CourseCategory.TECHNOLOGY
        )
    )
}
