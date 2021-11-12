#!/bin/sh

# rhoas login --print-sso-url

OLD_SERVICE_ACCOUNT_ID=$(rhoas service-account list | grep lucamolteni-managedconnector-serviceaccount | awk '{print $1}')

rhoas service-account delete -y --id=$OLD_SERVICE_ACCOUNT_ID

rhoas service-account create --output-file=./service-acct-credentials.json --file-format=json --overwrite --short-description=lucamolteni-managedconnector-serviceaccount

