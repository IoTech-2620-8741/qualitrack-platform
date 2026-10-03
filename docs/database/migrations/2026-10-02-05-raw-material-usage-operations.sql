-- QualiTrack - Product Batch Management: raw material usages registered from the batch (TS65)
-- Target: MySQL 8. Safe to run more than once and with any ddl-auto setting.
-- operation_id keeps the idempotency key of the Inventory consumption that produced the usage,
-- so a retried request returns the original usage. Usages recorded before keep NULL.

SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'raw_material_usages' AND COLUMN_NAME = 'operation_id');
SET @sql = IF(@missing, 'ALTER TABLE raw_material_usages ADD COLUMN operation_id VARCHAR(100) NULL', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;
