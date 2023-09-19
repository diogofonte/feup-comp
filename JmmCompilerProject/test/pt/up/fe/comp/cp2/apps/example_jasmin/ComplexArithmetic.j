.class public Simple
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public add(II)I
    .limit stack 2
    .limit locals 4
    iload_1
    iload_2
    iadd
    ireturn
.end method

.method public sub(II)I
    .limit stack 2
    .limit locals 4
    iload_1
    iload_2
    isub
    ireturn
.end method

.method public mult(II)I
    .limit stack 2
    .limit locals 4
    iload_1
    iload_2
    imul
    ireturn
.end method

.method public div(II)I
    .limit stack 2
    .limit locals 4
    iload_1
    iload_2
    idiv
    ireturn
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 2
    .limit locals 7
    bipush 10
    istore_1
    bipush 5
    istore_2
    new Simple
    dup
    invokespecial Simple/<init>()V
    astore_3
    aload_3
    astore 4
    aload 4
    iload_1
    iload_2
    invokevirtual Simple/add(II)I
    istore 5
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 5
    invokevirtual java/io/PrintStream/println(I)V
    aload 4
    iload_1
    iload_2
    invokevirtual Simple/sub(II)I
    istore 6
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 6
    invokevirtual java/io/PrintStream/println(I)V
    aload 4
    iload_1
    iload_2
    invokevirtual Simple/mult(II)I
    istore 7
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 7
    invokevirtual java/io/PrintStream/println(I)V
    aload 4
    iload_1
    iload_2
    invokevirtual Simple/div(II)I
    istore 8
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 8
    invokevirtual java/io/PrintStream/println(I)V
    return
.end method
