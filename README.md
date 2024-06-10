# Agencia de Turismo API

## Descripción

Esta API proporciona servicios para la búsqueda y reserva de vuelos y hoteles para una agencia de turismo. Permite a los usuarios buscar vuelos y hoteles disponibles, así como realizar reservas de vuelos y hoteles.

## Endpoints

### Hoteles
- **Listar Hoteles (US 0001):**
    - Método: GET
    - Endpoint: /api/v1/listHotels
    - Descripción: Obtiene un listado de todos los hoteles registrados.

- **Hoteles Disponibles (US 0002):**
    - Método: GET
      - Endpoint: /api/v1/hotels
    - Descripción: Obtiene un listado de todos los hoteles disponibles en un determinado rango de fechas y según el destino seleccionado.

- **Reservar Hotel (US 0003):**
    - Método: POST
    - Endpoint: /api/v1/booking
    - Descripción: Realiza una reserva de hotel.

- **Hoteles Reservados (Extra):**
  - Método: GET
  - Endpoint: /api/v1/reservedHotels
  - Descripción: Muestra las reservas creadas de hotel.

### Vuelos
- **Listar Vuelos (US 0004):**
    - Método: GET
    - Endpoint: /api/v1/listFlights
    - Descripción: Obtiene un listado de todos los vuelos registrados.

- **Vuelos Disponibles (US 0005):**
    - Método: GET
    - Endpoint: /api/v1/flights
    - Descripción: Obtiene un listado de todos los vuelos disponibles en un determinado rango de fechas y según el origen y destino seleccionados.

- **Reservar Vuelo (US 0006):**
    - Método: POST
    - Endpoint: /api/v1/flight-reservation
    - Descripción: Realiza una reserva de vuelo.

- **Vuelos Reservados (Extra):**
  - Método: GET
  - Endpoint: /api/v1/reservedFlights
  - Descripción: Muestra las reservas creadas de vuelos.

# <p align="center">CRUD</p>
- **CREATE:**
- @PostMapping("/createHotel") (Crear Hotel).
- @PostMapping("/createFlight") (Crear vuelo).
- **UPDATE:**
-  @PutMapping("/updateHotel/{hotelCode}") (Actualizar hotel).
-  @PutMapping("/updateFlight/{flightCode}") (Actualizar vuelo).
- **DELETE:**
- @DeleteMapping("/deleteHotel/{hotelCode}") (Borrar hotel).
- @DeleteMapping("/deleteFlight/{flightCode}") (Borrar vuelo).

## Excepciones Implementadas

- **InvalidFlightReservationException:** Se lanza cuando no se encuentra ningún vuelo que coincida con los criterios de reserva proporcionados. Esto puede ocurrir cuando las fechas, origen o destino no coinciden con ningún vuelo disponible.
- **InvalidReservationException:** Se lanza cuando se produce un error durante la reserva de un vuelo  o un hotel. Por ejemplo, cuando intentan reservar una oferta ya reservada, proporcionar un número de cuotas de pago no válido o no encontrar un hotel o vuelo que coincida con los criterios de búsqueda..
- **InvalidHotelReservationException:** Se lanza cuando no se encuentra ningún hotel que coincida con los criterios de reserva proporcionados. Esto puede suceder cuando las fechas o el destino no coinciden con ningún hotel disponible.
- **InvalidConsultDateException:** Se lanza cuando se proporciona una fecha de consulta incorrecta, como una fecha en un formato no válido o una fecha de finalización anterior a la fecha de inicio.
- **IllegalArgumentException:** Se lanza devolviendo al cliente una respuesta de formato DTO. 

## Miembros del Equipo y Responsabilidades

- Agustina Peralta: Implementación de la funcionalidad para la reserva de hoteles (US 0003), incluyendo la validación de datos de entrada y el manejo de excepciones específicas.
- Germán Poupeau: Implementación de la funcionalidad para listar todos los hoteles registrados (US 0001), asegurando que se obtengan y devuelvan correctamente los datos de los hoteles.
- Matías Leglise: Implementación de la funcionalidad para realizar reservas de vuelos (US 0006), incluyendo la lógica de negocio y la validación de reservas.
- Joaquín Cassina: Implementación de la funcionalidad para listar vuelos disponibles (US 0005), manejando la búsqueda por fechas y destinos.
- Juan Manuel Francesconi: Implementación de la funcionalidad para listar todos los vuelos registrados (US 0004), garantizando la correcta recuperación y formato de los datos de vuelos.
- Andrea Toledo: Implementación de la funcionalidad para buscar hoteles disponibles en un rango de fechas y destino específico (US 0002), gestionando la lógica de búsqueda y filtrado.

## Decisiones de Grupo

Durante el desarrollo del proyecto, el equipo tomó las siguientes decisiones:

- Utilizar Spring Boot para el desarrollo de la API debido a su facilidad de configuración y su amplia integración con Spring Framework.
- Implementar un controlador de excepciones global (ExecptionController) para manejar las excepciones de manera centralizada y devolver respuestas HTTP adecuadas.

## Ubicación de la Colección en el Proyecto

La colección de la API se encuentra en el directorio collections en la raíz del proyecto.

## Validaciones

### Hoteles

#### US 0002: Obtener un listado de todos los hoteles disponibles
#### US 0005: Obtener un listado de todos los vuelos disponibles

**Parámetros:**

- **Fecha Entrada:**
  - **Validaciones:**
    - Formato correcto (dd-mm-aaaa).
    - La fecha de entrada debe ser menor a la de salida.
  - **Mensajes de error:**
    - "Formato de fecha debe ser dd-mm-aaaa".
    - "La fecha de entrada debe ser menor a la de salida".

- **Fecha Salida:**
  - **Validaciones:**
    - Formato correcto (dd-mm-aaaa).
    - La fecha de salida debe ser mayor a la de entrada.
  - **Mensajes de error:**
    - "Formato de fecha debe ser dd-mm-aaaa".
    - "La fecha de salida debe ser mayor a la de entrada".

**Se creó el método dateValid, que verifica que la fecha de entrada sea menor a la de salida**

- **Destino:**
  - **Validaciones:**
    - El destino debe existir.
  - **Mensaje de error:**
    - "El destino elegido no existe"
      **Se creó un método que valida que el destino enviado exista.**
    - **US005: Se creó un método que valida que el destino y origen enviado exista.**

#### US 0003: Realizar una reserva de hotel
#### US 0006: Realizar una reserva de vuelo

**Parámetros:**

- **Fecha Entrada:**
  - **Validaciones:**
    - Formato correcto (dd-mm-aaaa).
    - La fecha de entrada debe ser menor a la de salida.
  - **Mensajes de error:**
    - "Formato de fecha debe ser dd-mm-aaaa"
    - "La fecha de entrada debe ser menor a la de salida"

- **Fecha Salida:**
  - **Validaciones:**
    - Formato correcto (dd-mm-aaaa).
    - La fecha de salida debe ser mayor a la de entrada.
  - **Mensajes de error:**
    - "Formato de fecha debe ser dd-mm-aaaa"
    - "La fecha de salida debe ser mayor a la de entrada"

- **Destino:**
  - **Validaciones:**
    - El destino debe existir.
  - **Mensaje de error:**
    - "El destino elegido no existe"


- **Cantidad de Personas:**
  - **Validaciones:**
    - Debe ser un valor numérico.
  - **Mensaje de error:**
    - "La cantidad de personas debe ser un valor numérico."

- **Tipo de Habitación:**
  - **Validaciones:**
    - Debe coincidir con la cantidad de personas.
  - **Mensaje de error:**
    - "El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella."

**Se creó un método (roomCapacity) que valida que la cantidad de personas coincida con el tipo de habitación.**
**US005: Se creó un método que valida que la cantidad de personas coincida con la cantidad de asientos.**

- **E-mail:**
  - **Validaciones:**
    - Debe cumplir con el formato de correo electrónico (nombre@dominio.com).
  - **Mensaje de error:**
    - "Por favor ingrese un e-mail válido".

- **Intereses:**
  - **Validaciones:**
    - En caso de tarjeta de crédito, verificar recargo de intereses según el número de cuotas.
    - En caso de tarjeta de débito, verificar que no se incorporen intereses y que se permita el pago en una sola cuota.
  - **Mensajes:**
    - Tarjeta de crédito: Devolver porcentaje y monto de interés (recargo).
    - Tarjeta de débito: Informar que se ha ingresado una cantidad de cuotas diferente a 1.

  **Se creó un método (calcInterest) que valida los tipos de pago e intereses correspondientes.**




