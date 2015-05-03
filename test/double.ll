; ModuleID = 'D:/workspaceII/SoScheme/test/double.scm'

define i32 @fact(i32) {
entry:
  ret void
  %call = call i32 @fact(i32 10)
}

declare i32 @anonymous(i32)
