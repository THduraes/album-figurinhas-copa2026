# Roteiro de apresentação

## Álbum de Figurinhas Digital - Copa do Mundo 2026

Este roteiro resume o que cada integrante deve mostrar e explicar durante a
apresentação. A duração sugerida é de aproximadamente 10 minutos.

## 2. Implementação Android

### O que foi desenvolvido

O aplicativo foi criado em Kotlin com Jetpack Compose e possui:

1. Tela de carregamento.
2. Tela da competição com as seleções.
3. Tela de cada equipe.
4. Tela de detalhes dos jogadores.
5. Tela de detalhes dos treinadores.
6. Navegação entre todas as telas.

### Como funciona o MVVM

O projeto foi dividido em três partes principais:

- **View:** mostra as telas e recebe os cliques do usuário.
- **ViewModel:** controla o estado da tela e solicita os dados.
- **Model/Repository:** representa os dados e acessa o Firebase.

Fluxo simplificado:

```text
Firebase -> Repository -> ViewModel -> Tela
```

Quando o usuário abre uma equipe, a tela informa o identificador ao ViewModel.
O ViewModel solicita os dados ao Repository e atualiza a tela quando recebe a
resposta.

### O que mostrar

1. Abrir o aplicativo.
2. Selecionar uma equipe.
3. Abrir um jogador.
4. Voltar e abrir o treinador.
5. Mostrar rapidamente as pastas `model`, `repository`, `viewmodel` e `ui`.

### Fala sugerida

> O aplicativo foi desenvolvido em Kotlin com Jetpack Compose. Utilizamos o
> padrão MVVM para separar as telas, a lógica e o acesso aos dados. Isso deixa o
> projeto mais organizado e facilita o trabalho de diferentes integrantes.

## 3. Integração de dados

### Base remota

O aplicativo utiliza o Cloud Firestore. Os dados estão organizados assim:

```text
competitions
  -> copa_mundo_2026
      -> teams
          -> brasil
              -> players
```

Cada equipe possui nome, descrição, cores, títulos, treinador e jogadores. Os
jogadores possuem nome, posição, número, estatísticas e URLs das imagens.

### Comunicação com o Firebase

O aplicativo realiza consultas para buscar:

- A competição e as equipes participantes.
- Os dados de uma equipe selecionada.
- Os jogadores e o treinador da equipe.

Exemplo simplificado de resposta:

```json
{
  "name": "Neymar Jr.",
  "position": "Atacante",
  "number": 10,
  "photo": "https://...",
  "thumbnail": "https://..."
}
```

As URLs são carregadas pelo aplicativo. Também existem imagens locais de apoio
para que a interface não fique vazia quando uma imagem remota falhar.

### O que mostrar

1. Abrir o Firestore no navegador.
2. Mostrar uma equipe e um jogador.
3. Comparar os dados do documento com o que aparece no aplicativo.
4. Explicar que o `google-services.json` conecta o app ao projeto Firebase.

### Fala sugerida

> Os dados ficam no Cloud Firestore e são consultados pelo aplicativo. O
> Repository recebe os documentos, transforma os campos em objetos Kotlin e os
> entrega ao ViewModel. Assim, as telas mostram os dados da base remota sem
> manter informações fixas na interface.

## 4. Testes e usabilidade

### Resultados

| Verificação | Resultado |
|---|---|
| Testes unitários Android | 8 de 8 aprovados |
| Testes das regras do Firestore | 5 de 5 aprovados |
| Compilação do APK | Aprovada |
| Android Lint | Aprovado |
| URLs de imagens | 55 de 55 funcionando |
| Documentos no Firestore | 31 documentos atualizados |

### Testes funcionais

Foram verificados os seguintes fluxos:

1. Abertura do aplicativo e carregamento da competição.
2. Navegação para as equipes.
3. Abertura dos jogadores e treinadores.
4. Exibição das informações vindas do Firebase.
5. Carregamento e enquadramento das imagens.
6. Funcionamento do botão de voltar.

Durante o desenvolvimento, foram corrigidos problemas de imagens ausentes,
fotos cortadas e associação incorreta da foto de um treinador.

### O que mostrar

1. Executar o fluxo principal sem interrupções.
2. Mostrar que as imagens e os dados carregam corretamente.
3. Apresentar a tabela de resultados acima.
4. Explicar uma melhoria feita após os testes.

### Fala sugerida

> Realizamos testes automatizados e testes manuais no aplicativo. Verificamos a
> navegação, a leitura do Firebase e o carregamento das imagens. Os problemas
> encontrados foram corrigidos, incluindo imagens cortadas e a foto incorreta
> de um treinador.

## Checklist final

- Celular ou emulador preparado e com internet.
- Aplicativo instalado e aberto antes da apresentação.
- Firebase Console aberto no documento que será demonstrado.
- Integrantes definidos para cada etapa.
- Fluxo principal testado antes de começar.
- Evitar mostrar credenciais ou chaves administrativas.
