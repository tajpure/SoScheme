; ModuleID = 'D:/workspace/workspace11/SoScheme/test/double.scm'

define i32 @double(i32*) {
entry:
  %arg0 = load i32* %0
  %mul = mul i32 %arg0, 2
  ret i32 %mul
}
