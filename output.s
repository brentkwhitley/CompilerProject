.data
.comm	x,4,4

.comm	y,4,4

.comm	z,4,4

.text
	.align 4
.globl  main
main:
main_bb2:
main_bb3:
	movl	$4, %EAX
	movl	%EAX, %EDI
	movl	$4, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb5
main_bb4:
	movl	$48, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb6:
	movl	$55, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb1:
	ret
main_bb5:
	movl	$50, %EAX
	movl	%EAX, %EDI
	call	putchar
	jmp	main_bb6
