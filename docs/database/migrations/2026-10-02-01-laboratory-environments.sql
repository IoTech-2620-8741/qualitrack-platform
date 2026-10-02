-- QualiTrack - Laboratory Management: environments (US30-US33, TS15-TS18)
-- Target: MySQL 8. Needed only where spring.jpa.hibernate.ddl-auto=none (prod profile).
-- In the dev profile (ddl-auto=update) Hibernate creates this table automatically.

CREATE TABLE IF NOT EXISTS environments (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6)  NOT NULL,
    laboratory_id     BIGINT       NOT NULL,
    code              VARCHAR(30)  NOT NULL,
    name              VARCHAR(100) NOT NULL,
    description       VARCHAR(255) NULL,
    environment_usage VARCHAR(30)  NULL,
    usage_assigned_by BIGINT       NULL,
    usage_assigned_at DATETIME(6)  NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_environments_laboratory_code UNIQUE (laboratory_id, code)
);
