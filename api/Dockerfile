FROM azul/zulu-openjdk:11

ENV APPDIR=/opt/app/api/classes
#VOLUME ${APPDIR}
WORKDIR ${APPDIR}

ENV JAVA_DEBUG_OPT="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
#CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom -jar ${APPDIR}/app.jar
CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom  -cp .:${APPDIR}/* com.gmarket.techblog.backend.api.ApiApplication

EXPOSE 8080 5005 8000