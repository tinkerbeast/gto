LD = ld
CC = gcc
CFLAGS   = -std=c99 -pedantic -Wall -O3

all: clean build

clean:
	@find . -name "*.out" | xargs rm -f

build: main

main: splay

splay: benchmark/splay.c
	$(CC) $(CFLAGS) ./benchmark/splay.c -o splay.out

