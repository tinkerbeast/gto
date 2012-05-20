all: build-java build-js build-c

clean:
	cd src/java && $(MAKE) clean
	cd src/java && $(MAKE) clean

build-java:
	@echo "Building java ..."
	cd src/java && $(MAKE)

build-c:
	@echo "Building C ..."
	cd src/c && $(MAKE)

build-js:
	@echo "Building js ..."
	cd src/java && $(MAKE)

