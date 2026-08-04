h1. Índice

* 1. Objetivo del proyecto
* 2. Tecnologías que vamos a utilizar y por qué
* 3. Arquitectura elegida
* 4. Microservicios iniciales
* 5. Requisitos funcionales y no funcionales
* 6. Roadmap de desarrollo



h1. 1. Objetivo del proyecto
	Desarrollar una plataforma backend de comercio electrónico basada en una arquitectura de microservicios, diseñada para simular un entorno de desarrollo profesional. El proyecto servirá como base para aprender, aplicar y demostrar conocimientos en tecnologías ampliamente utilizadas en el desarrollo backend con Java, incluyendo Spring Boot, JPA/Hibernate, OpenAPI, Swagger, Kafka, Docker, Jenkins, Kubernetes y AWS, así como patrones de arquitectura como DDD y Arquitectura Hexagonal

El desarrollo se realizará de forma incremental, implementando cada componenete siguiendo buenas prácticas de diseño, testing, integración continua y despliegue. El objetivo no es únicamente construir una aplicación funcional, sino comprender el papel de cada tecnología dentro d eun sistema distribuido y adquirir experiencia práctica similar a la que se encuentra en proyectos reales de empresa

h2. 1.1. Objetivos funcionales
* Desarrollar una API REST para la gestión de usuarios, productos, inventarios y pedidos
* Implementar un sistema de autenticación y autorización basado en JWT
* Gestionar el ciclo de vida de un pedido, desde su creación hasta su finalización o cancelación
* Procesar eventos asíncronos mediante Kafka para coordinar la comunicación entre Microservicios

h2. 1.2. Objetivos técnicos
* Diseñar una arquitectura basada en microservicios siguiendo principios de DDD y Arquitectura Hexagonal
* Documentar todas las APIs utilizando OpenAPI y Swagger
* Implementar pruebas unitarias e integración utilizando JUnit y Mockito
* Contenerizar todos los servicios mediante Docker y Docker Compose
* Automatizar la construcción y las pruebas mediante Jenkins
* Desplegar la aplicación sobre Kubernetes
* Prublicar la solución en AWS utilizando servicios habituales en entornos empresariales
* Mantener una estructura de proyecto profesional con documentación, control de versiones y buenas prácticas de desarrollo 


h1. 2. Tecnologías que vamos a utilizar y por qué
* Java 21
Lenguaje principal de proyecto. Es la versión LTS moderna más utilizada en nuevos proyectos empresariales
* Spring Boot
Framework para desarrollar microservicios de forma rápida, mantenible y siguiendo el ecosistema estándar de Java
* Spring Data JPA + Hibernate
Abstracción para el acceso a datos y ORM para trabajar con PostgreSQL sin escribir SQL para las operaciones más comunes
* PostgreSQL
Base de datos relacional robusta y ampliamente utilizada en aplicaciones empresariales
* Spring Security + JWT
Autenticación y autorización mediante tokens, un estándar habitual en APIs REST
* JUnit5
Framework para pruebas unitarias y de integración
* Mockito
Simulación de dependencias para probar la lógica de negocio de forma aislada. Uno de los objetivos principales del proyecto es aprender a utilizarlo correctamente
* OpenAPI + Swagger
Documentación automática de las APIs REST y posibilidad de probar los endpoints desde la propia interfaz web
* Apache Kafka
Comunicación asíncrona basada en eventos entre microservicios, reduciendo el acoplamiento entre ellos
* Docker
Contenerización de todos los servicios para asegurar un entorno reproducible
* Docker Compose
Orquestación del entorno de desarrollo local (bases de datos, Kafka y microservicios)
* Jenkins
Automatización de la integración continua (build, tests y creación de imágenes Docker)
* Kubernetes
Orquestación y despliegue de contenedores simulando un entorno de producción
* AWS
Despliegue del proyecto utilizando servicios cloud habituales en empresas (EC2, ECR, S3, CloudWatch y, como objetivo final, EKS)
* Git + GitHub
Control de versiones, trabajo por ramas y documentación del proyecto


h1. 3. Arquitectura elegida
h2. 3.1. Arquitectura general
la aplicación seguirá una arquitectura de microservicios, donde cada servicio será una aplicación independiente con una única responsabilidad de negocio.
Cada microservicio será autónomo y podrá desarrollarse, desplegarse y escalarse de forma independiente.
La comunicación entre servicios se realizará mediante dos mecanismos:
* Comunicación síncrona
Mediante APIs REST para operaciones que requieran una respuesta inmediata.
* Comunicación asíncrona
Mediante Apache Kafka para la propagación de eventos de negocio entre Microservicios
Este enfoque permite reducir el acoplamiento entre servicios y favorece la escalabilidad y mantenibilidad del sistema.

h2. 3.2. Arquitectura interna de cada microservicio
Todos los microservicios seguirán la misma estructura interna basada en Arquitectura Hexagonal (Ports & Adapters)
El dominio será completamente independiente de Spring Boot, JPA, Kafka o cualquier otra tecnología.

API REST - Controllers - Application (Casos de uso) - | Domain (Reglas de negocio) | - Ports (Interfaces) - Infrastructure (JPA, Kafka, REST Clients...)

Esto facilitará:
* Cambiar tecnologías sin modificar la lógica de negocio
* Escribir pruebas unitarias aisladas
* Mantener una separación clara entre responsabilidades

h2. 3.3. Diseño del dominio
Seguiremos los principios de Domain-Driven Design (DDD), pero de forma pragmática.
No pretendemos implementar todos los patrones de DDD, sino utilizar aquellos que aportan valor al proyecto
Principalmente utilizaremos:
* Entidades (Entities)
* Objetos de valor (Value Objects)
* Casos de uso (Application Services)
* Repositorios (Ports)
* Eventos de dominio
* Bounded Contexts representados por los distintos Microservicios
Nuestro objetivo será que la lógica de negocio resida en el dominio y no en los controladores o Repositorios

h2. 3.4. Estilo Arquitectónico
El sistema seguirá un estilo Event-Driven Architecture.
Cuando ocurra un evento importante dentro del negocio, se publicará un evento en Kafka.

Cliente crea un pedido -> Order Service -> Pedido Almacenado -> OrderCreatedEvent -> Kafka -> Inventory Service / Notification Service

De esta forma, cada microservicio reaccionará únicamente a los eventos que le interesen, evitando dependencias directas entre ellos
Esta combinación es exactamente la que más se repite en proyectos empresariales Java modernos:
* Microservicios -> Organización del sistema
* Arquitectura Hexagonal -> Organización del código dentro de cada servicio
* DDD -> Organización del dominio y la lógica de negocio
* Event-Driven -> Comunicación entre servicio mediante eventos


h1. 4. Microservicios iniciales
Crearemos los microservicios que realmente representen un bounded context del dominio, no simplemente una tabla de la base de datos:

h4. User Service
Gestionar toda la información relacionada con los usuarios.
Funcionalidades:
* Registro
* Inicio de sesión
* Gestión del perfil
* Roles (Cliente y Admin)
* Generación y validación de JWT
Base de datos -> Sólo almacenará información de usuarios. No conoce absolutamente nada de pedidos ni productos.

h4. Product Service
Gestionar el catálogo
Funcionalidades:
* Productos
* Categorías
* Búsquedas
* Filtros
* Imágenes (más adelante en S3)
Base de datos -> Solo productos. No conoce usuarios ni pedidos

h4. Order Service
Gestionar el ciclo de vida de los pedidos, es el corazón del negocio
Funcionalidades:
* Crear pedido
* Cancelar pedido
* Consultar historial
* Cambiar estado
* Calcular importe total
No modifica directamente el inventario ni envía correos. En su lugar, publicará eventos en Kafka para que otros servicios reaccionen.

h4. Inventory Service
Gestiona el stock
Funcionalidades:
* Reservar stock
* Liberar stock
* Actualizar cantidades
* Consultar disponibilidad
Tiene reglas de negocio propias, independientes al catálogo

h4. Notification Service
Enviará notificaciones
* Pedido creado
* Pedido enviado
* Pedido cancelado
* Recuperación de contraseña (más adelante)
Este servicio no recibirá llamadas REST de otros microservicios. Su función será escuchar eventos publicados en Kafka

El servicio del carrito y de pagos, se valorarán a futuro


h1. 5. Requisitos funcionales y no funcionales
h2. 5.1. Requisitos funcionales
h4. RF-01. Gestión de usuarios