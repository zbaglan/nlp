#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  create database test with encoding 'UTF8' LC_COLLATE='en_US.utf8' LC_CTYPE='en_US.utf8';
  grant all privileges on database test to postgres;
EOSQL