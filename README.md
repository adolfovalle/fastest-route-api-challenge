# Desafío Técnico: API de Ruta Más Rápida

Esta es una implementación de una API REST utilizando Java y Spring Boot para calcular la ruta más rápida entre dos ubicaciones.

## Características

-   **API RESTful:** Expone dos endpoints principales:
    -   `POST /api/routes/upload`: Para cargar una red de rutas desde un archivo CSV.
    -   `GET /api/routes/fastest`: Para obtener la ruta más rápida y el tiempo total de viaje entre un origen y un destino.
-   **Algoritmo Eficiente:** Utiliza el Algoritmo de Dijkstra para el cálculo de la ruta más corta, con un grafo en memoria representado por un Mapa de Adyacencia para un rendimiento óptimo.
-   **Pruebas Unitarias:** Incluye un conjunto de pruebas unitarias con JUnit para validar la correcta implementación del algoritmo.
-   **Contenerización:** Se proporciona un `Dockerfile` para empaquetar y ejecutar la aplicación de forma aislada y portable.

## Stack Tecnológico

-   **Lenguaje:** Java 21
-   **Framework:** Spring Boot 3.x
-   **Sistema de Construcción:** Maven
-   **Librerías Clave:** Spring Web, OpenCSV, Lombok
-   **Testing:** JUnit 5
-   **Contenerización:** Docker

## Requisitos Previos

-   JDK 21 o superior.
-   Apache Maven 3.6+.
-   Docker Desktop (para correr en contenedor).

## Cómo Ejecutar Localmente

1.  **Clonar el Repositorio:**
    ```bash
    git clone [URL-DE-TU-REPO]
    cd fastest-route-api
    ```

2.  **Ejecutar la Aplicación:**
    Puedes correr la aplicación directamente usando el wrapper de Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
    La API estará disponible en `http://localhost:8081`.

3.  **Ejecutar las Pruebas:**
    Para correr las pruebas unitarias, ejecuta:
    ```bash
    ./mvnw test
    ```

## Cómo Ejecutar con Docker

1.  **Construir la Imagen de Docker:**
    Desde la raíz del proyecto, ejecuta:
    ```bash
    docker build -t fastest-route-api .
    ```

2.  **Ejecutar el Contenedor:**
    ```bash
    docker run -p 8081:8081 fastest-route-api
    ```
    La API estará disponible en `http://localhost:8081`.

## Uso de la API

El archivo de rutas a cargar debe ser un CSV separado por punto y coma (;) con tres columnas. Se asume que la primera línea del archivo es una cabecera y será ignorada.

...
### 1. Cargar Rutas (Upload)

-   **Endpoint:** `POST /api/routes/upload`
-   **Método:** `POST`
-   **Body:** `form-data` con una clave `file` que contenga tu archivo `rutas.csv`.

### 2. Obtener Ruta Más Rápida (Fastest Route)

-   **Endpoint:** `GET /api/routes/fastest`
-   **Método:** `GET`
-   **Query Params:**
    -   `start`: Nombre de la ubicación de origen.
    -   `end`: Nombre de la ubicación de destino.
-   **Ejemplo:** `http://localhost:8081/api/routes/fastest?start=CP1&end=R20`