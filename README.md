# IMPLEMENTACIÓN DE LAS API REST DE LA PASARELA DE PAGO PAYPHONE
En este trabajo use Volley para consumir la API que me da PayPhones. 
La informacion del cliente que solicita la API ya se encuentran cargadas en una variable de la app, simulando que este "ha iniciado sesión".
PayPhone permite la opcion de invitar a un usuario, para que sirva como probador, en mi caso use esa opción.

## INTERFACES

Esta es la interfaz principal, donde podemos encontrar 2 carros disponibles para comprar.
(El precio de los carros es bajo, por que PayPhones no acepta transferencias de 1000$ en adelante)

![alt text](https://github.com/AndyNinasunta/Tarea_Api_PayPhones/blob/master/CAPTURAS/Interfaz%20principal.PNG)

Una vez se elije comprar un carro, la app redirige al usuario a la aplicacion PayPhone, para que decida realizar o no el pago.

![alt text](https://github.com/AndyNinasunta/Tarea_Api_PayPhones/blob/master/CAPTURAS/Comprando%20primer%20carro.PNG)

Si acepta se le mostrará el detalle del pago.Caso contrario, solo se mostrará la interfaz principal.

![alt text](https://github.com/AndyNinasunta/Tarea_Api_PayPhones/blob/master/CAPTURAS/Pago%20aceptado%20primer%20carro.PNG)
![alt text](https://github.com/AndyNinasunta/Tarea_Api_PayPhones/blob/master/CAPTURAS/Comprando%20segundo%20carro.PNG)

Interfaz de PayPhone donde quedan registrados los pagos del cliente.

![alt text](https://github.com/AndyNinasunta/Tarea_Api_PayPhones/blob/master/CAPTURAS/Historial%20de%20pagos%20del%20cliente.PNG)

Interfaz de la cuenta PayPhone Developer, aqui podemos ver la lista de cobros que se ha hecho.

![alt text](https://github.com/AndyNinasunta/Tarea_Api_PayPhones/blob/master/CAPTURAS/Registro%20de%20cobros%20dentro%20de%20PayPhone%20developer.PNG)
