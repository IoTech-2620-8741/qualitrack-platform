-- QualiTrack - Equipment Management: equipment per environment, IoT devices and status history (TS31-TS41)
-- Target: MySQL 8. Needed where spring.jpa.hibernate.ddl-auto=none; with ddl-auto=update Hibernate adds the
-- same columns and table. Safe to run more than once. No table or data is removed.

-- 1. Environment where the equipment is located (null until it is associated, US47).
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'equipment' AND COLUMN_NAME = 'environment_id');
SET @sql = IF(@missing, 'ALTER TABLE equipment ADD COLUMN environment_id BIGINT NULL AFTER laboratory_id', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 2. IoT device type: ENVIRONMENTAL_DEVICE or CONTAINER_MONITOR; null for equipment without telemetry (US51, US53).
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'equipment' AND COLUMN_NAME = 'device_type');
SET @sql = IF(@missing, 'ALTER TABLE equipment ADD COLUMN device_type VARCHAR(30) NULL', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 3. Firmware version reported for an IoT device.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'equipment' AND COLUMN_NAME = 'firmware_version');
SET @sql = IF(@missing, 'ALTER TABLE equipment ADD COLUMN firmware_version VARCHAR(50) NULL', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 4. The identity with which Edge recognises a device is unique (several equipment may still have none).
--    If existing rows repeat an identifier the constraint is not added; the SELECT lists them for review.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'equipment'
                  AND INDEX_NAME = 'uk_equipment_sensor_external_id');
SET @duplicates = (SELECT COUNT(*) FROM (SELECT sensor_external_id FROM equipment
                   WHERE sensor_external_id IS NOT NULL GROUP BY sensor_external_id HAVING COUNT(*) > 1) repeated);
SET @sql = IF(@missing AND @duplicates = 0,
              'ALTER TABLE equipment ADD CONSTRAINT uk_equipment_sensor_external_id UNIQUE (sensor_external_id)',
              IF(@missing, 'SELECT sensor_external_id, COUNT(*) AS equipment FROM equipment WHERE sensor_external_id IS NOT NULL GROUP BY sensor_external_id HAVING COUNT(*) > 1', 'SELECT 1'));
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 5. Environment where the equipment was located when a maintenance was registered (null for older records).
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'maintenance_records' AND COLUMN_NAME = 'environment_id');
SET @sql = IF(@missing, 'ALTER TABLE maintenance_records ADD COLUMN environment_id BIGINT NULL AFTER equipment_id', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 6. Traceable history of operational status changes (US48, TS34).
CREATE TABLE IF NOT EXISTS equipment_status_changes (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    created_at         DATETIME(6)  NOT NULL,
    updated_at         DATETIME(6)  NOT NULL,
    equipment_id       BIGINT       NOT NULL,
    environment_id     BIGINT       NULL,
    previous_status    VARCHAR(50)  NOT NULL,
    new_status         VARCHAR(50)  NOT NULL,
    reason             VARCHAR(500) NULL,
    changed_by_user_id BIGINT       NULL,
    changed_at         DATETIME(6)  NOT NULL,
    PRIMARY KEY (id)
);
