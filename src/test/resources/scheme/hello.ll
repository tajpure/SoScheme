; ModuleID = './test/hello.scm'

@.str = global [13 x i8] c"hello world!\00"

define i32 @main() {
entry:
  %call = call i32 @printf(i8* getelementptr inbounds ([13 x i8]* @.str, i32 0, i32 0))
  ret i32 %call
}

declare i32 @printf(i8*)
