#!/bin/sh

LOCAL_PATH=`dirname $0`
LOCAL_PATH=`cd $LOCAL_PATH && pwd`

$LOCAL_PATH/setEnvironment.sh ./configure --host=arm-eabi "$@"

