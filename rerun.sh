#!bin/bash
test_file="src/test/resources/FailedTests.txt"
if [ -f "$test_file" ]; then
  if [ -s "$test_file" ]; then
    ./gradlew clean testNGRun -x test $(cat $test_file)
  else
    echo "test file is empty"
  fi
fi