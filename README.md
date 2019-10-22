# Appersonal Trainer

### IF710 - Programação para Dispositivos Móveis com Android
Especificação Inicial do Projeto

## Ideia de aplicativo
Cuidar de sua saúde é muito importante, e muitas pessoas buscam esse cuidado através de atividades físicas. 
Com apps de fitness tracking, nos tornamos mais motivados ao organizar nossos exercícios e acompanhar nosso gasto calórico.
Algumas dessas atividades são compostas por vários exercícios, cada um chama-se série. 
Essas séries são realizadas em sequência, podendo ou não ter um intervalo de pausa entre elas.
Porém, em aplicações de fitness tracking já existentes, é necessário escolher manualmente a próxima série quando uma termina, atrapalhando seu pique durante a atividade.
Se já sabemos quanto tempo cada série leva ou quanta distância será percorrida em cada série, por que não só definirmos uma atividade com essas séries e deixar o aplicativo trocar automaticamente? O Appersonal Trainer fará isso por você. Ele pode até mesmo pausar a atividade, caso detecte que você parou de se movimentar.

### Público-alvo
Pessoas que realizam atividades físicas.

## Concorrentes
Existem muitos apps de fitness tracking no mercado hoje em dia. 
Porém, não foi encontrado algum que possua a feature proposta nesse projeto. 

Alguns exemplos de aplicações de fitness tracking.
- Zones
- Workout (iOS)
- Runtastic.
- Google Fit.
- Nike Training Club.
- Strava.
- Runkeeper.

## Estrutura da aplicação
![alt text](https://github.com/tancredosouza/if710projeto/blob/master/especificacao/imgs/mockupFluxoTelas.gif)

## Desenvolvimento
O projeto será realizado em dupla, tendo seu esforço de trabalho dividdo igualmente entre os integrantes. 
Iremos realizar o desenvolvimento dessa aplicação utilizando Test-Driven Development (TDD). 
O TDD prega o desenvolvimento dos testes de uma feature antes que essa seja adicionada ao código em produção. 
Dessa forma, garante-se que qualquer feature adicionada terá pelo menos um teste que a avalia. 
Nós do grupo escolhemos utilizar essa filosofia como um aprendizado e experimentação, visto que não existe consenso sobre se é "certo" ou "errado" utilizar TDD. 

Assim, será necessário:
- Definir primeiramente quais testes serão feitos (unitário, integração ou aceitação)
- Realizar pesquisas sobre as ferramentas de testes que poderão ser utilizadas
- Realizar pesquisas sobre bibliotecas para Kotlin que fornecem dados de saúde através de um android app
- Implementação das features utilizando TDD
