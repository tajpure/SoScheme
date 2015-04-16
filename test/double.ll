; ModuleID = 'D:/workspace/workspace11/SoScheme/test/double.scm'

%Any = type { i32, i8, i32, float }

define %Any* @double(%Any*) {
entry:
  %n = alloca %Any
  %typeP = getelementptr %Any* %n, i32 0, i32 0
  store i32 10098, i32* %typeP
  %typeV = load i32* %typeP
  %valueP = getelementptr %Any* %n, i32 0, i32 2
  %valueV = load i32* %valueP
  %add = add i32 %valueV, 2
  ret i32 %add
}
