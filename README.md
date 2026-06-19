# 📦 Sistema de Gestión de Pedidos – Spring Boot

## 📘 Descripción del Proyecto

Este proyecto implementa una **API REST para la gestión de pedidos**, permitiendo:

- Registrar pedidos con uno o más productos
- Validar cliente existente
- Validar stock disponible
- Calcular correctamente el total del pedido
- Consultar pedidos por ID
- Consultar pedidos por cliente
- Devolver respuestas estandarizadas usando `BaseResponse`

El sistema está desarrollado con **Spring Boot**, siguiendo buenas prácticas de **arquitectura en capas**, **DTOs**, **mappers con lógica de negocio**, **manejo de errores** y **pruebas unitarias obligatorias**.

---

## 👨‍🎓 Datos del Alumno

- **Alumno:** Gianfranco Montalvo
- **Curso:** Full Stack Java Developer
- **Evaluación:** Examen Práctico – API REST con Spring Boot

---

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- Maven
- Oracle / PostgreSQL
- Lombok
- JUnit 5
- Mockito
- Swagger OpenAPI

---


## 📄 BaseResponse Obligatorio

Todas las respuestas de la API retornan la siguiente estructura:

```java
public class BaseResponse<T> {
    private Integer codigo;
    private String mensaje;
    private T objeto;
}
```

## ✅ Ejemplo de respuesta exitosa

```json
{
  "codigo": 200,
  "mensaje": "Pedido registrado exitosamente",
  "objeto": {
    "id": 1,
    "clienteId": 123,
    "total": 150.00,
    "productos": [
      {
        "productoId": 456,
        "cantidad": 2,
        "precioUnitario": 75.00
      }
    ]
  }
}
```

## ❌ Ejemplo de respuesta con error

```json
{
  "codigo": 400,
  "mensaje": "Cliente no encontrado",
  "objeto": null
}
```

## ▶️ Cómo Ejecutar el Proyecto

1. Clonar el repositorio
   git clone https://github.com/Gianmc2000/Sistema-Gestion-Reserva-Salas.git
2. Configurar la base de datos en `application.properties`
3. Ejecutar la aplicación con Maven
   mvn spring-boot:run
4. Acceder a la API en `http://localhost:8080/api/pedidos`
5. Probar los endpoints usando Postman o Swagger UI
6. Ejecutar pruebas unitarias con
   mvn test
7. Revisar la documentación de la API en `http://localhost:8080/swagger-ui.html`
8. ¡Listo para gestionar tus pedidos!