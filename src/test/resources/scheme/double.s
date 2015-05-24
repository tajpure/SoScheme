	.def	 @feat.00;
	.scl	3;
	.type	0;
	.endef
	.globl	@feat.00
@feat.00 = 1
	.def	 _fact;
	.scl	2;
	.type	32;
	.endef
	.text
	.globl	_fact
	.align	16, 0x90
_fact:                                  # @fact
# BB#0:                                 # %entry
	pushl	%eax
	cmpl	$1, 8(%esp)
	jne	LBB0_2
# BB#1:                                 # %then
	movl	$_.str, (%esp)
	jmp	LBB0_3
LBB0_2:                                 # %else
	movl	$_.str1, (%esp)
LBB0_3:                                 # %end
	calll	_printf
	popl	%edx
	ret

	.def	 _main;
	.scl	2;
	.type	32;
	.endef
	.globl	_main
	.align	16, 0x90
_main:                                  # @main
# BB#0:                                 # %entry
	pushl	%eax
	movl	$1, (%esp)
	calll	_fact
	popl	%edx
	ret

	.data
	.globl	_.str                   # @.str
_.str:
	.asciz	"n equals 1"

	.globl	_.str1                  # @.str1
	.align	16
_.str1:
	.asciz	"n doesn't equal 1"


