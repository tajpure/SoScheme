; ModuleID = 'D:/workspace/workspace11/SoScheme/test/double.scm'

define i32 @double(i32, i32) {
entry:
  %mul = mul i32 %1, 2
  %add = add i32 %0, %mul
  ret i32 %add
}

define i32 @main() {
entry:
  %call = call i32 @double(i32 1, i32 2)
  ret i32 %call
}
