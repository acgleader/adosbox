#!/bin/sh

# Set here your own NDK path if needed
# export PATH=$PATH:~/src/endless_space/android-ndk-r5b
NDKBUILDPATH=$PATH

MYARCH=linux-x86
if uname -s | grep -i "linux" > /dev/null ; then
	MYARCH=linux-x86
fi
if uname -s | grep -i "darwin" > /dev/null ; then
	MYARCH=darwin-x86
fi
if uname -s | grep -i "windows" > /dev/null ; then
	MYARCH=windows-x86
fi

cd . && env PATH=$NDKBUILDPATH nice -n19 ndk-build -j4 V=1 && \
 { grep "CustomBuildScript=y" ./AndroidAppSettings.cfg > /dev/null && \
   [ -`which ndk-build | grep '/android-ndk-r5b/\|/android-ndk-r5-crystax/'` != - ] && \
   echo Stripping libapplication.so by hand \
   rm obj/local/armeabi/libapplication.so && \
   cp jni/application/src/libapplication.so obj/local/armeabi && \
   cp jni/application/src/libapplication.so libs/armeabi && \
   `which ndk-build | sed 's@/ndk-build@@'`/toolchains/arm-linux-androideabi-4.4.3/prebuilt/$MYARCH/bin/arm-linux-androideabi-strip --strip-unneeded libs/armeabi/libapplication.so \
   || true ; } 
