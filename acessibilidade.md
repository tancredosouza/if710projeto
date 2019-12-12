# Acessibilidade

De acordo com [este link](https://developer.android.com/guide/topics/ui/accessibility/apps), tomamos preocupação os tópicos a seguir.

### Visibilidade do texto

Garantimos que todo texto possui pelo menos tamanho `18sp`.

Além disso, nos preocupamos com a questão da taxa de constraste entre os elementos da UI. Por exemplo, o botão de adicionar uma nova atividade possui background com cor `#454545` enquanto que o símbolo `+` no foreground possui cor `#7FC500`, que equivale a uma taxa de `4.5:1`.

### Controles grandes
Usamos botões de tamanho pelo menos `48dp` de largura e comprimento. Por exemplo, o botão de ação de deletar

    <Button
      android:id="@+id/delete_action"
      android:layout_width="48dp"
      android:layout_height="48dp"
      android:layout_weight="2"
      ...
possui o tamanho mínimo adequado.

### Descrição de cada elemento da UI
Cada elemento da UI possui uma descrição, como no caso dos botões de controle da atividade.


    <ImageButton
        android:id="@+id/play_pause_button"
        ...
        android:contentDescription="Botão de começar e pausar a atividade" />

    <ImageButton
      android:id="@+id/next_exercise_button"
      ...
      android:contentDescription="Botão para ir ao próximo exercício" />

    <ImageButton
      android:id="@+id/stop_series_button"
      ...
      android:contentDescription="Botão para cancelar a atividade" />
