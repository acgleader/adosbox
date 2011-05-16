#! /bin/bash
# clean up generated files

find . -name "*.o"|xargs -I{} rm {}
find . -name "*.so"|xargs -I{} rm {}
find . -name "*.a"|xargs -I{} rm {}

if (( $# > 0 )); then
  howclean=$1
  case "$howclean" in
    all)
      echo "clean all"
      exit
      find . -name "Makefile" | xargs -I{} rm {}
      find . -name "config.log" | xargs -I{} rm {}
      find . -name "config.status" | xargs -I{} rm {}
      rm jni/application/dosbox/dosbox-0.74/config.h
      ;;
    *)
      echo "unrecognized parameter \"$howclean\""
  esac
fi

