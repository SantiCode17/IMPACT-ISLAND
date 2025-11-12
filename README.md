# Como empezar el proyecto

Primer se tienen que exportar las varaibles de entorno.

```bash
$ source .env
```

Después se ejecuta el contenedor de la BBDD.

```bash
$ docker compose -f docker/docker-compose.dev.yml up -d
```

Y por último abre tu IDE desde esta misma terminal y ejecuta el proyecto.

Como es un proyecto de `maven` los puedes compilar y ejecutar con `maven` si quieres,
pero, siempre cargando las variables de entorno antes.
