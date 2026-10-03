-- QualiTrack - Product Batch Management: equipment and staff that take part in a batch (TS66-TS67)
-- Target: MySQL 8. Needed where spring.jpa.hibernate.ddl-auto=none; with ddl-auto=update Hibernate
-- creates the same tables. Names are copied at registration time so traceability keeps them.

CREATE TABLE IF NOT EXISTS batch_equipment_usages (
    id                    BIGINT       NOT NULL AUTO_INCREMENT,
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6)  NOT NULL,
    batch_id              BIGINT       NOT NULL,
    equipment_id          BIGINT       NOT NULL,
    equipment_name        VARCHAR(150) NOT NULL,
    registered_by_user_id BIGINT       NULL,
    registered_at         VARCHAR(30)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_batch_equipment_usages_batch_equipment UNIQUE (batch_id, equipment_id)
);

CREATE TABLE IF NOT EXISTS batch_staff_participations (
    id                    BIGINT       NOT NULL AUTO_INCREMENT,
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6)  NOT NULL,
    batch_id              BIGINT       NOT NULL,
    staff_id              BIGINT       NOT NULL,
    staff_name            VARCHAR(150) NOT NULL,
    staff_role            VARCHAR(100) NULL,
    registered_by_user_id BIGINT       NULL,
    registered_at         VARCHAR(30)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_batch_staff_participations_batch_staff UNIQUE (batch_id, staff_id)
);
