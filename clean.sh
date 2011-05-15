#! /bin/bash
# clean up generated files

find . -name "*.o"|xargs -I{} rm {}
find . -name "*.so"|xargs -I{} rm {}
find . -name "*.a"|xargs -I{} rm {}
find . -name "Makefile" | xargs -I{} rm {}
find . -name "config.log" | xargs -I{} rm {}
find . -name "config.status" | xargs -I{} rm {}
find . -name "config.h" | xargs -I{} rm {}

