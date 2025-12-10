# AppNote

> **Proyecto Integrador - Desarrollo de Aplicaciones Móviles**
>
> **Semestre:** 4°F
> **Fecha de entrega:** 11 de Diciembre

---

## Equipo de Desarrollo

| Nombre Completo | Rol / Tareas Principales | Usuario GitHub |
| :--- | :--- | :--- |
| Sandoval Rueda Kevin Daniel | Logica, Repositorio, Backend | SanDaniel777 |
| Porcayo Cervantes Kevin Arturo | Sensores, Logica | R2D2PorcayoCK |
| Chacón Ayala Ángel Daniel | Sensores, Lógica | ADCAyala |

---

## Descripción del Proyecto

**¿Qué hace la aplicación?**
Nuestro proyecto es una app de notas dirigida para cualquier usuario. Su fin es resolver el problema de realizar alguna anotación de manera mas sencilla y a la mano, en esta se implemento el
sensor de cámara para que la nota este más complementada y el sensor del giroscopio el cual su función dentro de la app en mejorar la navegación de las notas dentro de la app.

**Objetivo:**
Demostrar la implementación de una arquitectura robusta en Android utilizando servicios web y hardware del dispositivo.

---

## Stack Tecnológico y Características

Este proyecto ha sido desarrollado siguiendo estrictamente los lineamientos de la materia:

* **Lenguaje:** Kotlin 100%.
* **Interfaz de Usuario:** Jetpack Compose.
* **Arquitectura:** MVVM (Model-View-ViewModel).
* **Conectividad (API REST):** Retrofit.
    * **GET:** Se obtienen todas las notas que esten guardadas junto con su contenido.
    * **POST:** Notas incluyendo el contenido de estas.
    * **UPDATE:** Se  actualiza el listado de las notas guardadas.
    * **DELETE:** Se borra la nota que el usuario desea.
* **Sensor Integrado:** Cámara, Giroscopio
    * *Uso:* Cámara: Agrega una fotografia dentro de la nota si es que el usuario asi lo requiera.
    * Giroscopio: Ayuda a la navegación de las notas dentro de la lista.

---

## Capturas de Pantalla

[Coloca al menos 3 (investiga como agregarlas y se vean en GitHub)]

| Pantalla de Inicio | Operación CRUD | Uso del Sensor |
| :---: | :---: | :---: |
| ![Inicio](url_imagen) | ![CRUD](url_imagen) | ![Sensor](url_imagen) |

---

## Instalación y Releases

El ejecutable firmado (.apk) se encuentra disponible en la sección de **Releases** de este repositorio.

[Liga correctamente tu link de releases en la siguiente sección]

1.  Ve a la sección "Releases" (o haz clic [aquí](link_a_tus_releases)).
2.  Descarga el archivo `.apk` de la última versión.
3.  Instálalo en tu dispositivo Android (asegúrate de permitir la instalación de orígenes desconocidos).
