# Reproductor de listas podcasts remotas

Aplicación ***Android*** que lee, muestra y reproduce listas *m3u*, *RSS de blogs* y *podcasts* extraídos de *feeds* de internet.

Implementa funcionalidades para **leer y reproducir** los recursos que contienen dichos feeds.

<p align="center">
<img src="doc/1.png" width="220" alt="">
<img src="doc/2.png" width="220" alt="">
<img src="doc/3.png" width="220" alt="">
<img src="doc/4.png" width="220" alt="">
<img src="doc/5.png" width="220" alt="">
</p>

- - - -

# Navegación
<br>

> [:octocat: Repositorio GitHub](https://github.com/Monroy2DAM/android-feed-reader)<br>
> [:scroll: Mapa de versiones](https://github.com/Monroy2DAM/android-feed-reader/network)<br>
> [:arrow_down: Releases](https://github.com/Monroy2DAM/android-feed-reader/releases)<br>
> [:memo: Tareas y mejoras propuestas](https://github.com/Monroy2DAM/android-feed-reader/issues)<br>
> [:blue_book: Guía de Git](https://github.com/ismenc/seminario-git)<br>


- - - -

# ¿Cómo colaborar?

<details>
     <summary>Instrucciones</summary>
 <p>
  <!-- alternative placement of p shown above -->

  1. :v: Haz un fork del repositorio y posteriormente descárgalo `git clone <url>`.
  2. :open_file_folder: Navega a la carpeta del proyecto `cd <carpeta>`.
  3. :memo: Crea una nueva rama `git checkout -b <nombre-rama>` y luego haz tus cambios.
  4. :sparkles: Añade los cambios `git add *` y haz el commit: `git commit -am "Resumen de cambios"`.
  5. :arrow_up: Sube la nueva rama: `git push origin <nombre-rama>`.
  6. :email: Inicia un pull request en el repositorio :D

:octocat: Si deseas aprender Git, visita mi [guía sobre Git](https://github.com/ismenc/seminario-git).
      </p></details>

- - - -

# Índice
<br>

* Índice de contenidos
  * [1. Funcionamiento](#1-funcionamiento-de-la-aplicación)
  * [2. Documentación](#2-documentación)
  * [3. Fallos conocidos](#3-fallos-conocidos)
  * [4. Colaboradores](#3-Colaboradores)

- - - -
<br>


## 1. Funcionamiento de la aplicación

1. La aplicación carga y muestra al usuario un selector de feeds guardados.
2. Cuando el usuario selecciona un feed, se carga una lista mostrando la información básica de cada entrada.
3. Cuando el usuario selecciona una entrada, se muestra la información detallada de esa entrada en una nueva pantalla.
4. También mostrará las imágenes que contenga y botones para reproducir su stream y navegar a la fuente.


## 2. Documentación

El proyecto se estructura en varios paquetes:

[:open_file_folder: feedreader/](app/src/main/java/com/example/ismael/feedreader/)

   > Paquete con las *activities* e *hilos*.

[:open_file_folder: feedreader/adaptadores/](app/src/main/java/com/example/ismael/feedreader/adaptadores/)

   > Paquete con parseadores y adaptadores de pantalla y datos.

[:open_file_folder: feedreader/modelo/](app/src/main/java/com/example/ismael/feedreader/modelo/)

   > Paquete con las clases modelo de los objetos empleados.

* Se ha empleado un buen estilo de programación para que el código sea **legible y escalable**:
  * Uso de comentarios explicativos y todo/fixme.
  * Uso de expresiones Lambda.
  * Modularización: métodos y clases simples.
* Se ha pensado en el usuario:
  * Capturación de errores.
  * Se ha pensado en posibles errores de otros feeds para que la app no se cierre.
  * Se ha intentado capturar toda la casuística de posibles formato de XML y M3U.

## 3. Fallos conocidos

Errores conocidos y sus causas o soluciones

### Cannot resolve AppCompActivity

Las siguientes opciones pueden solucionarlo:

* Clean/Build/Rebuild project.
* File -> Invalidate Caches / Restart
* En gradle, cambiar el compile de AppCompActivity a otro, sincronizar y volver a ponerlo como originalmente estaba

### "Please select SDK"

Posible solución:

   > File -> Project structure... -> Project -> Cambia JDK location al directorio de instalación de Java JDK
   
## 4. Colaboradores

Colaborador | Aporte
------------ | ------------
[Ismael Núñez](https://github.com/ismenc) | Mayoría de la app
[Francisco Rodríguez](https://github.com/KerosenoDev) | SaxParser
