# Testes

## Espresso
No desenvolvimento do projeto, dedicamos um tempo para o estudo da biblioteca Espresso. Essa biblioteca foi utilizada no desenvolvimento de testes instrumentados para a activity “CreateSeriesActivity”.
Como essa activity possuía a maior interação com o usuário (preenchimento de campos de texto, vários botões na tela, etc.), ela se tornou nosso foco no aprendizado de testes de instrumentação.

A seguir, apresentamos alguns exemplos de operações de usuário realizadas com o Espresso.

### Exemplo 1: Ação simples

    private fun userFillsSeriesName() {
        onView(withId(R.id.new_series_name)).perform(typeText(seriesName))
        Espresso.closeSoftKeyboard()
    }


Como apresentado no código acima, foi possível utilizar o Espresso para atuar como o usuário da aplicação. 
O principal benefício resultante desses testes foi a documentação do código, fazendo com que cada função de teste servisse como um caso de “user stories”.

### Exemplo 2: User story

    fun whenUserTriesToCreateExerciseWithoutMinutes_shouldOnlyDisplayToast() {
        userFillsSeriesName()
        userCreatesExercise(someExercise)
        userClearsExerciseMinutesField()
        userPressesButtonToSaveExercise()
        shouldThrowToastWithMessage(R.string.exercise_has_empty_time_field_error)
    }

### Dificuldade
Uma dificuldade encontrada no desenvolvimento dos testes de instrumentação foi checar se mensagens de formato Toast estavam sendo lançadas pela aplicação. 
Necessitamos implementar uma classe ToastMatcher, que checa por N instâncias de tempo se em algum momento um toast com a mensagem esperada foi lançado. 
Isso trouxe um desafio, pois o Espresso nativamente não possui uma abstração para esse tipo de checagem. 

### Últimas palavras
Assim, pudemos construir uma documentação inicial de nossa aplicação em formato de user stories, e aproveitamos dos testes para checar mudanças no código da activity ‘CreateSeriesActivity’.
Concluímos, portanto, que utilizar testes instrumentados agilizou bastante o processo de desenvolvimento ao substituírem os testes manuais dessa activity em particular.

[Veja aqui](https://i.imgur.com/aBitQ6k.mp4) alguns testes sendo executados automaticamente.