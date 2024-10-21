# Proyecto de Firma Digital

Este proyecto proporciona una API REST para firmar y verificar documentos digitalmente utilizando claves públicas y privadas. Está desarrollado en Java usando Spring Boot y se conecta a una base de datos MySQL.

## Requisitos

- **Java 21 o superior**
- **Maven**
- **MySQL** (o cualquier otra base de datos que elijas)
- **Spring Boot** (las dependencias se manejan a través de Maven)

## Instalación

1. **Clonar el repositorio**:
    ```bash
   git clone https://github.com/Clarobollo/Docuten
   cd Docuten/demo

2. **Configurar la base de datos**:
   - Crea una base de datos en MySQL con el archivo users.sql y ajusta las credenciales en el archivo `src/main/resources/application.properties`.

   ```properties
   bbdd.url=jdbc:mysql://localhost:3306/demo
   bbdd.username=root
   bbdd.password= 

3. **Ejecutar el proyecto**:
   - Puedes ejecutar el proyecto usando Maven:

   ```bash
   mvn spring-boot:run
  
## Endpoints de la API

### 1. Generar un nuevo par de claves

- **Endpoint**: `v1/keys/request`
- **Método**: GET
- **Descripción**: Genera un nuevo par de claves (pública y privada) para un usuario.
- **Headers**:
- `user`: nombre del usuario
- `pass`: contraseña del usuario

- **Ejemplo de uso**:

```bash
curl --request GET \
--url http://localhost:8083/v1/keys/request \
--header 'pass: hulolo' \
--header 'user: hulolo'
```

**Respuesta**:

```json
{
	"status": "OK",
	"message": "Keys generated successfully"
}
```

### 2. Firmar un documento

- **Endpoint**: `v1/sign/document`
- **Método**: POST
- **Descripción**: Firma un documento usando la clave privada asociada al usuario.
- **Headers**:
- `Content-Type`: text/plain
- `user`: nombre del usuario
- `pass`: contraseña del usuario
- **Cuerpo de la solicitud**: El cuerpo debe contener el documento en base64 como texto plano.

- **Ejemplo de uso**:

```bash
curl --request POST \
--url http://localhost:8083/v1/sign/document \
--header 'Content-Type: text/plain' \
--header 'pass: hulolo' \
--header 'user: hulolo' \
--data 'documento en base64' 
```

**Respuesta**:

```json
{
	"status": "OK",
	"message": "Document signed successfully",
	"signature": "Firma"
}
```

### 3. Verificar una firma

- **Endpoint**: `v1/sign/verify `
- **Método**: POST
- **Descripción**: Verifica una firma digital utilizando la clave pública asociada al usuario.
- **Headers**:
- `Content-Type`: application/json
- `user`: nombre del usuario
- `pass`: contraseña del usuario
- **Cuerpo de la solicitud**:
  ```json
  {
    "document": "DOCUMENTO_ORIGINAL_EN_BASE64",
    "signature": "FIRMA_EN_BASE64"
  }

- **Ejemplo de uso**:

```bash
curl --request POST \
--url http://localhost:8083/v1/sign/verify \
--header 'Content-Type: application/json' \
--header 'pass: hulolo' \
--header 'user: hulolo' \
--data '{
    "document": "documento base64",
    "signature": "firma"
}'

```

**Respuesta**:

```json
{
	"status": "OK",
	"message": "Signature is valid"
}
```


## Notas

- Asegúrate de tener el servidor de base de datos en funcionamiento y que la base de datos esté configurada correctamente.
- Este proyecto está destinado para fines de demostración y puede no ser adecuado para entornos de producción sin las modificaciones y las pruebas adecuadas.

