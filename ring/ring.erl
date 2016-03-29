-module(ring).

-export([ring1/2]). % only exported functions can be referenced from another module
-export([ring2/2]). % only exported functions can be referenced from another module

ring1(thing, NMsgs) -> NMsgs.
ring1(NProcesses, NMsgs) -> (NProcesses * NMsgs).

ring2(NProcesses, NMsgs) -> (NProcesses * NMsgs).