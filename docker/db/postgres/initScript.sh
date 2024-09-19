#!/bin/bash
set -e

POSTGRES="psql --username postgres"

echo "Creating database role: digica_tools_user"

$POSTGRES <<-EOSQL
CREATE USER digica_tools_user WITH CREATEDB PASSWORD 'digica_tools_user_pass';
EOSQL

echo "Creating database: digicatoolsdb"

$POSTGRES <<EOSQL
CREATE DATABASE digicatoolsdb OWNER digica_tools_user;
EOSQL