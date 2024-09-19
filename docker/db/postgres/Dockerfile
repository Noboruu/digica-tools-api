FROM postgres:16.4

ENV TZ=Europe/Lisbon
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD initScript.sh /docker-entrypoint-initdb.d