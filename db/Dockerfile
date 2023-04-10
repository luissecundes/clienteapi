FROM postgres:latest

# Set environment variables
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD mypassword

# Expose PostgreSQL default port
EXPOSE 5432

# Create data directory and set permissions
RUN mkdir -p /var/lib/postgresql/data && chown -R postgres:postgres /var/lib/postgresql/data

# Set data directory as volume
VOLUME /var/lib/postgresql/data

VOLUME ./init-scripts:/docker-entrypoint-initdb.d

# Start PostgreSQL service
CMD ["postgres"]
#docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=mypassword -e POSTGRES_USER=postgres -d postgres
