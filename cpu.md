# CPU

## Consumo de CPU

Podemos dividir o consumo de CPU da aplicação em três categorias, como pode ser visto na imagem. Vamos em seguida discutir cada uma dessas categorias.

### 1. Troca de tela

A troca de tela é o que mais consome CPU na nossa aplicação. Isso se deve aos custos inerentes de carregar os elementos da UI, mas também tem o custo de recuperar a informação de um banco de dados para criar a lista de atividades ou a lista de histórico.

### 2. Criação de uma atividade

Essa tarefa não demanda muita CPU, já que ela se resume a preencher campos.

### 3. Execução de uma atividade

Essa etapa apresenta pico de CPU ao iniciar a atividade, pois é necessário iniciar um novo `CountdownTimer` (da biblioteca `os` do Android) para cada exercício que acontece.

![Image](android_profiler.jpg)

## Boas práticas

### Trabalhando com threads
Utilizamos a biblioteca `Anko` para executar tarefas intensivas de forma assíncrona, fora da UI Thread. Por exemplo, no código

    private fun addSeriesToDatabase() {
        doAsync {
            val db = SeriesDB.getDatabase(this@CreateSeriesActivity)
            db.getAccessObject().insertSeries(seriesBeingCreated)
        }
    }

usamos Anko para adicionar uma nova atividade ao banco de dados.

### Evitar APIs deprecadas
Usamos, por exemplo, RecyclerView em vez de ListView. O Android Studio ajuda bastante nesse aspecto, pois ele sinaliza visualmente o uso de código deprecado.


### Evitar abusos
Não usamos nenhum recurso obscuro da linguagem.

### Preferir val em vez de var para constantes
Tentamos ao máximo utilizar val no nosso código. O Android Studio também sinaliza a maior parte dos casos em que isso pode ser feito.

### God classes
Ao adotar uma arquitetura de MVVM (Model View ViewModel), busca-se diminuir a responsabilidade para as classes que compõem a aplicação.
Assim, ao definir componentes com somente uma responsabilidade (SRP), evita-se a criação de God Classes.
Apesar de não atingir completamente esse estado em nosso desenvolvimento, estávamos com o pensamento em mente de não desenvolver uma God Class.
Essa boa prática auxiliou tanto no processo de testes, quanto no processo de debugging, pois a leitura do código torna-se mais eficiente.