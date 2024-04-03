CREATE TABLE "point_map"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "coords" VARCHAR(255) NOT NULL,
    "color" VARCHAR(255) NULL,
    "contacts_info" VARCHAR(255) NULL
);
ALTER TABLE
    "point_map" ADD PRIMARY KEY("id");

CREATE TABLE "beer"(
   "id" SERIAL NOT NULL,
   "name" VARCHAR(255) NOT NULL,
   "description" VARCHAR(255) NOT NULL,
   "color" VARCHAR(255) NOT NULL,
   "image" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "beer" ADD PRIMARY KEY("id");
ALTER TABLE
    "beer" ADD CONSTRAINT "beer_name_unique" UNIQUE("name");

CREATE TABLE "vacancy"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "image" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "vacancy" ADD PRIMARY KEY("id");
ALTER TABLE
    "vacancy" ADD CONSTRAINT "vacancy_name_unique" UNIQUE("name");