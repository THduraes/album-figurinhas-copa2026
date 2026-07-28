# Firebase Firestore

O projeto possui tres modos de dados:

| Modo | Quando e usado |
|---|---|
| Demonstracao | Sem configuracao Firebase e com `useFirebaseEmulator=false` |
| Emulator Suite | Build `debug` com `useFirebaseEmulator=true` |
| Firebase real | `app/google-services.json` presente e emulador desligado |

## Teste local com Emulator Suite

O emulador usa o projeto isolado `demo-album-figurinhas-2026`; ele nao acessa recursos reais nem exige credenciais.

Instale as ferramentas uma vez:

```powershell
cd firebase
npm.cmd install
```

Inicie o Firestore e sua interface web:

```powershell
npm.cmd run emulator
```

Em outro terminal, carregue os dados:

```powershell
cd firebase
npm.cmd run seed:emulator
```

Para apenas validar a carga em uma instancia descartavel, use `npm.cmd run seed:emulator:once`; o comando inicia, preenche e encerra o Firestore automaticamente.

Se a CLI informar que nao encontrou `java`, use o Java incluido no Android
Studio durante a sessao atual do PowerShell:

```powershell
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
npm.cmd run seed:emulator:once
```

Defina `useFirebaseEmulator=true` no `local.properties`, sincronize o Gradle e execute o app. No emulador Android, o app conecta a `10.0.2.2:8080`. A interface do Firebase fica em `http://localhost:4000`.

Para testar as regras automaticamente:

```powershell
cd firebase
npm.cmd run test:rules
```

## Conexao com o projeto real

1. Crie um projeto no Firebase Console e habilite o Cloud Firestore.
2. Adicione um app Android com o package `com.grupo.albumfigurinhas`.
3. Baixe o arquivo de configuracao para `app/google-services.json`.
4. Mantenha `useFirebaseEmulator=false` no `local.properties`.
5. Sincronize o Gradle. O plugin Google Services sera ativado automaticamente.

O arquivo `google-services.json` possui identificadores do projeto e esta ignorado pelo Git. Cada integrante deve obter sua copia pelo Console do Firebase.

## Carga inicial em producao

`google-services.json` configura o aplicativo Android, mas nao autentica scripts
administrativos. Para que um integrante execute o seed com a propria conta
Google, o proprietario deve conceder estes papeis no IAM do projeto:

- **Cloud Datastore User** (`roles/datastore.user`), para ler e gravar dados;
- **Service Usage Consumer** (`roles/serviceusage.serviceUsageConsumer`), para
  usar o projeto como cota das APIs.

Conceda esses papeis somente aos integrantes responsaveis pelos dados. No Google
Cloud Console, acesse **IAM e administrador > IAM > Permitir acesso**, informe o
e-mail Google do integrante e adicione os dois papeis.

Na maquina do integrante, instale a Google Cloud CLI e crie credenciais locais
com a propria conta:

```powershell
gcloud auth application-default login
gcloud auth application-default set-quota-project album-figurinhas-copa2026
```

Depois instale as dependencias e execute a carga:

```powershell
cd firebase
npm.cmd ci
npm.cmd run seed:production -- --project album-figurinhas-copa2026
```

A carga usa `set(..., merge: true)`, portanto pode ser repetida sem duplicar
documentos. Antes de executar em producao, altere `firebase/seed-data.json`,
teste no emulador, envie um Pull Request e aguarde a revisao. Execucoes
simultaneas podem fazer a ultima carga sobrescrever campos alterados por outra.

Quando a pessoa nao precisar mais executar scripts administrativos nessa
maquina, pode remover as credenciais locais:

```powershell
gcloud auth application-default revoke
```

Nao gere nem compartilhe `firebase-adminsdk*.json` para esse fluxo. Credenciais
ADC pertencem a cada integrante e respeitam os papeis concedidos no IAM.

## Regras e indices

As regras permitem leitura publica somente em `competitions` e suas
subcolecoes, e bloqueiam qualquer escrita pelo aplicativo. O script usa a
biblioteca de servidor, ignora essas regras e autoriza cada operacao pelo IAM da
conta Google autenticada.

Para autenticar a CLI e publicar regras/indices:

```powershell
cd firebase
npx.cmd firebase login
npx.cmd firebase deploy --only firestore --project SEU_FIREBASE_PROJECT_ID --config ../firebase.json
```

O deploy local sobrescreve as regras existentes no Console. `firestore.rules` deve ser tratado como a fonte oficial das regras do projeto.

## Estrutura remota

```text
competitions/copa_mundo_2026
  name, edition, trophyImage

competitions/copa_mundo_2026/teams/{teamId}
  name, victories, description, badge, colors, coach

competitions/copa_mundo_2026/teams/{teamId}/players/{playerId}
  name, position, number, photo, stats
```

Em producao, o Android usa cache persistente de 100 MB e indices locais automaticos. No emulador, usa apenas memoria para evitar que dados antigos sobrevivam ao reinicio do Firestore local.
