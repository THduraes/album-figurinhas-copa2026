# Relatório e roteiro de apresentação

## Álbum de Figurinhas Digital - Copa do Mundo 2026

Este documento organiza o que foi desenvolvido nas etapas de implementação,
integração de dados e testes/usabilidade. Além de registrar as decisões do
projeto, ele serve como roteiro para a apresentação da equipe.

Data da validação técnica: 28/07/2026.

## Visão geral da apresentação

| Etapa | Responsável | Tempo sugerido | Objetivo |
|---|---|---:|---|
| 2. Implementação | Desenvolvedores Android | 4 a 5 minutos | Mostrar o aplicativo e explicar o MVVM |
| 3. Integração de Dados | Engenheiros de Dados | 3 a 4 minutos | Demonstrar a leitura do Firestore |
| 4. Testes e Usabilidade | Designers/UX | 3 a 4 minutos | Apresentar testes, problemas encontrados e feedback |

Antes da apresentação, preencher os nomes:

- Implementação Android: **[nome(s)]**
- Integração de dados: **[nome(s)]**
- Testes e usabilidade: **[nome(s)]**

---

## 2. Implementação - Desenvolvedores Android

### O que foi desenvolvido

O aplicativo foi implementado em Kotlin com Jetpack Compose. A versão atual
possui os seguintes fluxos:

1. Tela de carregamento temática.
2. Tela da competição com as seleções participantes.
3. Tela da equipe com descrição, títulos, jogadores e treinador.
4. Tela de detalhes do jogador com posição, número, estatísticas e nascimento.
5. Tela de detalhes do treinador com estatísticas e perfil.
6. Navegação de retorno entre todas as telas.
7. Carregamento de imagens remotas com fallback para recursos locais.

As rotas são centralizadas e transportam apenas identificadores:

```text
splash
  -> competition
      -> team/{teamId}
          -> team/{teamId}/player/{playerId}
          -> team/{teamId}/coach/{coachId}
```

### Fluxo MVVM adotado

O aplicativo usa MVVM para separar interface, estado e acesso aos dados:

```text
Usuário
  |
  v
Screen (Jetpack Compose)
  | observa StateFlow<UiState<T>>
  v
ViewModel
  | chama operações do contrato
  v
CompetitionRepository
  |                         |
  v                         v
Firestore remoto       DemoData local
```

#### View - telas Compose

As telas exibem o estado recebido e enviam ações por callbacks. Elas não
acessam o Firebase diretamente. Exemplos:

- `CompetitionScreen.kt`
- `TeamScreen.kt`
- `PlayerDetailScreen.kt`
- `CoachDetailScreen.kt`
- `SplashScreen.kt`

#### ViewModel - estado e regras de apresentação

Cada tela possui um ViewModel que executa chamadas assíncronas e publica um
`StateFlow`. A interface trabalha com três estados:

```kotlin
Loading
Success(data)
Error(message)
```

Isso permite mostrar carregamento, conteúdo ou erro com opção de tentar
novamente. Os ViewModels também impedem que a interface dependa da origem dos
dados.

#### Model e Repository - dados da aplicação

Os modelos principais são `Competition`, `Team`, `Player`, `PlayerStats`,
`Coach` e `CoachStats`. O contrato `CompetitionRepository` oferece as operações:

```kotlin
getCompetition()
getTeam(teamId)
getPlayer(playerId)
getCoach(coachId)
```

O mesmo contrato possui duas implementações:

- `FakeCompetitionRepository`: usa dados locais para desenvolvimento.
- `FirestoreCompetitionRepository`: consulta o banco remoto.

### O que mostrar no aplicativo

Durante a apresentação, seguir esta ordem:

1. Abrir o aplicativo e mostrar a tela de loading.
2. Aguardar a tela da Copa do Mundo.
3. Selecionar uma equipe, por exemplo Brasil ou França.
4. Mostrar descrição, títulos e elenco.
5. Abrir um jogador e destacar foto e estatísticas.
6. Voltar e abrir o treinador.
7. Usar o botão voltar para provar que a navegação mantém o fluxo.

### Sugestão de fala

> Desenvolvemos o aplicativo em Kotlin e Jetpack Compose, seguindo MVVM. A
> tela observa o estado publicado pelo ViewModel. O ViewModel solicita os dados
> por uma interface de repositório, portanto a tela não sabe se os dados vieram
> do Firebase ou da base local. Essa separação facilitou o trabalho paralelo e
> permitiu testar as regras sem depender da interface.

### Arquivos que podem ser mostrados

- `ui/navigation/AppNavGraph.kt`: rotas e criação dos ViewModels.
- `ui/state/UiState.kt`: estados de carregamento, sucesso e erro.
- `viewmodel/CompetitionViewModel.kt`: chamada assíncrona e `StateFlow`.
- `data/repository/CompetitionRepository.kt`: contrato entre camadas.

---

## 3. Integração de Dados - Engenheiros de Dados

### Base remota utilizada

Foi utilizado o Cloud Firestore do projeto Firebase
`album-figurinhas-copa2026`. O banco atual contém:

- 1 competição;
- 5 equipes;
- 25 jogadores;
- 5 treinadores armazenados dentro dos documentos das equipes;
- 30 referências HTTPS de fotos, sendo 25 de jogadores e 5 de treinadores.

### Estrutura dos documentos

```text
competitions/copa_mundo_2026
  name
  edition
  trophyImage

competitions/copa_mundo_2026/teams/{teamId}
  name
  victories
  description
  colors
  badge
  coach

competitions/copa_mundo_2026/teams/{teamId}/players/{playerId}
  name
  position
  number
  birthDate
  photo
  stats
```

### Comunicação entre aplicativo e Firestore

Quando o `google-services.json` está presente, a fábrica cria um
`FirestoreCompetitionRepository`. A consulta acontece nesta sequência:

1. Busca o documento `competitions/copa_mundo_2026`.
2. Busca a subcoleção `teams`.
3. Para cada equipe, busca a subcoleção `players`.
4. Converte os mapas do Firestore para os modelos Kotlin.
5. Entrega o resultado ao ViewModel.
6. O ViewModel publica `UiState.Success` para a tela.

As equipes são ordenadas por nome e os jogadores por número. O repositório
mantém cache em memória, enquanto o SDK do Firestore usa cache persistente de
até 100 MB no modo de produção.

### Exemplo de requisição

O SDK Android executa uma operação equivalente a:

```http
GET /v1/projects/album-figurinhas-copa2026/databases/(default)/documents/competitions/copa_mundo_2026
```

Consulta de equipes:

```text
competitions/copa_mundo_2026/teams
```

Consulta dos jogadores do Brasil:

```text
competitions/copa_mundo_2026/teams/brasil/players
```

No código Kotlin, a leitura principal é feita com:

```kotlin
val competitionDocument = competitionRef.get().await()
val teamDocuments = competitionRef.collection("teams").get().await().documents
val players = teamRef.collection("players").get().await().documents
```

### Exemplo simplificado de resposta

Documento de jogador:

```json
{
  "name": "Neymar Jr.",
  "position": "Atacante",
  "number": 10,
  "birthDate": "05/02/1992",
  "photo": "https://raw.githubusercontent.com/.../player_neymar_jr.png",
  "stats": {
    "goals": 79,
    "assists": 55,
    "matches": 128
  }
}
```

O `FirestoreDataMapper` valida campos obrigatórios, converte números para `Int`
e aceita campos opcionais, como estatísticas e data de nascimento.

### Imagens remotas

O Firestore não armazena os arquivos binários. Ele armazena URLs HTTPS para as
imagens publicadas no repositório. O Coil recebe a URL e faz o carregamento na
interface. Os drawables clonados com o projeto são usados como fallback caso a
URL esteja vazia, inválida ou indisponível.

Essa solução garante dois modos:

- online: imagem obtida pela URL registrada no Firestore;
- offline/falha de rede: imagem local incluída no APK.

As URLs atuais dependem de o repositório GitHub permanecer público.

### Segurança

As regras do Firestore permitem leitura pública somente nas coleções do álbum e
bloqueiam escrita pelo aplicativo:

```text
leitura de competitions/teams/players: permitida
escrita pelo cliente Android: bloqueada
coleções desconhecidas: bloqueadas
```

A carga administrativa é realizada por script e credencial fora do
repositório. Chaves administrativas e `google-services.json` não são enviados
ao GitHub.

### O que mostrar na apresentação

1. Abrir o Firebase Console na coleção `competitions`.
2. Entrar em `copa_mundo_2026` e mostrar `teams`.
3. Abrir uma equipe e mostrar o mapa `coach`.
4. Abrir `players` e mostrar nome, estatísticas e URL da foto.
5. Rodar o app e abrir o mesmo jogador.
6. Explicar que a informação vista no Console foi transformada em modelo Kotlin.
7. Mostrar `FirestoreCompetitionRepository.kt` e `FirestoreDataMapper.kt`.

### Sugestão de fala

> A interface não consulta o Firebase diretamente. O repositório remoto busca
> competição, equipes e jogadores, e o mapper converte os documentos em modelos
> Kotlin. O banco permite leitura para o aplicativo, mas bloqueia escrita do
> cliente. Fotos são URLs HTTPS e possuem fallback local para melhorar a
> disponibilidade.

---

## 4. Testes e Usabilidade - Designers/UX

### Resultados técnicos atuais

| Verificação | Resultado em 28/07/2026 | O que valida |
|---|---:|---|
| Testes unitários Android | 8 de 8 aprovados | Mapper, miniaturas, repositório local e ViewModel |
| Regras do Firestore | 5 de 5 aprovadas | Leituras permitidas e escritas/acessos indevidos bloqueados |
| Lint Android | Aprovado | Análise estática do projeto |
| Geração do APK debug | Aprovada | Projeto compilável e instalável |
| URLs remotas de fotos | 30 de 30 responderam | Disponibilidade das imagens HTTPS |
| Documentos após carga | 31 verificados | Competição, 5 equipes e 25 jogadores |
| Instalação em aparelho real | Aprovada | Samsung SM-A156M, Android 16 |
| Teste visual da grade de equipe | Aprovado | Jogadores e treinador com foto |

### Testes automatizados existentes

#### Testes unitários

- Mapeamento de um jogador completo.
- Mapeamento de jogador sem estatísticas opcionais.
- Rejeição de equipe sem treinador.
- Rejeição de campos obrigatórios inválidos.
- Busca de uma equipe existente.
- Erro ao buscar jogador inexistente.
- Carregamento da competição pelo ViewModel.

#### Testes das regras do Firestore

- Permite leitura pública da competição.
- Permite leitura pública de uma equipe.
- Bloqueia escrita feita pelo aplicativo.
- Bloqueia coleções fora do álbum.
- Bloqueia subcoleções desconhecidas.

#### Testes instrumentados de interface

Existem dois casos em `MainActivityTest.kt`:

- a splash deve aparecer quando o app inicia;
- a competição deve aparecer depois da splash.

Na execução manual do runner no Samsung, o teste da splash foi aprovado. O
teste da competição ultrapassou o limite fixo de 5 segundos enquanto aguardava
Firebase e imagens remotas. O fluxo foi validado manualmente no mesmo aparelho,
mas o timeout do teste automatizado deve ser aumentado antes de usar esse caso
como evidência automática definitiva.

### Testes funcionais para demonstrar

| Cenário | Procedimento | Resultado esperado |
|---|---|---|
| Inicialização | Abrir o aplicativo | Splash e depois competição |
| Lista de equipes | Aguardar a leitura remota | Cinco seleções visíveis |
| Detalhe da equipe | Selecionar uma seleção | Descrição, títulos e elenco corretos |
| Detalhe do jogador | Tocar em um jogador | Foto, posição, número e estatísticas |
| Detalhe do treinador | Tocar no treinador | Foto, perfil e estatísticas disponíveis |
| Navegação | Usar voltar nas telas internas | Retorno para a tela anterior |
| Imagem remota | Executar com internet | Foto carregada pela URL HTTPS |
| Fallback | Usar dados locais ou URL ausente | Drawable local exibido |
| Segurança | Tentar escrita nos testes de regras | Operação recusada |

### Feedback já incorporado

Durante a validação interna, a equipe identificou dois pontos:

| Feedback observado | Alteração realizada |
|---|---|
| As fotos desapareciam quando o app usava o Firebase real | URIs locais foram substituídas por URLs HTTPS no Firestore |
| O projeto deveria funcionar após clone em outro computador | Imagens foram mantidas no Git e adicionou-se fallback local |

Esse é feedback interno de desenvolvimento. Para afirmar que houve teste com
usuários, a equipe ainda deve executar uma rodada curta com participantes e
registrar as respostas abaixo.

### Roteiro de teste com usuários

Aplicar com pelo menos 3 participantes que não tenham desenvolvido a tela.
Entregar as tarefas sem explicar onde tocar:

1. Encontre a seleção do Brasil.
2. Abra um jogador específico.
3. Informe a posição e o número desse jogador.
4. Volte e encontre o treinador.
5. Retorne à lista de seleções.

Registrar tempo, conclusão e dificuldade:

| Participante | Tarefas concluídas | Tempo aproximado | Dificuldade encontrada | Comentário |
|---|---:|---:|---|---|
| Usuário 1 | [preencher] | [preencher] | [preencher] | [preencher] |
| Usuário 2 | [preencher] | [preencher] | [preencher] | [preencher] |
| Usuário 3 | [preencher] | [preencher] | [preencher] | [preencher] |

Perguntas ao final:

1. Foi fácil entender como abrir uma equipe?
2. Foi fácil diferenciar jogador e treinador?
3. As informações mais importantes estavam legíveis?
4. O botão de voltar se comportou como esperado?
5. O que você mudaria primeiro?

### Como apresentar o feedback

O responsável por UX deve resumir padrões, não ler todas as respostas. Exemplo
de formato, a ser preenchido somente depois dos testes:

```text
Participantes: [quantidade]
Tarefas concluídas sem ajuda: [resultado]
Maior dificuldade: [resultado]
Ponto mais elogiado: [resultado]
Melhoria priorizada: [resultado]
```

### Sugestão de fala

> Validamos regras de negócio com testes unitários, segurança com o Firebase
> Emulator e execução em aparelho real. Um problema encontrado foi o
> desaparecimento das fotos ao usar o banco remoto; corrigimos usando URLs HTTPS
> com fallback local. Também registramos uma limitação no teste automatizado da
> competição: o timeout de cinco segundos é curto para a leitura remota. A
> avaliação com usuários foi organizada em tarefas de navegação e coleta de
> dificuldade percebida.

---

## Comandos para comprovação

Testes unitários, lint e APK:

```powershell
./gradlew.bat testDebugUnitTest lintDebug assembleDebug
```

Testes das regras do Firestore:

```powershell
cd firebase
npm.cmd run test:rules
```

Testes instrumentados com aparelho ou emulador conectado:

```powershell
./gradlew.bat connectedDebugAndroidTest
```

## Checklist antes da apresentação

- [ ] Definir quem apresenta cada etapa.
- [ ] Garantir internet no aparelho de demonstração.
- [ ] Confirmar que `google-services.json` está em `app/` apenas localmente.
- [ ] Abrir previamente o projeto correto no Firebase Console.
- [ ] Testar competição, uma equipe, um jogador e um treinador.
- [ ] Coletar feedback de pelo menos 3 usuários e preencher a tabela.
- [ ] Aumentar o timeout do teste instrumentado ou explicar a ressalva.
- [ ] Levar o APK debug como alternativa para instalação.
- [ ] Não exibir chaves administrativas durante a apresentação.
