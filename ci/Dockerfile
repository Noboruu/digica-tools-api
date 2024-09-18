FROM tomcat:10.1.30-jre21-temurin

ENV TZ=Europe/Lisbon
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JPDA_ADDRESS='*:8000'
ENV JPDA_TRANSPORT='dt_socket'

COPY wars/api.war /usr/local/tomcat/webapps/digica-tools.war

CMD ["catalina.sh", "jpda", "run"]