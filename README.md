# ⚽ Álbum de Figurinhas Digital — Copa do Mundo 2026

<p align="center">
  <img src="https://img.shields.io/badge/Android-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" />
  <img src="https://img.shields.io/badge/Arquitetura-MVVM-FF6F00?style=for-the-badge" />
</p>

## 📋 Sobre o Projeto

Aplicativo Android que simula um **álbum de figurinhas digital** para a Copa do Mundo 2026, desenvolvido como atividade avaliativa da disciplina de **Programação para Dispositivos Móveis (FACOM32503)** do curso de Bacharelado em Sistemas de Informação da **Universidade Federal de Uberlândia**.

O app permite aos usuários visualizar informações sobre a competição, equipes participantes, jogadores e treinadores, com navegação intuitiva e integração a uma base de dados remota.

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVVM (Model-View-ViewModel)** recomendado pelo Google:

```
┌─────────────────────────────────────────┐
│         UI (Jetpack Compose)            │
│  CompetitionScreen → TeamScreen →       │
│  PlayerDetailScreen                     │
├─────────────────────────────────────────┤
│            ViewModel                    │
│  CompetitionVM / TeamVM / PlayerVM      │
│  (StateFlow + Coroutines)               │
├─────────────────────────────────────────┤
│            Repository                   │
│  CompetitionRepository                  │
├─────────────────────────────────────────┤
│        Fonte de Dados Remota            │
│  Firebase Firestore / REST API          │
└─────────────────────────────────────────┘
```

---

## 📱 Telas do Aplicativo

| Tela | Descrição |
|---|---|
| 🏆 **CompetitionScreen** | Tela inicial com nome da competição, troféu e lista de equipes em cards |
| ⚽ **TeamScreen** | Detalhes da equipe: escudo, cores oficiais, selos de vitórias, descrição e elenco |
| 👤 **PlayerDetailScreen** | Detalhes do jogador/treinador: foto, nome, posição, número e estatísticas |

---

## 🛠️ Tecnologias

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose
- **Arquitetura:** MVVM
- **Navegação:** Jetpack Navigation (NavHost)
- **Gerenciamento de Estado:** StateFlow + Coroutines
- **Banco de Dados Remoto:** Firebase Firestore
- **Controle de Versão:** Git + GitHub
- **Gestão de Tarefas:** GitHub Projects

---

## 📂 Estrutura do Projeto

```
com.grupo.albumfigurinhas/
├── data/
│   ├── model/          # Entidades (Competition, Team, Player, Coach)
│   ├── remote/         # Fonte de dados remota (Firebase)
│   └── repository/     # Repositório de dados
├── ui/
│   ├── screens/        # Telas (Competition, Team, PlayerDetail)
│   ├── components/     # Componentes reutilizáveis (Cards, Badges)
│   ├── navigation/     # Grafo de navegação (NavHost)
│   └── theme/          # Tema, cores e tipografia
├── viewmodel/          # ViewModels
└── MainActivity.kt
```

---

## 📅 Cronograma

| Fase | Período | Status |
|---|---|---|
| 1. Requisitos + Modelagem | 26/05 → 26/06 | 🔄 Em andamento |
| 2. Desenvolvimento + Testes | 27/06 → 28/07 | ⏳ Pendente |

---

## 📄 Entregas

### Fase 1 — Requisitos e Modelagem (26/06/2026)
- [x] Documento de requisitos funcionais e não funcionais
- [x] Diagrama de arquitetura MVVM
- [x] Diagrama de navegação entre telas
- [x] Diagrama de fluxo de dados
- [x] Modelagem das entidades (data classes)
- [x] Estrutura do banco de dados remoto
- [ ] Protótipos de tela no Figma
- [ ] Apresentação oral

📄 **Documento completo:** [Fase1_Requisitos_Modelagem.md](./docs/Fase1_Requisitos_Modelagem.md)

### Fase 2 — Desenvolvimento e Testes (28/07/2026)
- [ ] Implementação das telas e navegação
- [ ] Integração com base de dados remota
- [ ] Testes funcionais e de usabilidade
- [ ] Versão final do app
- [ ] Apresentação final

---

## 👥 Equipe

| Papel | Responsabilidades |
|---|---|
| **Líder do Projeto** (1) | Coordenação geral, cronograma, integração das entregas |
| **Desenvolvedores Android** (3) | Implementação das telas, navegação, integração com ViewModel |
| **Engenheiros de Dados** (2) | Modelagem das entidades, criação e consumo da API remota |
| **Designers/UX** (2) | Layouts, cores, tipografia, prototipagem no Figma |

---

## 🚀 Como Executar

```bash
# Clone o repositório
git clone https://github.com/SEU_USUARIO/album-figurinhas-copa2026.git

# Abra o projeto no Android Studio
# File → Open → Selecione a pasta do projeto

# Execute no emulador ou dispositivo físico
# Run → Run 'app'
```

---

## 📝 Licença

Projeto acadêmico desenvolvido para a disciplina FACOM32503 — UFU.

---

<p align="center">
  <b>FACOM32503 — Programação para Dispositivos Móveis</b><br>
  Prof. Cláudio C. Rodrigues — UFU 2026/1
</p>
