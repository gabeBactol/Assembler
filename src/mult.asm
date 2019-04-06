// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

@2 	//A = 2
M = 0	//RAM[2] = 0
@0	//A = 0
D = M	//D = RAM[0]
@END	//Go to the END loop, 
D;JEQ	//If RAM[0], or D, is 0, 0*1 = 0
@1	//A = 1
D = M	//D = RAM[1]
@END	//Go to the END loop
D;JEQ	//If RAM[0], or D, is 0, 1*0 = 0
(LOOP)	//A loop to calculate the multiplication
@1	//A = 1
D = M	//D = RAM[1]
@2	//A = 2
M = D+M	//RAM[2] = D + RAM[2], adding the second number to the sum
@0	//A = 0
M = M-1	//RAM[0] = RAM[0] - 1, reducing amount to add for the multiplication
D = M	//D = RAM[0]
@LOOP	//Loop through the LOOP if condition is met
D;JGT	//if D, or RAM[0], is greater than 0
(END)	//INFINITE LOOP
@END	//This loop used to stop the code will infinitely loop
0;JMP	//it will jump to beginning of loop