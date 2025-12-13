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



### Cursos, Etapas y Grupos

<table>
   <tr>
      <th>METODO</th>
      <th>URL</th>
      <th>DESCRIPCION</th>
      <th>ROL REQUERIDO</th>
   </tr>
   <tr>
      <td>🟢 GET</td>
      <td>/school_base_server/admin/cursos_etapas_grupos</td>
      <td>Obtiene la lista de todos los cursos, etapas y grupos disponibles en el sistema.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/cursos_etapas_grupos</td>
      <td>Crea un nuevo curso, etapa y grupo. El curso, etapa y grupo no debe existir previamente.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🔴 DELETE</td>
      <td>/school_base_server/admin/cursos_etapas_grupos</td>
      <td>Elimina un curso, etapa y grupo específico del sistema.</td>
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
      <td>🟢 GET</td>
      <td>/school_base_server/admin/espacios/sin_docencia</td>
      <td>Obtiene la lista de todos los espacios sin docencia disponibles en el sistema.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/espacios/sin_docencia</td>
      <td>Crea un nuevo espacio sin docencia. El espacio no debe existir previamente en ningún repositorio.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🔴 DELETE</td>
      <td>/school_base_server/admin/espacios/sin_docencia</td>
      <td>Elimina un espacio sin docencia específico del sistema.</td>
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
      <td>🟢 GET</td>
      <td>/school_base_server/admin/espacios/fijo</td>
      <td>Obtiene la lista de todos los espacios fijos disponibles en el sistema.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/espacios/fijo</td>
      <td>Crea un nuevo espacio fijo. El espacio no debe existir previamente en ningún repositorio.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🔴 DELETE</td>
      <td>/school_base_server/admin/espacios/fijo</td>
      <td>Elimina un espacio fijo específico del sistema.</td>
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
      <td>🟢 GET</td>
      <td>/school_base_server/admin/espacios/desdoble</td>
      <td>Obtiene la lista de todos los espacios desdobles disponibles en el sistema.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🟡 POST</td>
      <td>/school_base_server/admin/espacios/desdoble</td>
      <td>Crea un nuevo espacio desdoble. El espacio no debe existir previamente en ningún repositorio.</td>
      <td>ADMINISTRADOR</td>
   </tr>
   <tr>
      <td>🔴 DELETE</td>
      <td>/school_base_server/admin/espacios/desdoble</td>
      <td>Elimina un espacio desdoble específico del sistema.</td>
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



**Respuesta:** Lista de objetos `CursoAcademicoDto` con la siguiente estructura:

```json
[
  {
    "cursoAcademico": "2025/2026",
    "seleccionado": true
  },
  {
    "cursoAcademico": "2026/2027",
    "seleccionado": false
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



## 🔹 Endpoints de Cursos, Etapas y Grupos



### 🟢 GET - Obtener lista de cursos, etapas y grupos

```
GET localhost:8092/school_base_server/admin/cursos_etapas_grupos
```

Endpoint que permite recuperar la lista completa de cursos, etapas y grupos disponibles en el sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT



**Ejemplo de petición:**

```
GET localhost:8092/school_base_server/admin/cursos_etapas_grupos
Headers:
  Authorization: Bearer <token>
```



**Respuesta:** Lista de objetos `CursoEtapaGrupoDto` con la siguiente estructura:

```json
[
  {
    "curso": 1,
    "etapa": "ESO",
    "grupo": "A"
  },
  {
    "curso": 1,
    "etapa": "ESO",
    "grupo": "B"
  }
]
```



**Códigos de respuesta:**

- `200 OK`: Lista obtenida correctamente

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



### 🟡 POST - Crear curso, etapa y grupo

```
POST localhost:8092/school_base_server/admin/cursos_etapas_grupos
```

Endpoint que permite crear un nuevo curso, etapa y grupo. El curso, etapa y grupo no debe existir previamente en el sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT

- `Content-Type`: application/json



**Body requerido (JSON):**

```json
{
  "curso": 1,
  "etapa": "ESO",
  "grupo": "A"
}
```

**Campos del body:**

- `curso` (Integer, requerido): Número del curso. No puede ser nulo.

- `etapa` (String, requerido): Etapa educativa (ej: "ESO", "BACHILLERATO"). No puede ser nulo o vacío.

- `grupo` (String, requerido): Grupo del curso (ej: "A", "B", "C"). No puede ser nulo o vacío.



**Ejemplo de petición:**

```
POST localhost:8092/school_base_server/admin/cursos_etapas_grupos
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "curso": 1,
  "etapa": "ESO",
  "grupo": "A"
}
```



**Respuesta:** 200 OK si el curso, etapa y grupo se crea correctamente.



**Códigos de respuesta:**

- `200 OK`: Curso, etapa y grupo creado correctamente

- `400 Bad Request`: Solicitud incorrecta - El curso, etapa y grupo es inválido o ya existe. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "La etapa no puede ser nula o vacía"
  - "El grupo no puede ser nulo o vacío"
  - "El curso, etapa y grupo ya existe"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



### 🔴 DELETE - Eliminar curso, etapa y grupo

```
DELETE localhost:8092/school_base_server/admin/cursos_etapas_grupos
```

Endpoint que permite eliminar un curso, etapa y grupo específico del sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT

- `Content-Type`: application/json



**Body requerido (JSON):**

```json
{
  "curso": 1,
  "etapa": "ESO",
  "grupo": "A"
}
```

**Campos del body:**

- `curso` (Integer, requerido): Número del curso a eliminar.

- `etapa` (String, requerido): Etapa educativa a eliminar.

- `grupo` (String, requerido): Grupo del curso a eliminar.



**Ejemplo de petición:**

```
DELETE localhost:8092/school_base_server/admin/cursos_etapas_grupos
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "curso": 1,
  "etapa": "ESO",
  "grupo": "A"
}
```



**Respuesta:** 204 No Content si el curso, etapa y grupo se elimina correctamente.



**Códigos de respuesta:**

- `204 No Content`: Curso, etapa y grupo eliminado correctamente

- `400 Bad Request`: Solicitud incorrecta - El curso, etapa y grupo no existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "La etapa no puede ser nula o vacía"
  - "El grupo no puede ser nulo o vacío"
  - "El curso, etapa y grupo no existe"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



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



### 🟢 GET - Obtener lista de espacios sin docencia

```
GET localhost:8092/school_base_server/admin/espacios/sin_docencia
```

Endpoint que permite recuperar la lista completa de espacios sin docencia disponibles en el sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT



**Ejemplo de petición:**

```
GET localhost:8092/school_base_server/admin/espacios/sin_docencia
Headers:
  Authorization: Bearer <token>
```



**Respuesta:** Lista de objetos `EspacioSinDocenciaDto` con la siguiente estructura:

```json
[
  {
    "cursoAcademico": "2025/2026",
    "nombre": "Aula 101"
  },
  {
    "cursoAcademico": "2025/2026",
    "nombre": "Aula 102"
  }
]
```



**Códigos de respuesta:**

- `200 OK`: Lista obtenida correctamente

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



### 🔴 DELETE - Eliminar espacio sin docencia

```
DELETE localhost:8092/school_base_server/admin/espacios/sin_docencia
```

Endpoint que permite eliminar un espacio sin docencia específico del sistema.



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

- `cursoAcademico` (String, requerido): Curso académico del espacio a eliminar.

- `nombre` (String, requerido): Nombre del espacio a eliminar.



**Ejemplo de petición:**

```
DELETE localhost:8092/school_base_server/admin/espacios/sin_docencia
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "cursoAcademico": "2025/2026",
  "nombre": "Aula 101"
}
```



**Respuesta:** 204 No Content si el espacio se elimina correctamente.



**Códigos de respuesta:**

- `204 No Content`: Espacio sin docencia eliminado correctamente

- `400 Bad Request`: Solicitud incorrecta - El espacio no existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "El nombre no puede ser nulo o vacío"
  - "El curso académico no existe"
  - "El espacio no existe en sin docencia"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



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



### 🟢 GET - Obtener lista de espacios fijos

```
GET localhost:8092/school_base_server/admin/espacios/fijo
```

Endpoint que permite recuperar la lista completa de espacios fijos disponibles en el sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT



**Ejemplo de petición:**

```
GET localhost:8092/school_base_server/admin/espacios/fijo
Headers:
  Authorization: Bearer <token>
```



**Respuesta:** Lista de objetos `EspacioFijoDto` con la siguiente estructura:

```json
[
  {
    "cursoAcademico": "2025/2026",
    "nombre": "Laboratorio Informática"
  },
  {
    "cursoAcademico": "2025/2026",
    "nombre": "Laboratorio Física"
  }
]
```



**Códigos de respuesta:**

- `200 OK`: Lista obtenida correctamente

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



### 🔴 DELETE - Eliminar espacio fijo

```
DELETE localhost:8092/school_base_server/admin/espacios/fijo
```

Endpoint que permite eliminar un espacio fijo específico del sistema.



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

- `cursoAcademico` (String, requerido): Curso académico del espacio a eliminar.

- `nombre` (String, requerido): Nombre del espacio a eliminar.



**Ejemplo de petición:**

```
DELETE localhost:8092/school_base_server/admin/espacios/fijo
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "cursoAcademico": "2025/2026",
  "nombre": "Laboratorio Informática"
}
```



**Respuesta:** 204 No Content si el espacio se elimina correctamente.



**Códigos de respuesta:**

- `204 No Content`: Espacio fijo eliminado correctamente

- `400 Bad Request`: Solicitud incorrecta - El espacio no existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "El nombre no puede ser nulo o vacío"
  - "El curso académico no existe"
  - "El espacio no existe en fijo"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



## 🔹 Endpoints de Espacios Desdobles



### 🟢 GET - Obtener lista de espacios desdobles

```
GET localhost:8092/school_base_server/admin/espacios/desdoble
```

Endpoint que permite recuperar la lista completa de espacios desdobles disponibles en el sistema.



**Rol requerido:** ADMINISTRADOR



**Headers requeridos:**

- `Authorization`: Bearer token JWT



**Ejemplo de petición:**

```
GET localhost:8092/school_base_server/admin/espacios/desdoble
Headers:
  Authorization: Bearer <token>
```



**Respuesta:** Lista de objetos `EspacioDesdobleDto` con la siguiente estructura:

```json
[
  {
    "cursoAcademico": "2025/2026",
    "nombre": "Aula Desdoble 1"
  },
  {
    "cursoAcademico": "2025/2026",
    "nombre": "Aula Desdoble 2"
  }
]
```



**Códigos de respuesta:**

- `200 OK`: Lista obtenida correctamente

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---



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



### 🔴 DELETE - Eliminar espacio desdoble

```
DELETE localhost:8092/school_base_server/admin/espacios/desdoble
```

Endpoint que permite eliminar un espacio desdoble específico del sistema.



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

- `cursoAcademico` (String, requerido): Curso académico del espacio a eliminar.

- `nombre` (String, requerido): Nombre del espacio a eliminar.



**Ejemplo de petición:**

```
DELETE localhost:8092/school_base_server/admin/espacios/desdoble
Headers:
  Authorization: Bearer <token>
  Content-Type: application/json
Body:
{
  "cursoAcademico": "2025/2026",
  "nombre": "Aula Desdoble 1"
}
```



**Respuesta:** 204 No Content si el espacio se elimina correctamente.



**Códigos de respuesta:**

- `204 No Content`: Espacio desdoble eliminado correctamente

- `400 Bad Request`: Solicitud incorrecta - El espacio no existe o los datos son incorrectos. Posibles errores:
  - "El curso académico no puede ser nulo o vacío"
  - "El nombre no puede ser nulo o vacío"
  - "El curso académico no existe"
  - "El espacio no existe en desdoble"

- `401 Unauthorized`: No autorizado - Se requiere autenticación

- `403 Forbidden`: Prohibido - Se requiere rol de administrador

- `500 Internal Server Error`: Error interno del servidor



---

