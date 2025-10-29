# Comandos básicos de `git`

Esto es una pequeña introducción a **git** para que conozcais los comandos esenciales para gestionar el repositorio y subir cambios.

## Subir cambios

Para subir los cambios, primero se tienen que añadir los ficheros:
```bash
$ # Añadir los ficheros que se han cambiado
$ git add .
$ # Para añadir ficheros manulamente
$ # git add fichero1 fichero2

$ # Guardar los cambios añadidos
$ git commit -m "Resumen de los cambios"

$ # Subir los cambios al repositorio
$ git push origin
$ # Si la rama no está creada
$ # git push -u origin rama
```

## Gestión de ramas

### Creación

```bash
$ git branch rama
$ git switch -c rama # Esto se mueve a la rama después de crearla
```

### Eliminación

```bash
$ git branch -d rama        # Intenta eliminar la rama
$ git branch -D rama        # Fuerza la eliminación de la rama
$ git push -d origin rama   # Intenta eliminar la rama remota
```

### Movimiento

```bash
$ git switch rama
$ git checkout rama
```

# Comandos útiles

```bash
$ git log                           # Muestra un historial de cambios
$ git status                        # Musetra un estado
$ git branch                        # Muestra las ramas locales
$ git branch -r                     # Muestra las ramas remotas
$ git branch -a                     # Muestra todas las ramas
$ git restore fichero               # Deshace los cambios realizados sin guardar
$ git restore --staged ficheros     # Elimina el fichero del "git add"
$ git tag nombre                    # Le asigna un tag a la rama
$ git diff hash-commit              # Muestra los cambios realizados desde ese commit
```

---

# Gestión de Ramas

Para mantener un flujo de trabajo ordenado y poder diferenciar fácilmente qué commits y ramas pertenecen a cada cambio del código, se establecen las siguientes "normas".

> No os voy a cortar la cabeza si os olvidais, pero mejor si lo haceis asi (las empresas funcionan parecido)

## Clasificación de ramas

Podemos clasificar las ramas de 3 formas:

- `feature` → Estamos creando una **nueva característica**.  
- `update` → Estamos **actualizando** una característica existente.  
- `bugfix` → Estamos **solucionando un bug**.  

**Ejemplos:**

```text
feature/crear-personaje
update/cambiar-campo-nombre-json
bugfix/carga-de-personajes
```

> Utilizar siempre guiones en vez de espacios en el nombre.

> Esta convención permite identificar rápidamente el tipo de trabajo que se está realizando en cada rama.
