# Guia de configuracao e trabalho do grupo

Este documento explica como cada integrante deve obter o projeto, configurar o
ambiente, acessar o Firebase e entregar sua parte sem compartilhar credenciais.

## Acessos necessarios

GitHub e Firebase sao acessos separados. Cada integrante deve usar sua propria
conta; ninguem deve receber a senha da conta Google ou GitHub de outro membro.

### GitHub

O responsavel pelo repositorio deve adicionar os usuarios em:

`GitHub > Settings > Collaborators > Add people`

Isso permite criar branches e enviar alteracoes. Se o repositorio for publico,
qualquer pessoa pode clonar, mas somente colaboradores podem enviar branches
diretamente para ele.

### Firebase

Para apenas executar o aplicativo conectado ao banco real, os integrantes nao
precisam acessar a conta Google do proprietario nem o Console Firebase. O
responsavel pode compartilhar uma copia de `google-services.json` no grupo
privado da equipe.

Quando um integrante tambem precisar visualizar ou administrar o projeto pelo
Console, o proprietario deve adicionar o e-mail Google dessa pessoa em:

`Firebase > Configuracoes do projeto > Usuarios e permissoes > Adicionar membro`

Use o menor acesso necessario:

| Atividade | Acesso sugerido |
|---|---|
| Desenvolver telas usando os dados de demonstracao | Nenhum acesso ao Firebase |
| Executar o app com um `google-services.json` recebido pelo grupo | Nenhum acesso ao Console |
| Baixar a propria copia e consultar o Console | Viewer |
| Alterar dados, regras ou configuracoes | Editor, somente para o responsavel |

O integrante acessa o projeto com sua propria conta Google. Ninguem precisa
entrar na conta pessoal do proprietario.

## Arquivos do Firebase

### `google-services.json`

Este arquivo associa o aplicativo Android ao projeto Firebase e nao concede
acesso administrativo. A equipe adotara uma destas formas para recebe-lo:

1. O responsavel envia o arquivo `google-services.json` no grupo privado; ou
2. o integrante com acesso Viewer baixa sua copia pelo Console Firebase.

Para baixar pelo Console:

1. Acesse o projeto `album-figurinhas-copa2026` no Console Firebase.
2. Abra `Configuracoes do projeto > Geral`.
3. Em `Seus apps`, selecione o Android `com.grupo.albumfigurinhas`.
4. Clique em `Baixar google-services.json`.
5. Coloque o arquivo em `app/google-services.json`.

Todas as copias configuram o mesmo aplicativo Android e o mesmo banco. O arquivo
contem identificadores de configuracao do cliente, mas permanece fora do Git
para que cada ambiente escolha entre demonstracao, emulador e projeto real. Ele
ja esta listado no `.gitignore`.

O arquivo deve ser enviado somente como `google-services.json`, sem ser
renomeado para `google-services (1).json`. Mesmo nao sendo uma credencial
administrativa, nao deve ser publicado em mensagens abertas ou adicionado ao
repositorio.

### Chave administrativa

Arquivos com nomes como `firebase-adminsdk*.json` sao credenciais secretas e
nunca devem ser enviados por GitHub, Drive, e-mail ou mensagem. Eles nao sao
necessarios para compilar, executar ou desenvolver as telas do aplicativo.

Nao confunda os dois arquivos:

| Arquivo | Pode compartilhar no grupo privado? | Vai para o Git? |
|---|---|---|
| `google-services.json` | Sim | Nao |
| `firebase-adminsdk*.json` | Nao | Nunca |

## Preparacao da maquina

Cada integrante precisa de:

- Git;
- Android Studio;
- Android SDK 36.1;
- um emulador ou dispositivo com Android 6.0 (API 23) ou superior;
- Node.js 22 ou superior somente para trabalhar com regras, seed ou emuladores
  do Firebase.

O Android Studio inclui um JDK compativel. O Gradle Wrapper e as versoes das
bibliotecas ja estao no repositorio.

## Clonar e executar

No PowerShell:

```powershell
git clone https://github.com/THduraes/album-figurinhas-copa2026.git
cd album-figurinhas-copa2026
```

Depois:

1. Abra a pasta clonada no Android Studio.
2. Aguarde o Gradle Sync.
3. Aceite a instalacao do SDK solicitado pelo Android Studio.
4. Adicione `app/google-services.json` para usar o Firebase real, se necessario.
5. Selecione um dispositivo e execute a configuracao `app`.

Sem `google-services.json`, o aplicativo usa automaticamente os dados locais de
demonstracao. Isso permite desenvolver as telas sem acesso ao Firebase.

Para validar pelo terminal:

```powershell
./gradlew.bat test
./gradlew.bat lintDebug
./gradlew.bat assembleDebug
```

O Gradle baixa automaticamente Compose, Firebase, Navigation, Coroutines e as
demais dependencias declaradas no projeto. Nao se compartilham pastas `.gradle`
ou `build`.

## Ferramentas do Firebase

Somente quem trabalhar com regras, seed ou emulador precisa executar:

```powershell
cd firebase
npm.cmd ci
npm.cmd run test:rules
```

`npm ci` instala as versoes registradas em `package-lock.json`. A pasta
`firebase/node_modules` nao deve entrar no Git.

Para iniciar o Firestore local:

```powershell
npm.cmd run emulator
```

Em outro terminal, carregue os dados locais:

```powershell
cd firebase
npm.cmd run seed:emulator
```

Defina `useFirebaseEmulator=true` no `local.properties` somente quando quiser
usar esse emulador. Para o Firebase real, use `useFirebaseEmulator=false`.

## Como popular o banco e trabalhar com imagens

As regras atuais permitem que o aplicativo leia competicoes, equipes e
jogadores, mas bloqueiam qualquer escrita feita pelo app. Compartilhar
`google-services.json` nao concede permissao para criar, alterar ou excluir
documentos.

Os acessos funcionam assim:

| Acesso do integrante | O que pode fazer |
|---|---|
| Somente `google-services.json` | Executar o app e ler os dados permitidos pelas regras |
| Viewer no projeto Firebase | Consultar o Console, sem alterar os dados |
| Editor no projeto Firebase | Criar e alterar documentos diretamente pelo Console |
| Cloud Datastore User + Service Usage Consumer | Executar o seed com a propria conta Google |

Mesmo que um integrante seja Editor no Console, as escritas feitas pelo
aplicativo Android continuam bloqueadas pelas regras em `firestore.rules`. O
acesso do Console e o acesso do aplicativo sao mecanismos diferentes.

### Fluxo recomendado para adicionar dados

Para manter as alteracoes revisaveis e evitar que o banco fique diferente do
codigo, o grupo deve usar este fluxo:

1. Crie uma branch para a alteracao.
2. Edite `firebase/seed-data.json` com equipes, jogadores, treinadores,
   estatisticas e URLs de imagens.
3. Teste a carga no Firebase Emulator.
4. Envie a alteracao por Pull Request.
5. Apos a revisao, um responsavel autorizado aplica o seed no Firebase real
   usando as credenciais ADC da propria conta Google.

Para testar localmente:

```powershell
cd firebase
npm.cmd ci
npm.cmd run seed:emulator:once
```

Se o emulador nao localizar `java`, adicione temporariamente o Java do Android
Studio ao `PATH`:

```powershell
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
npm.cmd run seed:emulator:once
```

Para publicar no Firebase real, o proprietario adiciona somente os responsaveis
pelos dados no IAM com os papeis **Cloud Datastore User** e **Service Usage
Consumer**. Cada responsavel instala a Google Cloud CLI e autentica a propria
conta:

```powershell
gcloud auth application-default login
gcloud auth application-default set-quota-project album-figurinhas-copa2026
cd firebase
npm.cmd ci
npm.cmd run seed:production -- --project album-figurinhas-copa2026
```

O `google-services.json` nao participa desse comando. O script usa credenciais
ADC e IAM, enquanto o aplicativo Android usa o arquivo de configuracao e as
regras do Firestore. Nao altere `firestore.rules` para `allow write: if true` e
nao compartilhe uma chave `firebase-adminsdk*.json` para contornar o acesso.

Somente execute o seed real depois que a alteracao de `seed-data.json` estiver
revisada e integrada. Duas pessoas executando versoes diferentes podem fazer a
ultima carga sobrescrever campos da anterior.

Um Editor pode alterar documentos manualmente pelo Console, mas essas mudancas
nao ficam registradas no Git. Por isso, a edicao direta deve ser reservada para
correcoes pontuais e tambem reproduzida em `firebase/seed-data.json`.

### Imagens

O Firestore armazena os dados e as URLs das imagens, nao os arquivos binarios.
O modelo atual possui campos como `trophyImage`, `badge` e `photo`.

As opcoes atuais sao:

- hospedar a imagem em um servico externo e salvar sua URL no seed;
- adicionar a imagem ao aplicativo em `app/src/main/res/drawable` quando ela
  fizer parte fixa da interface;
- configurar Firebase Storage futuramente para upload e armazenamento dos
  arquivos, salvando no Firestore apenas a URL gerada.

O Firebase Storage ainda nao esta configurado neste projeto. Adiciona-lo exige
definir regras de acesso, dependencias Android e o fluxo de upload antes de o
grupo enviar imagens por ele.

## Divisao sugerida

| Responsabilidade | Arquivos principais | Branch sugerida |
|---|---|---|
| Tela inicial | `CompetitionScreen.kt` e componentes | `feature/tela-inicial` |
| Tela do time | `TeamScreen.kt` e componentes | `feature/tela-equipe` |
| Jogador e treinador | `PlayerDetailScreen.kt`, `CoachDetailScreen.kt` | `feature/tela-pessoa` |
| Firebase e integracao | `data/remote`, regras, navegacao e testes | `feature/firebase` |

Antes de iniciar uma tarefa:

```powershell
git switch main
git pull origin main
git switch -c feature/nome-da-tarefa
```

Depois de implementar e testar:

```powershell
git add .
git status
git commit -m "Descreve a alteracao realizada"
git push -u origin feature/nome-da-tarefa
```

Abra um Pull Request para `main`. Nao trabalhe diretamente na `main` depois que
o desenvolvimento em grupo comecar.

## Nunca enviar ao Git

Antes de cada commit, confira `git status`. Estes arquivos e diretorios nao
podem ser incluidos:

- `app/google-services.json`;
- qualquer `firebase-adminsdk*.json` ou `service-account*.json`;
- `local.properties`;
- `.idea/`, `.gradle/` e `build/`;
- `firebase/node_modules/`;
- logs do Firebase Emulator.

Em caso de duvida, nao force a inclusao de um arquivo ignorado com `git add -f`.
