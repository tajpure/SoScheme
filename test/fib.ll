; ModuleID = './test/fib.scm'

@.str = global [24 x i8] c"Test Case for fibnacci:\00"

declare i32 @printf(i8*)

define i32 @fib(i32) {
entry:
  ret void
}

define i32 @func(i32, i32) {
entry:
  ret void
  %call = call i32 @func(i32 0, i32 20)
}
