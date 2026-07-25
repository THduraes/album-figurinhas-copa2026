# Fluxo de trabalho do grupo

1. Atualize a branch `main` antes de iniciar.
2. Crie uma branch curta por tarefa, por exemplo `feature/tela-equipe`.
3. Evite alterar contratos de `data/`, `viewmodel/` e `ui/navigation/` sem combinar com o responsavel pela integracao.
4. Execute `./gradlew test` antes de abrir o pull request.
5. No pull request, informe a tela alterada, como testar e inclua capturas em tamanhos de tela diferentes.

## Divisao sugerida

| Branch | Escopo |
|---|---|
| `feature/tela-inicial` | `CompetitionScreen.kt` e componentes usados por ela |
| `feature/tela-equipe` | `TeamScreen.kt` e componentes usados por ela |
| `feature/tela-pessoa` | telas de jogador e treinador |
| `feature/firebase` | dados remotos, regras e validacao dos modelos |
| `feature/integracao` | navegacao, resolucao de conflitos e testes de fluxo |

Nao envie `local.properties` nem `app/google-services.json`; os dois arquivos estao ignorados pelo Git.
