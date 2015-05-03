; ModuleID = 'D:/workspaceII/SoScheme/test/hello.scm'

define i32 @addOne(i32) {
entry:
  %x = alloca i32
  store i32 1, i32* %x
  %call = call i32 @printf(i32* %x)
  ret i32 %call
}

declare i32 @printf(i8*)
