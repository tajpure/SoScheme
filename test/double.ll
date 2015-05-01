; ModuleID = 'D:/workspaceII/SoScheme/test/double.scm'

define i32 @strr() {
entry:
  %s = alloca i1
  store i1 false, i1* %s
  %ret = alloca i32
  %void = load i32* %ret
  ret i32 %void
}
