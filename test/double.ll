; ModuleID = 'D:/workspace/workspace11/SoScheme/test/double.scm'

%Any = type { i32, i8, i32, float }

define %Any* @double(%Any*) {
entry:
  %n = alloca %Any
  %typeP = getelementptr %Any* %n, i32 0, i32 0
  %typeV = load i32* %typeP
  %valueP = getelementptr %Any* %n, i32 0, i32 1
  %valueV = load i8* %valueP
  %add = add i8 %valueV, i32 2
  ret i8 %add
}

define %Any* @double1(%Any*) {
entry:
  %n = alloca %Any
  %typeP = getelementptr %Any* %n, i32 0, i32 0
  %typeV = load i32* %typeP
  %valueP = getelementptr %Any* %n, i32 0, i32 1
  %valueV = load i8* %valueP
  %mult = mul i8 %valueV, i32 2
  ret i8 %mult
}
