# 🧠📱 Sistema de Apoyo para Personas con Síndrome de Asperger

Plataforma móvil diseñada para apoyar a personas con **síndrome de Asperger**, facilitando la gestión de actividades diarias, el seguimiento de tareas y la interacción con tutores mediante un sistema intuitivo y accesible.

---

## 📌 Descripción

Este proyecto presenta un sistema informático enfocado en mejorar la **organización, autonomía y seguimiento** de personas con síndrome de Asperger.

El sistema está compuesto por dos aplicaciones móviles Android:

- 👨‍🏫 **App Tutor** → Para profesionales o docentes  
- 👤 **App Usuario** → Para personas con Asperger  

Ambas aplicaciones trabajan de forma sincronizada, permitiendo asignar tareas, dar seguimiento al progreso y reforzar hábitos mediante recordatorios y retroalimentación.

---

## 🎯 Objetivo

Brindar una herramienta tecnológica que facilite:

- 🧩 La organización de actividades diarias  
- ⏰ Recordatorios de tareas importantes  
- 📊 Seguimiento del progreso del usuario  
- 🤝 Comunicación entre tutor y usuario  

---

## 🚀 Características

### 👨‍🏫 Aplicación Tutor
- Gestión de alumnos  
- Creación de tareas, eventos y retos  
- Seguimiento del progreso  
- Administración centralizada  

### 👤 Aplicación Usuario
- Recordatorios personalizados  
- Interfaz simple y accesible  
- Confirmación de tareas realizadas  
- Personalización según edad y necesidades  

---

## 🧠 Enfoque de Diseño

- 🎨 Interfaces basadas en **claridad y simplicidad**
- 🧩 Adaptabilidad para diferentes edades
- 💡 Enfoque centrado en el usuario
- 📱 Diseño basado en **Material Design**

---

## 🔄 Comunicación del Sistema

- 📡 Sincronización mediante **Sockets**
- 📶 Conexión vía **WiFi**
- 💾 Almacenamiento local en cada dispositivo

Esto permite mantener un sistema funcional incluso sin conexión a internet, garantizando disponibilidad y eficiencia.

---

## 🏗️ Arquitectura

El sistema está desarrollado bajo una **arquitectura multicapa**, lo que permite:

- 🔄 Reusabilidad  
- ⚡ Escalabilidad  
- 🔐 Encapsulación  
- 📊 Mejor rendimiento  
- 🧩 Separación de responsabilidades  

---

### 🧩 Capas del sistema

- 🎨 Presentación  
- ⚙️ Lógica de negocio  
- 🔗 Integración  

---

### 📐 Patrones de diseño utilizados

- Singleton  
- Dispatcher  
- Command  
- Factoría Abstracta  
- Servicio de Aplicación  
- DAO (Data Access Object)  
- DTO (Data Transfer Object)  

---

## 🖼️ Arquitectura general

<p align="center">
  <img src="https://s11.postimg.org/w1rdtpeib/esquema_general_intro.png" />
</p>

---

## 🏗️ Arquitectura multicapa

<p align="center">
  <img src="https://s11.postimg.org/pt6204gxf/arq_multicapa.png" />
</p>

---

## 🔄 Flujo del sistema

<p align="center">
  <img src="https://s11.postimg.org/sjfdxbdmb/arq_multi_AS.png" />
</p>

---

## 📊 Modelo de datos (App Tutor)

<p align="center">
  <img src="https://s11.postimg.org/mxsyt9cxf/as_tutor_bdd.png" />
</p>

---

## 💡 Funcionamiento

1. 👨‍🏫 El tutor crea tareas, eventos o retos  
2. 📤 Se sincronizan con el dispositivo del usuario  
3. 🔔 El usuario recibe recordatorios  
4. ✅ Confirma la realización de actividades  
5. 📊 El tutor visualiza el progreso  

---

## 📈 Beneficios

- 🧠 Mejora la autonomía del usuario  
- ⏰ Refuerza hábitos y rutinas  
- 📊 Facilita el seguimiento educativo  
- 🤝 Fortalece la relación tutor–usuario  

---

## ⚠️ Nota

Este sistema está diseñado como una herramienta de apoyo y **no sustituye el acompañamiento profesional especializado**.

---

## 📄 Licencia

Proyecto de uso educativo y académico.

---

## 👨‍💻 Autor

Adaptado y presentado por **Isai Reyes Peña**
