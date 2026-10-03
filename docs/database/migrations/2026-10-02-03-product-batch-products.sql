-- QualiTrack - Product Batch Management: pharmaceutical products per environment (TS61-TS62)
-- Target: MySQL 8. Safe to run more than once and with any ddl-auto setting:
--   * ddl-auto=update (dev) adds environment_id and the new unique key, but it never drops the
--     old global unique index on code, so this script is needed in dev as well.
--   * ddl-auto=none (prod) needs every step.
-- The products table keeps its name; only the owning bounded context changed in the code.

-- 1. Environment where the product is manufactured (nullable for products registered before environments).
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'pharmaceutical_products' AND COLUMN_NAME = 'environment_id');
SET @sql = IF(@missing, 'ALTER TABLE pharmaceutical_products ADD COLUMN environment_id BIGINT NULL AFTER laboratory_id', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 2. Product codes were unique across all laboratories; they are now unique per laboratory.
SET @old_index = (SELECT INDEX_NAME FROM information_schema.STATISTICS
                  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'pharmaceutical_products'
                    AND NON_UNIQUE = 0 AND INDEX_NAME <> 'PRIMARY'
                  GROUP BY INDEX_NAME HAVING COUNT(*) = 1 AND MAX(COLUMN_NAME) = 'code' LIMIT 1);
SET @sql = IF(@old_index IS NULL, 'SELECT 1', CONCAT('ALTER TABLE pharmaceutical_products DROP INDEX `', @old_index, '`'));
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'pharmaceutical_products'
                  AND INDEX_NAME = 'uk_pharmaceutical_products_laboratory_code');
SET @sql = IF(@missing, 'ALTER TABLE pharmaceutical_products ADD CONSTRAINT uk_pharmaceutical_products_laboratory_code UNIQUE (laboratory_id, code)', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 3. Products registered before environments existed are placed in the first production environment
--    of their laboratory. Products of laboratories without a production environment keep NULL and
--    can be assigned manually: UPDATE pharmaceutical_products SET environment_id = <id> WHERE id = <id>;
UPDATE pharmaceutical_products p
SET p.environment_id = (SELECT MIN(e.id) FROM environments e
                        WHERE e.laboratory_id = p.laboratory_id AND e.environment_usage = 'PRODUCTION')
WHERE p.environment_id IS NULL;
