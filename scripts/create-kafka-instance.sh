#!/bin/sh

interval_in_seconds=20

# Delete
OLD_KAFKA_INSTANCE_ID=$(rhoas kafka list | grep lucamolteni-managedconnector | awk '{print $1}')
if test -z "$OLD_KAFKA_INSTANCE_ID"
then
      echo "No Kafka Instance to to delete"
else
      echo "Attemping to delete kafka instance $OLD_KAFKA_INSTANCE_ID"
      rhoas kafka delete -y --id=$OLD_KAFKA_INSTANCE_ID

      printf "\nPolling kafka deletion every $interval_in_seconds seconds, until deleted\n"
      while true;
      do
          rhoas kafka list | grep lucamolteni-managedconnector
          status=$?
          printf "\r$(date +%H:%M:%S): $status";
          if [ $status = 1 ]; then
              printf "Kafka Instance deleted\n";
              break;
          fi;
          sleep $interval_in_seconds;
      done
fi


rhoas kafka create --name=lucamolteni-managedconnector
# rhoas kafka describe

SERVICEACCOUNT=$(cat ./service-acct-credentials.json | jq -r '.clientID')
export SERVICEACCOUNT

printf "\nPolling kafka creation every $interval_in_seconds seconds, until 'ready'\n"
while true;
do
    status=$(rhoas kafka list | grep lucamolteni-managedconnector | awk '{print $5}');
    printf "\r$(date +%H:%M:%S): $status";
    if [ "$status" = "ready" ]; then
        printf r"Kafka Instance provisioned\n";
        break;
    fi;
    sleep $interval_in_seconds;
done

rhoas kafka acl grant-access -y --consumer --producer --service-account $SERVICEACCOUNT --topic-prefix slacktopic  --group all

BASE_URL=$(rhoas kafka describe --name=lucamolteni-managedconnector | jq -r '.bootstrap_server_host')
export BASE_URL

PASSWORD=$(cat ./service-acct-credentials.json | jq -r '.clientSecret')
export PASSWORD

rhoas kafka topic create --name=slacktopic

echo "Kafk\n BASE_URL: $BASE_URL \n SERVICEACCOUNT: $SERVICEACCOUNT"
