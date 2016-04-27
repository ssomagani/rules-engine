# rules-engine

A prototype for using external dynamic rules in VoltDB's stored procedures. This project contains both the source 
for the rule engine and a test stored procedure to demonstrate the usage.

Rules
-----
Rules can be written in simple Javascript code and saved in a table. Each rule is identified by an identifier that
the stored procedure code can use to look it up.

Use Case
-----
db/TestSP is a test stored procedure that uses the rules engine. The use case here is a primitive/incomplete
traffic monitoring system that logs movement of cars.
Rules - When a car's (identified by car_id) movement event is received, it's velocity against a rule to whether 
insert it into the car_movement table or not.
When the movement event belongs to a car that was not see before, the movement is also logged to the new_car table.

