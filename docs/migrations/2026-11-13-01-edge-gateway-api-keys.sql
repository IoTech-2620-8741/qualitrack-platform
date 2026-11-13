-- QualiTrack - Edge Gateway API keys (#30) and indexes of the deviation alerts (#33).
-- Target: MySQL 8. Needed where spring.jpa.hibernate.ddl-auto=none (the deployed platform): run it before the
-- release that brings Edge Gateways is deployed. With ddl-auto=update Hibernate creates the same table and indexes.
-- Safe to run more than once. No table or data is removed, and nothing is updated, so it runs with safe update mode.

-- 1. API keys of the Edge Gateways: only the BCrypt hash of each key, one key per Gateway (its equipment id).
CREATE TABLE IF NOT EXISTS iam_edge_gateway_api_keys (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6)  NOT NULL,
    equipment_id BIGINT       NOT NULL,
    api_key_hash VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_gateway_api_key_equipment UNIQUE (equipment_id)
);

-- 2. Open incident of a device and variable: the alert that a new deviation joins.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'deviation_alerts'
                  AND INDEX_NAME = 'idx_deviation_alerts_open_incident');
SET @sql = IF(@missing,
              'CREATE INDEX idx_deviation_alerts_open_incident ON deviation_alerts (equipment_id, parameter_name, status)',
              'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 3. Alerts of an environment of a laboratory: the inbox and the history of the web application.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'deviation_alerts'
                  AND INDEX_NAME = 'idx_deviation_alerts_environment');
SET @sql = IF(@missing,
              'CREATE INDEX idx_deviation_alerts_environment ON deviation_alerts (laboratory_id, environment_id)',
              'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- Check: the table and both indexes are listed.
SELECT TABLE_NAME, INDEX_NAME
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
  AND (TABLE_NAME = 'iam_edge_gateway_api_keys'
       OR INDEX_NAME IN ('idx_deviation_alerts_open_incident', 'idx_deviation_alerts_environment'))
GROUP BY TABLE_NAME, INDEX_NAME;
