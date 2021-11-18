

```shell
# Only if needed 
./create-service-account.sh

# Then
./create-kafka-instance.sh
source scripts/token.sh

export WEBHOOK_URL=<slack URL>

mvn compile exec:java -Dexec.mainClass=com.redhat.service.ManagedConnectorServiceApplication -Dexec.args="$OCM_TOKEN $COS_BASE_PATH $KAFKA_BASE_PATH $SERVICEACCOUNT_ID $SERVICEACCOUNT_SECRET $WEBHOOK_URL"

kcat -t slacktopic -b "$KAFKA_BASE_PATH" \
-X security.protocol=SASL_SSL -X sasl.mechanisms=PLAIN \
-X sasl.username="$SERVICEACCOUNT_ID" \
-X sasl.password="$SERVICEACCOUNT_SECRET" -P

```
