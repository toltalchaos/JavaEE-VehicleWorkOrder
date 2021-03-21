FROM openliberty/open-liberty:kernel-java11-openj9-ubi

COPY --chown=1001:0  target/dmit2015-assignment03-BraydonTol.war /content.config/dropins/
COPY --chown=1001:0  server.xml /content.config

RUN configure.sh