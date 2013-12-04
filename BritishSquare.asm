#	Author:Keenan Lee kbl3236
#	Date:5/3/2013
#	n
#


# CONSTANTS
#
# syscall codes

PRINT_INT = 	1
PRINT_STRING = 	4
READ_INT = 	5
EXIT = 		10

	.data

	.align	2
board:	.word	0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24

player1_tot:
	.word	0

player2_tot:
	.word	0

player_in:
	.word	-1

	.align	0
star_line:
	.asciiz "****************************"

welcome:
	.asciiz "**     British Square     **"

board_top:
	.asciiz "***********************"

board_div:
	.asciiz "*+---+---+---+---+---+*"

player1_sig:
	.asciiz "XXX"

player2_sig:
	.asciiz "OOO"

empty_sig:
	.asciiz "   "

num_sig:
	.asciiz "  "

player1_mes:
	.asciiz "Player X enter a move (-2 to quit, -1 to skip move) : "

player2_mes:
	.asciiz "Player O enter a move (-2 to quit, -1 to skip move) : "

player1_skip:
	.asciiz "Player X has no legal moves, turn skipped."

player2_skip:
	.asciiz "Player O had no legal moves, turn skipped."

game_tot:
	.asciiz "Game Totals"

x_tot:	
	.asciiz	"X's total="

o_tot:	
	.asciiz "O's total="

player1_quit:
	.asciiz	"Player X quit the game."

player2_quit:
	.asciiz "Player O quit the game."

player1_win:
	.asciiz "**     Player X wins      **"

player2_win:
	.asciiz "**     Player O wins      **"

game_tie:
	.asciiz "**     Game is a tie      **"

new_line:
	.asciiz "\n"

space:
	.asciiz " " 

star:
	.asciiz "*"

parr_div:
	.asciiz "|"


error1:
	.asciiz "Illegal move, can't place first stone of game in middle square"

error2:
	.asciiz "Illegal location, try again"

error3: 
	.asciiz "Illegal move, square is occupied"

error4:
	.asciiz "Illegal move, square is blocked"


	.text
	.align	2

	.globl	main

#
# Name:		MIAN PROGRAM
#
# Description:	main logic for the program.
#
#	Program for playing the game British squares
#

main:
	addi	$sp, $sp, -12	#allocate space for the return adress
	sw	$ra, 8($sp)
	sw	$s1, 4($sp)
	sw	$s0, 0($sp)

	li	$v0, PRINT_STRING

	la	$a0, star_line
	syscall

	la	$a0, new_line
	syscall

	la	$a0, welcome
	syscall

	la	$a0, new_line
	syscall

	la	$a0, star_line
	syscall

	la	$a0, new_line
	syscall

	la	$s0, board
	jal	print_board

	la	$a0, new_line
	syscall

main_loop:

player1_start:
	la	$a0, player1_mes
	syscall

	la	$a0, new_line
	syscall

	#li	$a0, 0
	#jal	still_playable

	beq	$v0, $zero,player2_start

	li	$v0, READ_INT
	syscall
	move	$s4,$v0
	
	li	$v0, PRINT_STRING
	li	$t8, -1
	beq	$t8, $s4, player2_start

	li	$t8, -2
	beq	$t8, $s4, p1_quit

	move	$a0, $s4
	li	$a1, 0
	jal	check_input
	move	$s5, $v0
	li	$v0, PRINT_STRING
	la	$a0, new_line
	syscall
	beq	$s5, $zero, player1_start

	li	$t4, 4
	li	$t3, -1
	mult	$t4, $s4

	mflo	$t4

	add	$s0, $s0, $t4
	sw	$t3, 0($s0)
	sub	$s0, $s0, $t4

	lw	$t4, player1_tot
	addi	$t4, $t4, 1
	sw	$t4, player1_tot


	li	$v0, PRINT_STRING
	jal	print_board

	la	$a0, new_line
	syscall

player2_start:

	la	$a0, player2_mes
	syscall

	li	$v0, READ_INT
	syscall
	
	move	$s5, $v0
	#li	$t8, -1
	#beq	$t8,$s5, player1_start

	#li	$t8, -2
	#beq	$t8, $s5, p2_quit

	li	$v0, PRINT_STRING
	li	$a1, 1
	#jal	check_input
	#move	$s5, $v0
	li	$v0, PRINT_STRING
	la	$a0, new_line
	syscall
	#beq	$s5, $zero, player2_start

	li	$t4, 4
	li	$t3, -2
	mult	$t4, $s7

	mflo	$t4

	add	$s0, $s0, $t4
	sw	$t3, 0($s0)
	sub	$s0, $s0, $t4

	lw	$t4, player2_tot
	addi	$t4, $t4, 1
	sw	$t4, player2_tot

	li	$v0, PRINT_STRING
	jal	print_board

	la	$a0, new_line
	syscall

	j	main_loop

p1_quit:
	li	$v0, PRINT_STRING
	la	$a0, player1_quit
	syscall
	j	main_done

p2_quit:
	li	$v0, PRINT_STRING
	la	$a0, player2_quit
	syscall
	j	main_done


main_done:
	li	$v0, PRINT_STRING
	la	$a0, game_tot
	syscall

	la	$a0, new_line
	syscall

	la	$a0, x_tot
	syscall

	li	$v0, PRINT_INT
	la	$a0, player1_tot
	syscall

	li	$v0, PRINT_STRING
	la	$a0, space
	syscall

	la	$a0, o_tot
	syscall

	li	$v0, PRINT_INT
	la	$a0, player2_tot
	syscall

	li	$v0, PRINT_STRING
	la	$a0, new_line
	syscall

	lw	$ra, 8($sp)
	lw	$s1, 4($sp)
	lw	$s0, 0($sp)
	addi	$sp, $sp, 12



	li	$v0, EXIT
	syscall

# prints out the board in its current state to output
print_board:
	li	$v0, PRINT_STRING

	la	$a0, new_line
	syscall

	la	$a0, board_top
	syscall

	la	$a0, new_line
	syscall

	la	$a0, board_div
	syscall

	la	$a0, new_line
	syscall

	la	$a0, star
	syscall

	la	$a0, parr_div	
	syscall
	
	move	$t0, $s0
	li	$t1, 0
	li	$t2, 0
	li	$t3, 4
	li	$t5, 5
	li	$t6, 10
	li	$t7, 0

box:
	beq	$t1, $t6, div_point
	bne	$t1, $t5, balen
	addi	$t0, $t0, -20

	la	$a0, star
	syscall

	la	$a0, new_line
	syscall

	la	$a0, star
	syscall

	la	$a0, parr_div
	syscall

balen:
	lw	$t7, 0($t0)
	addi	$t0, $t0, 4

	li	$t4, -1
	beq	$t7, $t4, x_box
	
	li	$t4, -2
	beq	$t7, $t4, o_box
	blt	$t1, $t5, first_row
	
	li	$v0, PRINT_INT
	add	$a0, $t7, $zero
	syscall
	li	$v0, PRINT_STRING

	li	$t4, 9
	bge	$t4, $t7, sing_sig
	la	$a0, space
	syscall
	j	box_done

sing_sig:
	la	$a0, num_sig
	syscall
	j	box_done
first_row:
	la	$a0,empty_sig
	syscall
	j	box_done
x_box:
	la	$a0, player1_sig
	syscall
	j	box_done
o_box:
	la	$a0, player2_sig
	syscall
	j	box_done
box_done:
	addi	$t1, $t1, 1
	
	la	$a0, parr_div
	syscall
	j	box

div_point:
	beq	$t2, $t3, end_print

	li	$t1, 0
	addi	$t2, $t2, 1

	la	$a0, star
	syscall

	la	$a0, new_line
	syscall
	
	la	$a0, board_div
	syscall

	la	$a0, new_line
	syscall

	la	$a0, star
	syscall

	la	$a0, parr_div
	syscall

	j box

end_print:
	la	$a0, star
	syscall
	
	la	$a0, new_line
	syscall

	la	$a0, board_div
	syscall

	la	$a0, new_line
	syscall

	la	$a0, board_top
	syscall

	la	$a0, new_line
	syscall

	jr	$ra

# $a0 should contain the location a paice will be placed
# $a1 will contion the player placing it, 
#     0 for player1, another number other wise
# will return 0 for a non-valid spot
check_input:
	addi	$sp, $sp, -20
	sw	$ra, 16($sp)
	sw	$s3, 12($sp)
	sw	$s2, 8($sp)
	sw	$s1, 4($sp)
	sw	$s0, 0($sp)

	la	$s0, board
	li	$t9, 4
	mult	$a0, $t9

	mflo	$s1
	add	$s0, $s0, $s1
	lw	$s2, 0($s0)
	ble	$s2, $zero, err3
	
	blt	$a0, $zero, err2
	li	$s2, 24
	bgt	$a0, $s2, err2

	li	$v0, 1
	j	done_check

err2:
	la	$a0, error2
	syscall
	li	$v0, 0
	j	done_check


err3:
	la	$a0, error3
	syscall
	li	$v0, 0
	j	done_check

done_check:
	lw	$s0, 0($sp)
	lw	$s1, 4($sp)
	lw	$s2, 8($sp)
	lw	$s3, 12($sp)
	lw	$ra, 16($sp)
	addi	$sp, $sp, 20
	jr	$ra


# $a0 should be a the location that needs to be checked
# $a1 should be the player placing a peice, 0 for X
valid_spot:
	li	$t9, 4
	mult	$t9, $a0

	mflo	$t9
	
	add	$s0, $s0, $t9

	beq	$a1, $zero, setX
	addi	$t0, $zero, -1
	j	check
setX:
	addi	$t0, $zero, -2

check:
	li	$t1, 4
	lw	$t2, 0($s0)
	bgt	$s0, $t1, top
top:
	lw	$t2, -20($s0)
	beq	$t2, $t0, bad_done


#left:
#	lw	$t2, -4($s0)

#right:
#	lw	$t2, 4($s0)

	li	$t1, 20
	bgt	$s0, $t1,good_done

bottom:
	lw	$t2, 20($s0)
	beq	$t2, $t0, bad_done

good_done:
	li	$v0, 1
	sub	$s0, $s0, $t9
	jr	$ra

bad_done:
	li	$v0, 0
	sub	$s0, $s0, $t9
	jr	$ra


# $a0 should be the player whos turn it is
#
#
still_playable:
	addi	$sp, $sp, -4
	sw	$ra, 0($sp)

	li	$t8, 24
	li	$t7, 0
	move	$a1, $a0
loop:
	bgt	$t7, $t8, end
	move	$a0, $t7
	
	jal	valid_spot	

	beq	$v0, $zero, not_play

	j	loop

not_play:
	lw	$ra, 0($sp)
	addi	$sp, $sp, 4
	jr	$ra

end:
	li	$v0, 1
	lw	$ra, 0($sp)
