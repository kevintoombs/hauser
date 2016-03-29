-module(geometry).
-export([area/1]). % only exported functions can be referenced from another module
area({rectangle, Width, Height}) -> Width * Height;
area({circle, R}) -> 3.14159 * R * R.