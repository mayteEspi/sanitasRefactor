Pasos para ejecutar proyecto

1. Si se va abrir con un ide (sts...) Instalar lombok en el ide https://projectlombok.org/download
2. El método principal usa un CompletionService para lanzar asincronamente ciertos calculos (simulaciones de poliza frequencia). Todo el código que gestiona esos Callables habria que extraerlo a parte, teniendo una clase que se encarge de esa gestión de Callables, llamarlos y devolver el resultado. De esta manera, aparte de dejar el código de la clase RealizarSimulacion más limpio, seria facilmente testeable (ahora mismo con las llamadas asincronas no es posible implementar un tests que no falle una vez si una vez no...).

3 Ha sido necesario añadir la dependencia de la commons-lang porque las clases del jar entregado sino no funcionaba:
 <dependency>
    <groupId>commons-lang</groupId>
    <artifactId>commons-lang</artifactId>
    <version>2.6</version>
</dependency>

