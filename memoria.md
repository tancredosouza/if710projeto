# Memória

Como se pode perceber no gráfico, o consumo de memória permanece praticamente constante. Isso se deve ao fato de nossa aplicação só utilizar um banco de dados com Room. 
Espera-se que quanto maior o banco de dados, mais intensivo será o consumo de memória, pois um maior número de informações deverá ser carregado.
Isso ocorre majoritariamente nas telas iniciais e de histórico do aplicativo, onde necessita-se acessar todas as atividades do usuário e o seu histórico.

![Image](android_profiler.jpg)

### Leak Canary
Não foi detectado nenhum memory leak ao utilizar a ferramenta Leak Canary. Como o processo de detecção envolve executar fluxos da aplicação, seria interessante como um próximo passo, utilizar em conjunto ferramentas de testes aleatórios como o Android Monkey.