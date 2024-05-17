# Agencia de Turismo API

## Descripción

Esta API proporciona servicios para la búsqueda y reserva de vuelos y hoteles para una agencia de turismo. Permite a los usuarios buscar vuelos y hoteles disponibles, así como realizar reservas de vuelos y hoteles.

## Endpoints

### Hoteles
- **Listar Hoteles (US 0001):**
    - Método: GET
    - Endpoint: /api/v1/listarHoteles
    - Descripción: Obtiene un listado de todos los hoteles registrados.

- **Hoteles Disponibles (US 0002):**
    - Método: GET
    - Endpoint: /api/v1/hotels
    - Descripción: Obtiene un listado de todos los hoteles disponibles en un determinado rango de fechas y según el destino seleccionado.

- **Reservar Hotel (US 0003):**
    - Método: POST
    - Endpoint: /api/v1/booking
    - Descripción: Realiza una reserva de hotel.

### Vuelos
- **Listar Vuelos (US 0004):**
    - Método: GET
    - Endpoint: /api/v1/listarvuelos
    - Descripción: Obtiene un listado de todos los vuelos registrados.

- **Vuelos Disponibles (US 0005):**
    - Método: GET
    - Endpoint: /api/v1/flights
    - Descripción: Obtiene un listado de todos los vuelos disponibles en un determinado rango de fechas y según el origen y destino seleccionados.

- **Reservar Vuelo (US 0006):**
    - Método: POST
    - Endpoint: /api/v1/flight-reservation
    - Descripción: Realiza una reserva de vuelo.

## Excepciones Implementadas

- **InvalidFlightReservationException:** Se lanza cuando no se encuentra ningún vuelo que coincida con los criterios de reserva.
- **InvalidReservationException:** Se lanza cuando se produce un error durante la reserva de un vuelo.
- **InvalidHotelReservationException:** Se lanza cuando no se encuentra ningún hotel que coincida con los criterios de reserva.
- **InvalidConsultDateException:** Se lanza cuando se proporciona una fecha de consulta incorrecta.

## Miembros del Equipo y Responsabilidades

- Agustina Peralta: ---------------------.
- Germán Poupeau: ------------------------.
- Matías Leglise:
- Joaquín Cassina:
- Juan Manuel Francesconi:
- Andrea Toledo:

## Decisiones de Grupo

Durante el desarrollo del proyecto, el equipo tomó las siguientes decisiones:

- Utilizar Spring Boot para el desarrollo de la API debido a su facilidad de configuración y su amplia integración con Spring Framework.
- Implementar un controlador de excepciones global (ExecptionController) para manejar las excepciones de manera centralizada y devolver respuestas HTTP adecuadas.

## Ubicación de la Colección en el Proyecto

La colección de la API se encuentra en el directorio collections en la raíz del proyecto.