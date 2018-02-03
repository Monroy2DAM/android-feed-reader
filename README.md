# Reproductor de listas podcasts remotas

Aplicación Android que reproduce podcasts extraídos de RSS's de internet.

## Cómo conectar el repositorio con Android Studio

Mira este [tutorial](https://androidstudiofaqs.com/tutoriales/como-usar-git-en-android-studio)

## ¿Cómo puedo colaborar?

En la página Issues puedes encontrar tareas por realizar (issues) y objetivos que acaparan muchas tareas (milestones).
* Programando
* Gestionando tareas: añadiendo, proponiendo funciones, clarificando, clasificando, ...
* Mejorando el fichero [README](README.md) y la documentación
* Limpiando GitHub de archivos que no sean necesarios para correr el proyecto

## Errores típicos

### Cannot resolve AppCompActivity

Las siguientes opciones pueden solucionarlo:

* Clean/Build/Rebuild project.
* File -> Invalidate Caches / Restart
* En gradle, cambiar el compile de AppCompActivity a otro, sincronizar y volver a ponerlo como originalmente estaba

### "Please select SDK"

Salta este error tras darle al ejecutar y saltar una ventana de configuración:
1. Ve a File -> Proyect Estructure -> app
2. Cambia el Compile SDK Version.
