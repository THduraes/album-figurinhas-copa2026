# Arquitetura e pontos de integracao

O aplicativo usa um fluxo unidirecional simples:

```text
Screen -> ViewModel -> CompetitionRepository -> Fake ou Firestore
   ^            |
   +-- UiState -+
```

## Contratos entre as partes

Cada tela recebe somente estado pronto e callbacks. Isso permite trabalhar em paralelo sem acessar o banco diretamente dentro do Compose.

| Responsavel | Arquivo principal | Nao precisa alterar |
|---|---|---|
| Tela inicial | `ui/screens/CompetitionScreen.kt` | ViewModel, repositorio e navegacao |
| Tela da equipe | `ui/screens/TeamScreen.kt` | ViewModel, repositorio e navegacao |
| Jogador/treinador | `ui/screens/PlayerDetailScreen.kt` e `CoachDetailScreen.kt` | ViewModel, repositorio e navegacao |
| Base e integracao | `data/`, `viewmodel/` e `ui/navigation/` | Layout interno das telas |

As assinaturas publicas das telas devem ser preservadas durante o desenvolvimento. Componentes reutilizaveis podem ser adicionados em `ui/components/`.

## Estados obrigatorios

Todos os ViewModels publicam `UiState<T>` com tres possibilidades:

- `Loading`: a tela mostra progresso.
- `Success`: a tela recebe o modelo pronto.
- `Error`: a tela mostra a mensagem e oferece nova tentativa.

## Fluxo de navegacao

As rotas ficam centralizadas em `ui/navigation/Screen.kt`:

```text
competition
team/{teamId}
player/{playerId}
coach/{coachId}
```

Somente IDs trafegam na navegacao. Objetos completos sao carregados pelo ViewModel, o que evita limites de tamanho e dados desatualizados no `Bundle`.

## Dados de desenvolvimento

`FakeCompetitionRepository` e `DemoData` permitem implementar todas as telas sem conta Firebase. `FirebaseRepositoryFactory` seleciona a fonte na inicializacao:

- dados demonstrativos quando nao ha configuracao;
- Firestore Emulator em builds `debug` com `useFirebaseEmulator=true`;
- Firestore real quando `app/google-services.json` esta presente.

As tres fontes implementam `CompetitionRepository`, portanto ViewModels e telas nao conhecem a origem dos dados.
