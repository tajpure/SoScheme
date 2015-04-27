; ModuleID = 'D:/workspace/workspace11/SoScheme/test/double.scm'

define i32* @double(i32*, i32*) {
entry:
  %mul = mul i32* %0, i32 2
  %add = add i32* %mul, %1
  ret i32* %add
}
