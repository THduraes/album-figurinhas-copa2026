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
