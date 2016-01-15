PACKAGE = translate

JAVAC = javac
JVM = java
MKDIR = mkdir -p

LIB_DIR = ./lib/
BIN_DIR = ./bin/
SRC_DIR = ./src/
TEST_DIR = $(SRC_DIR)test/

JUNIT_DIR = /usr/share/java/junit4.jar

COMPILE_FLAGS = -g -d $(BIN_DIR) -cp $(JUNIT_DIR):$(BIN_DIR)
COMPILE = $(JAVAC) $(COMPILE_FLAGS)

TEST_FLAGS = -cp $(BIN_DIR):$(JUNIT_DIR) org.junit.runner.JUnitCore $(PACKAGE).test.TestSuite
RUN_TESTS = $(JVM) $(TEST_FLAGS)

RUN_FLAGS = -cp $(BIN_DIR):$(JUNIT_DIR) $(PACKAGE).Driver
RUN = $(JVM) $(RUN_FLAGS)

all: makedirs
	find $(SRC_DIR) -name "*.java" -print | xargs $(COMPILE)

makedirs:
	$(MKDIR) $(BIN_DIR)

clean:
	rm -rf $(BIN_DIR)

tests:
	find $(TEST_DIR) -name "*.java" -print | xargs $(COMPILE)

run_tests:
	$(RUN_TESTS)

run:
	$(RUN)
