-- QualiTrack - Inventory Management: raw materials kept per environment (TS21-TS30)
-- Target: MySQL 8. Needed only where spring.jpa.hibernate.ddl-auto=none (prod profile).
-- In the dev profile (ddl-auto=update) Hibernate adds the column automatically.
-- environment_id stays nullable: materials registered before environments existed keep NULL
-- until they are assigned, for example:
--   UPDATE inventory_materials SET environment_id = <environment id> WHERE laboratory_id = <laboratory id>;

ALTER TABLE inventory_materials ADD COLUMN environment_id BIGINT NULL AFTER laboratory_id;
