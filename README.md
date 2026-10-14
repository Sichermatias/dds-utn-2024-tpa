# Heladeras solidarias — TP anual de Diseño de Sistemas (UTN FRBA, 2024)

Plataforma web para una ONG que reduce la inseguridad alimentaria con una red de heladeras comunitarias. Personas y organizaciones colaboran donando dinero o viandas, distribuyendo viandas entre heladeras o hosteando una heladera; las heladeras reportan su estado con sensores y el sistema gestiona incidentes, puntos y reportes.

Trabajo práctico anual en equipo (Grupo 12) de la materia Diseño de Sistemas de Información.

## Funcionalidades

- **Colaboraciones** de personas humanas y jurídicas: donación de dinero y de viandas, redistribución de viandas, hosteo de heladeras, registro de personas en situación de vulnerabilidad y oferta de premios.
- **Puntos y canje de premios** según las colaboraciones de cada persona.
- **Carga masiva** de colaboradores desde CSV.
- **Heladeras con sensores** de temperatura y movimiento que publican por **MQTT**; las alertas y fallas técnicas generan **incidentes** y visitas de técnicos.
- **Suscripciones** a eventos de una heladera (pocas viandas, heladera llena, desperfectos) con aviso por mail.
- **Recomendación de puntos** para ubicar heladeras y donar, usando la API de Georef.
- **Reportes semanales** programados: fallas por heladera, viandas por heladera y viandas donadas por colaborador.
- Interfaz web con login y control de acceso por rol.

## Stack

Java 17 · Javalin · Handlebars · Hibernate / JPA · MySQL · MQTT (Eclipse Paho) · Quartz · Retrofit · JavaMail · JUnit 5 · Mockito · Maven

## Estructura

```
src/main/java/ar/edu/utn/frba/dds/
├── dominio/       modelo: colaboraciones, personas, heladeras e infraestructura, incidentes, reportes, puntos
├── controllers/   controladores web (Javalin)
├── services/      casos de uso e integraciones (Georef, sensores, incidentes)
├── models/        repositorios y persistencia
├── server/        servidor, rutas, handlers y excepciones
└── middlewares/   autenticación
src/main/resources/templates/   vistas Handlebars
```

## Cómo correrlo

1. Java 17 y Maven. Una base MySQL local (`tpa_2024_dds`).
2. Completar la conexión a la base en `persistence.xml` y la cuenta de envío en `src/main/resources/mailconfig.properties`.
3. Correr los tests: `mvn verify`
4. Levantar la app web: clase `ar.edu.utn.frba.dds.server.App`.

## Equipo

[@Bietti-Federico](https://github.com/Bietti-Federico) · [@Carrizo-Gonzalo](https://github.com/Carrizo-Gonzalo) · [@Sichermatias](https://github.com/Sichermatias) · [@lautaropetronacci](https://github.com/lautaropetronacci) · [@tinchoi99](https://github.com/tinchoi99)

Servicio REST complementario del mismo TP: [2024-tpa-mi-no-grupo-12-api-rest](https://github.com/Sichermatias/2024-tpa-mi-no-grupo-12-api-rest).
