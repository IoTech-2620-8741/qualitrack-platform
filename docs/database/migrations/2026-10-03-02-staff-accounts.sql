-- QualiTrack - Staff accounts: sign-in accounts for the staff registered by a quality manager (US34, TS19)
-- Target: MySQL 8. Needed where spring.jpa.hibernate.ddl-auto=none; with ddl-auto=update Hibernate adds the
-- same columns. Safe to run more than once. No table or data is removed.
-- The ROLE_AUDITOR role is created by the application at startup together with the other roles.

-- 1. Accounts created with a temporary password must change it at the first sign in.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'iam_users' AND COLUMN_NAME = 'password_change_required');
SET @sql = IF(@missing, 'ALTER TABLE iam_users ADD COLUMN password_change_required BOOLEAN NOT NULL DEFAULT FALSE', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 2. What the staff member can do (OPERATOR or AUDITOR); null for staff registered before accounts existed.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'staff_members' AND COLUMN_NAME = 'access_role');
SET @sql = IF(@missing, 'ALTER TABLE staff_members ADD COLUMN access_role VARCHAR(20) NULL', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 3. Account (iam_users.id) with which the staff member signs in; one account per staff member.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'staff_members' AND COLUMN_NAME = 'user_id');
SET @sql = IF(@missing, 'ALTER TABLE staff_members ADD COLUMN user_id BIGINT NULL', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'staff_members' AND INDEX_NAME = 'uk_staff_members_user_id');
SET @sql = IF(@missing, 'ALTER TABLE staff_members ADD CONSTRAINT uk_staff_members_user_id UNIQUE (user_id)', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;

-- 4. Staff member who performed a maintenance (chosen from the staff list); null for records with a typed name.
SET @missing = (SELECT COUNT(*) = 0 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'maintenance_records' AND COLUMN_NAME = 'technician_staff_id');
SET @sql = IF(@missing, 'ALTER TABLE maintenance_records ADD COLUMN technician_staff_id BIGINT NULL AFTER technician_name', 'SELECT 1');
PREPARE statement FROM @sql; EXECUTE statement; DEALLOCATE PREPARE statement;
