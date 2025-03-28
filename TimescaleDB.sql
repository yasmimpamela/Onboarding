-- Criando a tabela de sensores
CREATE TABLE energy_readings (
    id SERIAL PRIMARY KEY,
    reading_time TIMESTAMPTZ NOT NULL,
    sensor_id INT NOT NULL,
    energy_kwh DOUBLE PRECISION NOT NULL,
    location TEXT
);

-- Converter para hypertable
SELECT create_hypertable('energy_readings', 'reading_time');

-- Otimizando buscas
CREATE INDEX ON energy_readings (sensor_id, reading_time DESC);

-- Inserindo dados
INSERT INTO energy_readings (reading_time, sensor_id, energy_kwh, location) VALUES
    (NOW() - INTERVAL '1 day', 1, 12.5, 'Fábrica A'),
    (NOW() - INTERVAL '12 hours', 1, 14.1, 'Fábrica A'),
    (NOW() - INTERVAL '6 hours', 2, 10.9, 'Escritório B'),
    (NOW(), 2, 11.4, 'Escritório B');

-- CONSULTAS

-- Média de consumo nas últimas 24h por sensor
SELECT sensor_id, AVG(energy_kwh) AS avg_consumption
FROM energy_readings
WHERE reading_time >= NOW() - INTERVAL '1 day'
GROUP BY sensor_id;

-- Última leitura de cada sensor
SELECT DISTINCT ON (sensor_id) sensor_id, reading_time, energy_kwh, location
FROM energy_readings
ORDER BY sensor_id, reading_time DESC;

-- Sensores com consumo acima de 50 kWh
SELECT * FROM energy_readings WHERE energy_kwh > 50;

-- Consumo diário por sensor
SELECT sensor_id, DATE_TRUNC('day', reading_time) AS day,
       MAX(energy_kwh) - MIN(energy_kwh) AS daily_consumption
FROM energy_readings
GROUP BY sensor_id, day
ORDER BY sensor_id, day;