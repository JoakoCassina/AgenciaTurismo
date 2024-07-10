INSERT INTO hotels (hotel_code, hotel_name, destination, room_type, price_for_night, date_from, date_to, reserved)
VALUES
('CH-0002', 'Cataratas Hotel', 'Puerto Iguazú', 'Doble', 6300, '2025-02-10', '2025-03-20', 0 ),
('CH-0003', 'Cataratas Hotel 2', 'Puerto Iguazú', 'Triple', 8200, '2025-02-10', '2025-03-23', 0);

INSERT INTO flights (flight_code, origin, destination, seat_type, price, date_from, date_to)
VALUES
('BAPI-1235', 'Buenos Aires', 'Puerto Iguazú', 'Economy', 6500, '2025-02-10', '2025-02-15'),
('PIBA-1420', 'Puerto Iguazú', 'Bogotá', 'Business', 43200, '2025-02-10', '2025-02-20');
