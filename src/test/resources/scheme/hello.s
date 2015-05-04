	.def	 @feat.00;
	.scl	3;
	.type	0;
	.endef
	.globl	@feat.00
@feat.00 = 1
	.def	 _main;
	.scl	2;
	.type	32;
	.endef
	.text
	.globl	_main
	.align	16, 0x90
_main:                                  # @main
# BB#0:                                 # %entry
	pushl	%eax
	movl	$_.str, (%esp)
	calll	_printf
	popl	%edx
	ret

	.data
	.globl	_.str                   # @.str
_.str:
	.asciz	"hello world!"


