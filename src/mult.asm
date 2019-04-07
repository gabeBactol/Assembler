// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

@2     //A=2
M=0    //RAM[2] = 0
@0     //A =0
D=M    //D=RAM[0]
@cout  // A=cout
M=D    //RAM[cout] = D
@END   //A=END
D;JEQ  // if D=0 jump to END

(LOOP)
@1     //A=1
D=M    //D = RAM[1]
@2     // A=2
M=M+D  //RAM[2] = RAM[2] + RAM[1]
@cout  //A=cout
MD=M-1 //MD=RAM[cout] - 1
@END   //A=END
D;JEQ  //RAM[cout] = 0, if the cout is finished then jump to the end
@LOOP  //A=LOOP
0;JMP  //jump back up to LOOP

(END)  //infinate loop to end the program
@END
0;JMP
