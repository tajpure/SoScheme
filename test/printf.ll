target triple = "i386-pc-linux-gnu"

@.str = private constant [17 x i8] c"hello, world!%d\0A\00"
define i32 @factorial(i32 %n) {
entry:
%iszero = icmp eq i32 %n, 0
br i1 %iszero, label %return1, label %recurse
return1:
ret i32 1
recurse:
%nminus1 = add i32 %n, -1
%factnminusone =
call i32 @factorial(i32 %nminus1)
%factn = mul i32 %n, %factnminusone
ret i32 %factn
}
define i32 @main() {
entry:
  %ff = call i32 @factorial(i32 13)
  %str = getelementptr inbounds [17 x i8]* @.str, i32 0, i32 0
  %call = call i32 (i8*, ...)* @printf(i8* %str, i32 %ff)
  ret i32 1
}

declare i32 @printf(i8*, ...)
