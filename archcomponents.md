# Arquitetura e Componentes 

## Model-View-ViewModel
Buscamos utilizar um modelo de arquitetura Model-View-ViewModel (MVVM). 
Nesse tipo de modelo, a View representa as Activities da aplicação, sendo responsáveis por mostrar na tela as informações ao usuário.
Buscamos também aplicar o Single Responsibility Principle (SRP) removendo a lógica de negócios do código das Activities.
Porém, é deixado como trabalho futuro uma refatoração de código que aprimore principalmente o código das activities CreateSeriesActivity e SeriesHappeningActivity, baseado nesse princípio.

Assim, a responsabilidade da lógica de negócios é passada para o ViewModel, sendo responsável por atender requisições da View e/ou retornando informações para apresentação na tela do usuário. 
Abaixo apresentamos um exemplo do clickListener na SeriesHappeningActivity responsável por iniciar o cronômetro para um exercício.

    play_pause_button.apply {
        setOnClickListener {
            viewModel.handleButtonPress()
        }
    }

Por fim, o Model representa os componentes-base de nossa aplicação (como Series, Exercise e ExerciseTimer).
Para o cronômetro em si, utilizamos o próprio CountdownTimer do sistema operacional Android. 
Esse componente está representado pela classe ExerciseTimer, que é ativada pelo ViewModel de nosso projeto.
Note que no MVVM, a View não sabe da existência do Model e vice-versa.
Isso torna alterações estruturais menos suscetíveis a erros colaterais, novamente baseadas no SRP.

## LiveData
Utilizando LiveData, criamos um atributo no timer referente ao tempo restante para o exercício. 
Esse LiveData é observado pelo ViewModel, com um Observer. 

Assim, a cada segundo, o timer utiliza o método .postValue() no LiveData para atualizar o tempo restante do exercício.

    override fun onTick(millisUntilFinished: Long) {

        remainingTime = millisUntilFinished / 1000
        TIMER_LIVE_DATA.postValue(remainingTime)
    }

Essa atualização é detectada pelo Observer.

    private fun observeDisplayedTimeChanges() {
        elapsedTimeObserver = Observer { newDisplayedTime ->
            viewToPresent.updateDisplayedTime(newDisplayedTime)
        }
        timerModel
            .getLiveDataFromTimer()
            .observe(viewToPresent.getContext(), elapsedTimeObserver)
    }

Com isso, o ViewModel então notifica a View de que um novo valor de tempo está disponível, sendo a Activity responsável somente por atualizar o widget na UI com o novo valor. Isso acontece a cada segundo. 
Assim, utilizando LiveData e um Observer, conseguimos desenvolver uma View (SeriesHappeningActivity) que não precisa saber da existência do cronômetro, somente utilizando o ViewModel como mediador. 

## Room
Utilizando Room, criamos um banco de dados para as atividades, contendo dados relativos aos exercícios que as compõem.

Criamos também outro banco de dados para o histórico das atividades, este contendo apenas uma data de quando ela foi completada.