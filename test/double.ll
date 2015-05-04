; ModuleID = './test/double.scm'

@.str = global [11 x i8] c"n equals 1\00"
@.str1 = global [19 x i8] c"n doesn't equals 1\00"

define i32 @fact(i32) {
entry:
  %eq = icmp eq i32 %0, 1
  br i1 %eq, label %then, label %else

then:                                             ; preds = %entry
  %call = call i32 @printf(i8* getelementptr inbounds ([11 x i8]* @.str, i32 0, i32 0))
  br label %end

else:                                             ; preds = %entry
  %call1 = call i32 @printf(i8* getelementptr inbounds ([19 x i8]* @.str1, i32 0, i32 0))
  br label %end

end:                                              ; preds = %else, %then
  %result = phi i32 [ %call, %then ], [ %call1, %else ]
  ret i32 %result
}

declare i32 @printf(i8*)

define i32 @main() {
entry:
  %call = call i32 @fact(i32 %0)
  ret i32 %call
}
