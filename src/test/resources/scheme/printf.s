	.file	"printf.ll"
	.text
	.globl	factorial
	.align	16, 0x90
	.type	factorial,@function
factorial:                              # @factorial
	.cfi_startproc
# BB#0:                                 # %entry
	pushl	%esi
.Ltmp2:
	.cfi_def_cfa_offset 8
	subl	$8, %esp
.Ltmp3:
	.cfi_def_cfa_offset 16
.Ltmp4:
	.cfi_offset %esi, -8
	movl	16(%esp), %esi
	testl	%esi, %esi
	je	.LBB0_1
# BB#3:                                 # %recurse
	leal	-1(%esi), %eax
	movl	%eax, (%esp)
	calll	factorial
	imull	%esi, %eax
	jmp	.LBB0_2
.LBB0_1:                                # %return1
	movl	$1, %eax
.LBB0_2:                                # %return1
	addl	$8, %esp
	popl	%esi
	ret
.Ltmp5:
	.size	factorial, .Ltmp5-factorial
	.cfi_endproc

	.globl	main
	.align	16, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# BB#0:                                 # %entry
	subl	$12, %esp
.Ltmp7:
	.cfi_def_cfa_offset 16
	movl	$13, (%esp)
	calll	factorial
	movl	%eax, 4(%esp)
	movl	$.L.str, (%esp)
	calll	printf
	movl	$1, %eax
	addl	$12, %esp
	ret
.Ltmp8:
	.size	main, .Ltmp8-main
	.cfi_endproc

	.type	.L.str,@object          # @.str
	.section	.rodata,"a",@progbits
	.align	16
.L.str:
	.asciz	"hello, world!%d\n"
	.size	.L.str, 17


	.section	".note.GNU-stack","",@progbits
