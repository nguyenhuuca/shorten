#!/bin/sh

#export JAVA_HOME=/usr/local/java
#PATH=/usr/local/java/bin:${PATH}

#---------------------------------#
# dynamically build the classpath #
#---------------------------------#
#THE_CLASSPATH=
#for i in `ls ./lib/*.jar`
#do
#  THE_CLASSPATH=${THE_CLASSPATH}:${i}
#done
#====
# .env loading in the shell
dotenv () {
  set -a
  [ -f ../config/.env ] && . ../config/.env
  set +a
}


# . ../config/env.properties
dotenv
CLASSPATH=
for file in `ls ../lib/*.jar`
do
    CLASSPATH=${CLASSPATH}:${file}
done

for f  in `ls ../classes/*.jar`
do
    CLASSPATH=${CLASSPATH}:${f}
done
echo $CLASSPATH

# java option configure. 
JAVA_OPT="${MAX_HEAP} ${MIN_HEAP} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/heap_dump.hprof"
echo "java option configure:"
echo "${JAVA_OPT}"

# export SPRING_ACTIVE_PROFILE=${SPRING_ACTIVE_PROFILE}
echo "env: ${SPRING_ACTIVE_PROFILE}"
echo "" > logs/shorten.log

## in case run main file
# nohup $JAVA_HOME/bin/java $JAVA_OPT -cp ":${CLASSPATH}" \
#    com.canhlabs.ShortenApp  --spring.config.location=../config/ > /dev/null & echo "$!" > ../pid/shorten.pid

## in case rung jar file
nohup $JAVA_HOME/bin/java $JAVA_OPT -jar shorten-1.0-SNAPSHOT.jar --spring.config.location=config/ > /dev/null 2>&1 & echo  "$!" > ../pid/shorten.pid

i=0
while [ $i -lt 5 ]  
do
    
    if grep -q "Tomcat started on port(s)" ../logs/shorten.log;
        then
            echo "Started ShortenApp"
            echo "process id:$!"
            break
        else
                echo "waiting..."
    fi
    sleep 5

done
