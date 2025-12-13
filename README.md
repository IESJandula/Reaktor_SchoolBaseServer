# 📌 Módulo de gestión de espacios académicos.

Repositorio del proyecto para el módulo de gestión de espacios académicos.

Este servicio maneja las solicitudes HTTP relacionadas con la gestión de cursos académicos y espacios (sin docencia, fijos y desdobles) en una base de datos de motor MySQL Server. Permite crear y gestionar diferentes tipos de espacios académicos asociados a cursos académicos específicos.



## 📋 Tabla de Endpoints



### Cursos Académicos

<table>
   <tr>
      <th>METODO</th>
      <th>URL</th>
      <th>DESCRIPCION</th>
      <th>ROL REQUERIDO</th>
   </tr>
   <tr>
      <td>🟢 GET</td>
      <td>/school_base_server/admin/cursos_academicos</td>
      <td>Obtiene la lista de todos los cursos académicos disponibles en el sistema.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/cursos_academicos</td>
      <td>Selecciona un curso académico como activo. Deselecciona todos los demás cursos académicos.</td>
      <td>ADMINISTRADOR</td>
   </tr>
</table>



### Espacios Sin Docencia

<table>
   <tr>
      <th>METODO</th>
      <th>URL</th>
      <th>DESCRIPCION</th>
      <th>ROL REQUERIDO</th>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/espacios/sin_docencia</td>
      <td>Crea un nuevo espacio sin docencia. El espacio no debe existir previamente en ningún repositorio.</td>
      <td>ADMINISTRADOR</td>
   </tr>
</table>



### Espacios Fijos

<table>
   <tr>
      <th>METODO</th>
      <th>URL</th>
      <th>DESCRIPCION</th>
      <th>ROL REQUERIDO</th>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/espacios/fijo</td>
      <td>Crea un nuevo espacio fijo. El espacio no debe existir previamente en ningún repositorio.</td>
      <td>ADMINISTRADOR</td>
   </tr>
</table>



### Espacios Desdobles

<table>
   <tr>
      <th>METODO</th>
      <th>URL</th>
      <th>DESCRIPCION</th>
      <th>ROL REQUERIDO</th>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/espacios/desdoble</td>
      <td>Crea un nuevo espacio desdoble. El espacio no debe existir previamente en ningún repositorio.</td>
      <td>ADMINISTRADOR</td>
   </tr>
</table>



## 🔹 Requisitos de ejecución.

El servicio necesita una base de datos **MySQL** escuchando en el puerto **3306**. El esquema se crea automáticamente con el nombre "**reaktor_school_base_server**" si no existe. En el archivo de configuración del proyecto, `application.yaml`, se definen el nombre del esquema y las credenciales de acceso a la base de datos.



**Configuración actual:**

- **Puerto del servidor:** 8092

- **Base de datos:** reaktor_school_base_server

- **Puerto MySQL:** 3306

- **Usuario:** root (configurable en `application.yaml`)

- **Contraseña:** toor (configurable en `application.yaml`)



**Para crear un contenedor de forma rápida y sencilla que proporcione este servicio, utiliza el siguiente comando:**

```docker
docker run -d -p 3306:3306 --name mi_mysql -e MYSQL_ROOT_PASSWORD=toor -e MYSQL_DATABASE=reaktor_school_base_server mysql
```



**Nota:** El servicio utiliza autenticación basada en roles (ADMINISTRADOR) mediante JWT. Asegúrate de configurar correctamente las claves públicas en el archivo de configuración.



<br/>

<br/>



# 📌 Endpoints expuestos.

A continuación el listado de endpoints expuestos actualmente y los parámetros necesarios con una descripción de su comportamiento.



**Nota importante:** Todos los endpoints requieren autenticación mediante JWT. El token debe incluirse en el header `Authorization` con el formato `Bearer <token>`.



---



## 🔹 Endpoints de Cursos Académicos



### 🟢 GET - Obtener lista de cursos académicos

```
GET localhost:8092/school_base_server/admin/cursos_academicos
```

Endpoint que permite recuperar la lista completa de cursos académicos disponibles en el sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT



**Ejemplo de petición:**

```
GET localhost:8092/school_base_server/admin/cursos_academicos
Headers:
  Authorization: Bearer <token>
```



**Respuesta:** Lista de objetos `CursoAcademico` con la siguiente estructura:

```json
[
  {
    "cursoAcademico": "2025/2026",
    "seleccionado": true,
    "espaciosSinDocencia": [],
    "espaciosFijos": [],
    "espaciosDesdobles": []
  },
  {
    "cursoAcademico": "2026/2027",
    "seleccionado": false,
    "espaciosSinDocencia": [],
    "espaciosFijos": [],
    "espaciosDesdobles": []
  }
]
```



**Códigos de respuesta:**

- `200 OK`: Lista obtenida correctamente

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



### 🟡 POST - Seleccionar curso académico

```
POST localhost:8092/school_base_server/admin/cursos_academicos
```

Endpoint que permite seleccionar un curso académico como activo. Al seleccionar un curso académico, se deseleccionan automáticamente todos los demás cursos académicos.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT

- `cursoAcademico`: Curso académico a seleccionar (ej: "2025/2026")



**Ejemplo de petición:**

```
POST localhost:8092/school_base_server/admin/cursos_academicos
Headers:
  Authorization: Bearer <token>
  cursoAcademico: 2025/2026
```



**Respuesta:** 200 OK si el curso académico se selecciona correctamente.



**Códigos de respuesta:**

- `200 OK`: Curso académico seleccionado correctamente

- `400 Bad Request`: Solicitud incorrecta - El curso académico es nulo, vacío o no existe

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



**Nota:** El curso académico debe existir previamente en la base de datos. Si el curso académico no existe, se devolverá un error 400 con el mensaje "El curso académico no existe".



---



## 🔹 Endpoints de Espacios Sin Docencia



### 🟡 POST - Crear espacio sin docencia

```
POST localhost:8092/school_base_server/admin/espacios/sin_docencia
```

Endpoint que permite crear un nuevo espacio sin docencia. El espacio no debe existir previamente en ningún repositorio (sin docencia, fijo o desdoble).



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT

- `Content-Type`: application/json



**Body requerido (JSON):**

```json
{
  "cursoAcademico": "2025/2026",
  "nombre": "Aula 101"
}
```

**Campos del body:**

- `cursoAcademico` (String, requerido): Curso académico al que pertenece el espacio. Debe existir en la base de datos.

- `nombre` (String, requerido): Nombre del espacio. No puede ser nulo o vacío.



**Ejemplo de petición:**

```
POST localhost:8092/school_base_server/admin/espacios/sin_docencia
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "cursoAcademico": "2025/2026",
  "nombre": "Aula 101"
}
```



**Respuesta:** 200 OK si el espacio se crea correctamente.



**Códigos de respuesta:**

- `200 OK`: Espacio sin docencia creado correctamente

- `400 Bad Request`: Solicitud incorrecta - El espacio es inválido, ya existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "El nombre no puede ser nulo o vacío"
  - "El curso académico no existe"
  - "El espacio ya existe en sin docencia"
  - "El espacio ya existe en fijo"
  - "El espacio ya existe en desdoble"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



**Nota:** El sistema valida que el espacio no exista previamente en ninguno de los tres tipos de repositorios (sin docencia, fijo o desdoble) antes de crearlo.



---



## 🔹 Endpoints de Espacios Fijos



### 🟡 POST - Crear espacio fijo

```
POST localhost:8092/school_base_server/admin/espacios/fijo
```

Endpoint que permite crear un nuevo espacio fijo. El espacio no debe existir previamente en ningún repositorio (sin docencia, fijo o desdoble).



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT

- `Content-Type`: application/json



**Body requerido (JSON):**

```json
{
  "cursoAcademico": "2025/2026",
  "nombre": "Laboratorio Informática"
}
```

**Campos del body:**

- `cursoAcademico` (String, requerido): Curso académico al que pertenece el espacio. Debe existir en la base de datos.

- `nombre` (String, requerido): Nombre del espacio. No puede ser nulo o vacío.



**Ejemplo de petición:**

```
POST localhost:8092/school_base_server/admin/espacios/fijo
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "cursoAcademico": "2025/2026",
  "nombre": "Laboratorio Informática"
}
```



**Respuesta:** 200 OK si el espacio se crea correctamente.



**Códigos de respuesta:**

- `200 OK`: Espacio fijo creado correctamente

- `400 Bad Request`: Solicitud incorrecta - El espacio es inválido, ya existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "El nombre no puede ser nulo o vacío"
  - "El curso académico no existe"
  - "El espacio ya existe en sin docencia"
  - "El espacio ya existe en fijo"
  - "El espacio ya existe en desdoble"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



**Nota:** El sistema valida que el espacio no exista previamente en ninguno de los tres tipos de repositorios (sin docencia, fijo o desdoble) antes de crearlo.



---



## 🔹 Endpoints de Espacios Desdobles



### 🟡 POST - Crear espacio desdoble

```
POST localhost:8092/school_base_server/admin/espacios/desdoble
```

Endpoint que permite crear un nuevo espacio desdoble. El espacio no debe existir previamente en ningún repositorio (sin docencia, fijo o desdoble).



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT

- `Content-Type`: application/json



**Body requerido (JSON):**

```json
{
  "cursoAcademico": "2025/2026",
  "nombre": "Aula Desdoble 1"
}
```

**Campos del body:**

- `cursoAcademico` (String, requerido): Curso académico al que pertenece el espacio. Debe existir en la base de datos.

- `nombre` (String, requerido): Nombre del espacio. No puede ser nulo o vacío.



**Ejemplo de petición:**

```
POST localhost:8092/school_base_server/admin/espacios/desdoble
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "cursoAcademico": "2025/2026",
  "nombre": "Aula Desdoble 1"
}
```



**Respuesta:** 200 OK si el espacio se crea correctamente.



**Códigos de respuesta:**

- `200 OK`: Espacio desdoble creado correctamente

- `400 Bad Request`: Solicitud incorrecta - El espacio es inválido, ya existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "El nombre no puede ser nulo o vacío"
  - "El curso académico no existe"
  - "El espacio ya existe en sin docencia"
  - "El espacio ya existe en fijo"
  - "El espacio ya existe en desdoble"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



**Nota:** El sistema valida que el espacio no exista previamente en ninguno de los tres tipos de repositorios (sin docencia, fijo o desdoble) antes de crearlo.



---

