-module(ringDemo).
-export([test/0, testRing1/0, testRing2/0, testBothRings/0]).

-import(ring, [ring1/2, ring2/2, batch_init/1, loop/1]).

test()-> 
	0.
testRing1()-> 
	ring:ring1(1000,0000),
	ring:ring1(1000,1000),
	ring:ring1(1000,2000),
	ring:ring1(1000,4000),
	ring:ring1(1000,8000),
	ring:ring1(2000,0000),
	ring:ring1(2000,1000),
	ring:ring1(2000,2000),
	ring:ring1(2000,4000),
	ring:ring1(2000,8000),
	ring:ring1(4000,0000),
	ring:ring1(4000,1000),
	ring:ring1(4000,2000),
	ring:ring1(4000,4000),
	ring:ring1(4000,8000),
	ring:ring1(8000,0000),
	ring:ring1(8000,1000),
	ring:ring1(8000,2000),
	ring:ring1(8000,4000),
	ring:ring1(8000,8000).
testRing2()-> 
	ring:ring2(200, 8).
testBothRings()->
	ring:ring1(200, 8),
	ring:ring2(200, 8).