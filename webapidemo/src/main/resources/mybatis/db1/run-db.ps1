#cd /d %~dp0
Set-Location $PSScriptRoot

docker run --rm -d --name my-postgres-container -p 5432:5432 -e POSTGRES_DB=db1 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -v $pwd/init:/docker-entrypoint-initdb.d -v pg_hba.conf:/etc/postgresql/pg_hba.conf  postgres:15
