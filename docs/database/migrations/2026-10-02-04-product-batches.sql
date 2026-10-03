-- QualiTrack - Product Batch Management: batches nested under products (TS63-TS64, TS71-TS72)
-- Target: MySQL 8. Run after 2026-10-02-03-product-batch-products.sql. Safe to run more than once
-- and with any ddl-auto setting (ddl-auto=update never drops the old global batch_number index).
-- digital_signatures and rejection_records already exist; releases and rejections now write to them.

-- 1. Environment where the batch is manufactured (nullable for batches registered before environments).
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'batches' AND COLUMN_NAME = 'environment_id');
SET @sql = IF(@missing, 'ALTER TABLE batches ADD COLUMN environment_id BIGINT NULL AFTER laboratory_id', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 2. Batch numbers were unique across all laboratories; they are now unique per laboratory.
SET @old_index = (SELECT INDEX_NAME FROM information_schema.STATISTICS
                  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'batches'
                    AND NON_UNIQUE = 0 AND INDEX_NAME <> 'PRIMARY'
                  GROUP BY INDEX_NAME HAVING COUNT(*) = 1 AND MAX(COLUMN_NAME) = 'batch_number' LIMIT 1);
SET @sql = IF(@old_index IS NULL, 'SELECT 1', CONCAT('ALTER TABLE batches DROP INDEX `', @old_index, '`'));
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'batches'
                  AND INDEX_NAME = 'uk_batches_laboratory_batch_number');
SET @sql = IF(@missing, 'ALTER TABLE batches ADD CONSTRAINT uk_batches_laboratory_batch_number UNIQUE (laboratory_id, batch_number)', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 3. Existing batches take the environment of their product (set by the previous script).
UPDATE batches b
JOIN pharmaceutical_products p ON p.id = b.product_id AND p.laboratory_id = b.laboratory_id
SET b.environment_id = p.environment_id
WHERE b.environment_id IS NULL AND p.environment_id IS NOT NULL;
