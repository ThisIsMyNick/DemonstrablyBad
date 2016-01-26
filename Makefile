PACKAGE = translate

JAVAC = javac
JVM = java
MKDIR = mkdir -p

LIB_DIR = ./lib/
BIN_DIR = ./bin/
SRC_DIR = ./src/

TESS_DIR = ./lib/tess4j-1.3.0.jar

COMPILE_FLAGS = -g -d $(BIN_DIR) -cp $(BIN_DIR):$(TESS_DIR)
COMPILE = $(JAVAC) $(COMPILE_FLAGS)

RUN_FLAGS = -cp $(BIN_DIR):$(TESS_DIR) $(PACKAGE).Driver
RUN = $(JVM) $(RUN_FLAGS)

.SILENT:

all: makedirs
	find $(SRC_DIR) -name "*.java" -print | xargs $(COMPILE)

makedirs:
	$(MKDIR) $(BIN_DIR)

clean:
	rm -rf $(BIN_DIR)
