#!/bin/bash

JMETER=~/apache-jmeter-5.2.1/bin/jmeter

HOST=10.0.0.11
PORT=81

JMX_FILE=ArrivalsDepartures1
LOOP_COUNT=100


execute_test() {
  DATE_TIME=$(date +"%FT%H%M")
  
  RESULTS_FOLDER=results/stress_U${NO_USERS}_LC${LOOP_COUNT}__${DATE_TIME}
  JTL_FILE=${RESULTS_FOLDER}/$JMX_FILE.jtl

  mkdir $RESULTS_FOLDER

  PARAMS="-Jhost=${HOST} -Jport=${PORT} -Jno_users=${NO_USERS} -Jloop_count=${LOOP_COUNT}"
  CMD="jmeter -n -t $JMX_FILE.jmx $PARAMS -l $JTL_FILE -e -o $RESULTS_FOLDER/"

  echo $CMD
  ${JMETER} -n -t $JMX_FILE.jmx ${PARAMS} -l $JTL_FILE -e -o ${RESULTS_FOLDER}/
}



NO_USERS=100
echo "NO_USERS: ${NO_USERS}"

execute_test

