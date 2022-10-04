# Java API Checkout SDK  

<img align="right" src="./javabis.png">

[![Version](https://img.shields.io/badge/version-1.0.0--beta-blue)]()
[![Coverage](https://img.shields.io/badge/coverage-97%25-brightgreen)]()
[![Java version](https://img.shields.io/badge/Java%20Version-1.8-orange)]()

Este sdk es una herramienta que permite realizar ciertas acciones de una forma mas fácil sin tener que
utilizar API Checkout directamente.

## Contenido
- [API Checkout Java SDK](#api-checkout-java-sdk)
  - [Funciones disponibilizadas](#funciones-disponibilizadas)
  - [Requisitos e instalacion](#requisitos-e-instalacion)
  - [Como usarlo](#como-usarlo) 
  - [API Checkout docs](#api-checkout-docs)   
  - [Colaboradores](#colaboradores)
        

## Funciones disponibilizadas
Las funciones disponibilizadas hasta el momento son las siguientes:
- Get Auth Token
- Create Order
- Get Order
- Get Failed Notifications

<br>

**Get Auth Token**
<br>

Mediante esta funcionalidad se podrá obtener un token (requerido en futuras acciones).
Para ello deberemos invocar al metodo provisto por el cliente, denominado 'getAuthToken'.

<br>

**Create Order**
<br>

Con esta funcionalidad, podremos realizar la creación de una orden. Requiere entre otras cosas, obtener un token previamente.

<br>

**Get Order**
<br>

Con esta funcionalidad, podremos obtener una orden por su ID. Requiere obtener previamente un token.


## Requisitos e instalación

El Cliente esta desarrollado haciendo uso de la version 8 de Java. La versión mínima es esta misma.

Para instalar el sdk en tu aplicación, deberás importar el jar como dependencia.

//TODO link de descargas de jars aqui :)

## Como usarlo

Luego de haber importado como dependencia el jar del sdk, primero debemos crear una instancia del cliente.


### Crear un Cliente
La opción mas simple es indicar si el entorno es o no de prueba (STAGE-PRODUCCION) - (true - false)
```
Client client = new Client(true);
```

Existe tambien una variante, que no posee parámetros y que nos retorna una instancia *PRODUCTIVA* del client.

```
Client client = new Client();
```

Por último también puede usarse el constructor que como parametros espera una Config. 
De esta forma, deberemos crear primero la config indicando el environment (donde los valores posibles también son STAGE y PRODUCCION) y luego 
pasarla como parametro del constructor asociado.

```
Config cfg = new Config("STAGE");
Client client = new Client(cfg);
```

<br>

### Obtencion de Token
Luego de tener un cliente instanciado, procederemos a realizar la obtención de un token.

Para esto invocaremos al metodo getAuthToken(AuthRequest request), donde request es un objeto de tipo AuthRequest conformado de la siguiente manera:

```
AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
```

Una vez provistos se procede a realizar la llamada. Este metodo, almacena en el cliente el token para ser empleado en
los metodos que luego lo requieran.
``` 
String respuesta = client.getAuthToken(req); 
```

Donde la respuesta tiene la siguiente forma (datos ilustrativos):

```
{
  "access_token":"eyJhbGciOiJSUzI1NiIsCI6MTY2MzM0MjYaV9jaGVja291uF8EKajNA",
  "expires_in":86400,
  "token_type":"Bearer"
}
```


### Creacion de una orden

Para crear una orden, debe invocarse al metodo *createOrder(OrderRequest or)* 
Tal como la firma lo indica, requiere proveer un OrderRequest.

```
OrderRequest or = new OrderRequest("1986.10", "Copa Mundial Original", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", 
                "https://www.notify.me.com.ar",
                "ECOMMERCE");
                    
String createdOrder = client.createOrder(or); 
               
```

Donde, en caso exitoso la respuesta tiene la siguiente forma:

```
{
  "id":"/api/v2/orders/82b41bb1-ae75-40a0-a3c7-ffb35903a484",
  "type":"Order",
  "uuid":"82b41bb1-ae75-40a0-a3c7-ffb35903a484",
  "orderNumber":"0010866-0000000019",
  "currency":"032",
  "amount":"1986.1",
  "status":"PENDING",
  "refNumber":"a7448319-4b9a-4d71-8d27-6166629dced3"
 }
```

### Obtencion de una orden

A partir del id de una orden, podremos obtener información asociada utilizando el metodo 
*getOrder(String orderID)*

```
String orderID = "82b41bb1-ae75-40a0-a3c7-ffb35903a484"; 

String orderResponse = client.getOrder(orderID);

```

Donde, en caso de éxito, orderResponse tiene la siguiente forma:

```
{
  "order_id":"82b41bb1-ae75-40a0-a3c7-ffb35903a484",
  "status":"PENDING",
  "ref_number":"a7448319-4b9a-4d71-8d27-6166629dced3",
  "amount":1986.1
}
```


### Obtencion de notificaciones fallidas

A partir de la invocación del metodo *getFailedNotifications()* podremos recuperar las notificaciones fallidas para
el usuario. El mismo, no tiene parámetros, pero si requiere que el Client posea un token valido. Si este expiró,
deberá obtenerse otro.

``` 

String failedNotifications = client.getFailedNotifications();

```

En caso de éxito, obtendremos una lista (puede ser vacía) de notificaciones, de la siguiente forma:

```
{
    "notifications": [
        {
            "uuid": "9b115cab-c5df-47e1-bab8-61de8f1b19c7",
            "account_id": "e5b801ef-06bf-40ed-8dc2-6bb2f2684b5e",
            "status_code": 0,
            "attempts": 1,
            "amount": 10,
            "created_date": "2022-09-29T19:16:38Z"
        },
        {
            "uuid": "38d9f606-cf00-47b1-834b-debe9d5549fa",
            "account_id": "e5b801ef-06bf-40ed-8dc2-6bb2f2684b5e",
            "status_code": 403,
            "attempts": 1,
            "amount": 21.23,
            "created_date": "2022-07-26T21:58:52Z"
        }
    ]
}
```

## API Checkout docs

Además de este github donde se subirán actualizaciones, nuevas features o simplemente versiones actualizadas, es re 
lectura altamente recomendada, el sitio de
[API Checkout developers](https://developers.ualabis.com.ar/). 

## Colaboradores

- Lagger, Pablo      - pablo.lagger@uala.com.ar
- Crisafulli, Bruno - bruno.crisafulli@uala.com.ar
- Segura, Sebastian  - sebastian.segura@uala.com.ar
- Meze               - martin.abogado@uala.com.ar
