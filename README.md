# Pascal to JVM Bytecode Translator

A compiler project developed during my bachelor's degree course in Formal Languages and Translators. This tool translates Pascal-like code into JVM bytecode, allowing Pascal programs to be executed on the Java Virtual Machine.

## Overview

This project implements a complete compilation pipeline that includes:
- Lexical analysis (tokenization)
- Syntax analysis (parsing)
- Semantic analysis
- Bytecode generation

The compiler converts a simplified version of Pascal into Jasmin assembly code, which can then be assembled into Java bytecode (.class files) and executed on the JVM.

## Features

- Support for basic Pascal language constructs:
  - Integer and Boolean data types
  - Arithmetic operations (+, -, *, /)
  - Logical operations (AND, OR, NOT)
  - Comparison operators (=, <>, <, >, <=, >=)
  - Control structures (IF-THEN-ELSE, WHILE-DO)
  - BEGIN-END blocks
  - Variable declarations
  - Print statements

## Project Structure

- `Lexer.java` / `LexerF.java`: Implements lexical analysis
- `Tag.java`: Defines token types
- `Token.java`: Base class for tokens
- `Word.java`: Handles keywords and identifiers
- `Number.java`: Manages numeric literals
- `Compilatore.java`: Main compiler implementation
- `CodeGenerator.java`: Generates Jasmin bytecode
- `SymbolTable.java`: Manages variable tracking
- `OpCode.java`: Defines JVM operation codes
- `Instruction.java`: Represents bytecode instructions

## Requirements

- Java Development Kit (JDK)
- Jasmin assembler (included as `jasmin.jar`)

## Usage

1. Write your Pascal code in a `.pas` file
2. Compile the Pascal code to Jasmin assembly:
```bash
java Compilatore < input.pas > output.j
```

3. Assemble the Jasmin code to JVM bytecode:
```bash
java -jar jasmin.jar output.j
```

4. Run the compiled program:
```bash
java OutputClassName
```

## Example

Input (`example.pas`):
```pascal
integer x, y;
begin
    x := 10;
    y := 20;
    print(x + y)
end
```

This will compile to JVM bytecode and when executed will print `30`.

## Implementation Details

The compiler follows these main steps:

1. **Lexical Analysis**: Converts source code into tokens
2. **Parsing**: Validates syntax and builds an abstract representation
3. **Code Generation**: Converts the program into JVM bytecode instructions
4. **Symbol Management**: Tracks variables and their properties

## Limitations

- Limited subset of Pascal language features
- Single file compilation only
- Basic error reporting
- No optimization passes