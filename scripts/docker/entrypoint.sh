#!/bin/sh

SPRING_PROFILE_CONFIG=""


################################################## active config start #################################################
if [ "${SPRING_PROFILES_ACTIVE}" ];then
    SPRING_PROFILE_CONFIG="${SPRING_PROFILE_CONFIG} -Dspring.profiles.active='$SPRING_PROFILES_ACTIVE'"
else
    SPRING_PROFILE_CONFIG="${SPRING_PROFILE_CONFIG} -Dspring.profiles.active='docker'"
fi
################################################## active config end ###################################################

sh -c "java -server ${SPRING_PROFILE_CONFIG} ${SPRING_OPTS} ${JAVA_OPTS} -jar /app/face-search/face-search-server.jar"
