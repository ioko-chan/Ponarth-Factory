
CREATE TABLE "users"(
    "id" SERIAL NOT NULL,
    "username" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "date_registration" DATE NOT NULL,
    "enabled" BOOLEAN NOT NULL
);

ALTER TABLE
    "users" ADD PRIMARY KEY("id");
ALTER TABLE
    "users" ADD CONSTRAINT "user_username_unique" UNIQUE("username");

CREATE TABLE "roles"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "roles" ADD PRIMARY KEY("id");
ALTER TABLE
    "roles" ADD CONSTRAINT "roles_name_unique" UNIQUE("name");

CREATE TABLE "user_roles"(
    "user_id" BIGINT NOT NULL,
    "role_id" BIGINT NOT NULL
);
ALTER TABLE
    "user_roles" ADD CONSTRAINT "user_roles_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "users"("id");
ALTER TABLE
    "user_roles" ADD CONSTRAINT "user_roles_role_id_foreign" FOREIGN KEY("role_id") REFERENCES "roles"("id");

INSERT INTO "roles"("name") VALUES ('ADMIN'), ('USER');
