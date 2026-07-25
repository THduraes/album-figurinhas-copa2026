# Album de Figurinhas Digital - Copa do Mundo 2026

Aplicativo Android da disciplina FACOM32503 que apresenta competicao, equipes, jogadores e treinadores com Jetpack Compose, MVVM, Navigation Compose e uma base remota no Firebase Firestore.

## Estado atual

A base funcional da Fase 2 inclui:

- projeto Android e Gradle configurado;
- modelos `Competition`, `Team`, `Player`, `PlayerStats` e `Coach`;
- repositorio substituivel com dados demonstrativos, Firestore Emulator e Firebase real;
- ViewModels com `StateFlow` e estados de carregamento, sucesso e erro;
- navegacao `Competition -> Team -> Player/Coach`;
- telas simples para validar o fluxo enquanto os layouts finais sao desenvolvidos;
- testes unitarios de repositorio e ViewModel e teste de interface inicial.

## Como executar

1. Abra esta pasta no Android Studio.
2. Aguarde a sincronizacao do Gradle.
3. Escolha um emulador ou dispositivo Android 6.0 (API 23) ou superior.
4. Execute a configuracao `app`.

Pelo terminal no Windows:

```powershell
./gradlew.bat test
./gradlew.bat assembleDebug
```

Sem `google-services.json`, o aplicativo usa `DemoData` automaticamente. O fluxo completo do emulador, da carga inicial e da conexao real esta em [Configuracao do Firebase](docs/FIREBASE_SETUP.md).

## Documentacao

- [Arquitetura e divisao das telas](docs/ARCHITECTURE.md)
- [Configuracao do Firebase](docs/FIREBASE_SETUP.md)
- [Guia de configuracao e trabalho do grupo](docs/GUIA_DO_GRUPO.md)
- [Fluxo de contribuicao](CONTRIBUTING.md)
- [Requisitos e modelagem da Fase 1](Fase1_Requisitos_Modelagem.md)

## Estrutura

```text
app/src/main/java/com/grupo/albumfigurinhas/
|-- data/
|   |-- model/
|   |-- remote/
|   `-- repository/
|-- ui/
|   |-- components/
|   |-- navigation/
|   |-- screens/
|   |-- state/
|   `-- theme/
|-- viewmodel/
|-- AlbumApplication.kt
`-- MainActivity.kt
```

Projeto academico desenvolvido pela equipe do grupo 5 da UFU.
