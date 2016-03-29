-module(ring).
-export([ring1/2, ring2/2]).
-export([loop/1, test/0, testRing1/0, testRing2/0]).

%This sends each message around the ring one at a time, like method 1 in the assignment.
%The only lines I wrote were 10 and 11. Line 10 creates the process chain and returns the 
%"Leader" of the processes. Line 11 then tells the Leader process to process NMsgs.
%It then prints out the total time spend on the operation. %We know all the messages are received by
%the leader because the counter decrements when a messages is recieved rather than sent, meaning it
%reaches 0 and allows the main process to continue only after it recievs the final message.
ring1(NProcesses, NMsgs)-> 
	statistics(runtime),
	statistics(wall_clock),
	Leader = batch_init(NProcesses),
	leaderProcess(Leader, NMsgs),
	{_,Time1} = statistics(runtime),
	{_,Time2} = statistics(wall_clock),
	io:format("The work took ~p cpu milliseconds and ~p wallclock milliseconds~n", [Time1, Time2]).

%This function is relatively simple. leaderProcess(Leader, NMsgs) send a message, waits for it to
%get around the loop, then sends the next message. Each time it decrements NMsgs until it reaches 0
%when the function then is matches to (Leader, 0) and quits itself and all the other chained processes
leaderProcess(Leader, 0) ->
	Leader ! quit;
leaderProcess(Leader, NMsgs) ->
	Leader ! {next, NMsgs},
	receive
		{next, NMsgs} ->
			leaderProcess(Leader, NMsgs - 1)
    end.

%Simple loop function described in class. This function loops repeatedly, sending any received messages
%to the process ID passed to it initially. Stops looping when it receives the quit command.
loop(Successor)->
	receive
		quit-> Successor ! quit;
		M-> Successor ! M, 
			loop (Successor)
	end.
	

%This simply calls batch_init_loop/2 passing the main process ID in to be the original Successor.
batch_init(N)->
	batch_init_loop(N, self()).

%This uses the magical foldl function discussed in class to spawn every process
%and pass in Successors PID.	
batch_init_loop(N, First)->
	lists:foldl(fun spawnProcess/2, First, lists:seq(1,N)).
	
%This is used in fold to spawn all the processes and begin looping them.
spawnProcess(_,A)->
	spawn(ring, loop, [A]).
	
%This ring2 function simply has the leader process shoot out all of its messages in some scheduler
%determined speed so that the following processes can do the same. The only difference from thisis
%and ring1 is that this uses the leaderProcess2 function tosend the messages. This is method 2 as 
% described in the assignment. 
ring2(NProcesses, NMsgs)->
	statistics(runtime),
	statistics(wall_clock),
	Leader = batch_init(NProcesses),
	leaderProcess2(Leader, NMsgs),
	{_,Time1} = statistics(runtime),
	{_,Time2} = statistics(wall_clock),
	io:format("The work took ~p cpu milliseconds and ~p wallclock milliseconds~n", [Time1, Time2]).

%This just initiates the recursive leaderProcess2 function and initializes the variables.
leaderProcess2(Leader, NMsgs) ->
	leaderProcess2(Leader, NMsgs, 0, NMsgs).

%First, the function matching NMSgs 0 will not be called at all until
%The leader has sent every message out. Once that happens, the matching function
%will check to see if it has recieved all messages and die when it has.
%It checks this by counting messages recieved, and when every message has been recieved,
%it just passes quit down the process chain.
leaderProcess2(Leader, 0, NDone, NMax) ->
	if
		NDone == NMax -> 
			Leader ! quit;
		true ->
			receive
				{next, _, _, _} ->
					leaderProcess2(Leader, 0, NDone + 1, NMax)
			end
    end;
leaderProcess2(Leader, NMsgs, NDone, NMax) ->
	Leader ! {next, NMsgs, NDone, NMax},
	leaderProcess2(Leader, NMsgs - 1, NDone, NMax).

%Only testing functions below.
	
test()-> 
	ring:ring1(2000,2000),
	ring:ring2(2000,2000).
testRing1()-> 
	ring:ring1(8000,0000),
	ring:ring1(8000,1000),
	ring:ring1(8000,2000),
	ring:ring1(8000,4000),
	ring:ring1(8000,8000).
testRing2()-> 
	ring:ring2(8000,0000),
	ring:ring2(8000,1000),
	ring:ring2(8000,2000),
	ring:ring2(8000,4000),
	ring:ring2(8000,8000).