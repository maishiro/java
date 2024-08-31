FROM postgres:15

# 環境変数の設定
ENV POSTGRES_DB=db1
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

# 初期化スクリプトをコピー
COPY init.sql /docker-entrypoint-initdb.d/
