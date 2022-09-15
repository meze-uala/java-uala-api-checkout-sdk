# Java API Checkout SDK  

<img align="right" width="159px" src="./duke.png">

[![Version](https://img.shields.io/badge/version-1.0.0--beta-blue)]()
[![Coverage](https://img.shields.io/badge/coverage-88%25-green})]()
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


###Crear un Cliente
La opción mas simple es indicando el entorno (STAGE-PRODUCCION)
```
Client client = new Client("STAGE");
```

Existe tambien una variante, que puede tomar las properties, y en caso de encontrar una bajo el key "ENVIRONMENT'
creará una instancia acorde al valor encontrado. Si es un valor no valido, por default se retornará una instancia de *stage*

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

Una vez provistos se procede a realizar la llamada.
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

Para crear una orden, debe invocarse al metodo *createOrder(OrderRequest or, String token)* 
Tal como la firma lo indica, requiere crear un OrderRequest y proveer un token previamente obtenido.

```
OrderRequest or = new OrderRequest("1986.10", "Copa Mundial Original", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", 
                "https://www.notify.me.com.ar",
                "ECOMMERCE");
     
String token  = "eyJhbGciOiJSUzI1NiIsCI6MTY2MzM0MjYaV9jaGVja291uF8EKajNA"                
                
String respuesta2 = client.createOrder(or, token); 
               
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
*getOrder(String orderID, String authToken)*

```
String orderID = "82b41bb1-ae75-40a0-a3c7-ffb35903a484";
String token = "eyJhbGciOiJSUzI1NiIsCI6MTY2MzM0MjYaV9jaGVja291uF8EKajNA";

String orderResponse = client.getOrder(orderID, token);

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

## API Checkout docs

Además de este github donde se subirán actualizaciones, nuevas features o simplemente versiones actualizadas, es re 
lectura altamente recomendada, el sitio de
[API Checkout developers](https://developers.ualabis.com.ar/). 

## Colaboradores

- Lagger, Pablo      - pablo.lagger@uala.com.ar
- Crisafulli, Bruno - bruno.crisafulli@uala.com.ar
- Segura, Sebastian  - sebastian.segura@uala.com.ar
- Meze               - martin.abogado@uala.com.ar
