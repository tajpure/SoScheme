; ModuleID = 'D:/workspaceII/SoScheme/test/double.scm'

define i32 @double(i32) {
entry:
  %mul = mul i32 %0, %0
  ret i32 %mul
}
