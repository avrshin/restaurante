#!/bin/sh -e
/usr/local/pgsql/bin/dropdb -U postgres restaurante
/usr/local/pgsql/bin/createdb -U postgres restaurante
/usr/local/pgsql/bin/psql -U postgres -f "/home/luis/Dropbox/software_engineer_2/restaurante/banco/script.sql" restaurante
/usr/local/pgsql/bin/psql -U postgres -f "/home/luis/Dropbox/software_engineer_2/restaurante/banco/popula.sql" restaurante
