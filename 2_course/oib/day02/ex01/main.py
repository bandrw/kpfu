#!/usr/bin/env python3

import math
import random
import os
import socket
import sys

BLUE_BACK = "\033[44m"
RED_BACK = "\033[41m"
RED = "\033[0;31m"
NULL = "\033[0m"
BOLD = "\033[1m"

MODES_INFO = f"""\
{BLUE_BACK} {NULL} {'Console input: 1':>16s} {BLUE_BACK} {NULL}
{BLUE_BACK} {NULL} {'File input: 2':>16s} {BLUE_BACK} {NULL}
{BLUE_BACK} {NULL} {'Server input: 3':>16s} {BLUE_BACK} {NULL}
"""

HOST = "127.0.0.1"


def quick_power_mod(nbr, power, n):
	if nbr <= 0 or power <= 0:
		return 0
	res = 1
	while power > 0:
		if power & 1:
			res = res * nbr % n
		nbr = nbr * nbr % n
		power = power >> 1
	return res


def is_prime(nbr, rounds=None):
	if 1 <= nbr <= 3:
		return True
	if rounds is None:
		rounds = int(math.log(nbr, 2)) + 1
	t = nbr - 1
	s = 0
	while t % 2 == 0:
		t /= 2
		s += 1
	for _ in range(rounds):
		a = random.randint(2, nbr - 2)
		x = quick_power_mod(a, int(t), nbr)
		if x == 1 or x == nbr - 1:
			continue
		for _ in range(s - 1):
			x = quick_power_mod(x, 2, nbr)
			if x == 1:
				return False
			if x == nbr - 1:
				break
		if x != nbr - 1:
			return False
	return True


def console_input():
	line = input("Enter natural number: ").strip()
	nbr = int(line)
	if nbr <= 0:
		raise Exception("Can't check negative number")
	line = input("Enter rounds count (enter to skip): ").strip()
	if line == "":
		rounds = None
	else:
		rounds = int(line)
	if is_prime(nbr, rounds):
		print(f"{RED_BACK} {NULL} {nbr} - вероятно простое")
	else:
		print(f"{RED_BACK} {NULL} {nbr} - составное")


def file_input():
	filename = input("Enter file name: ").strip()
	with open(filename, "r") as f:
		lines = f.readlines()
	out_text = ""
	for line in lines:
		arr = line.split()
		if len(arr) not in [1, 2]:
			raise Exception(f"File parsing error ({filename})")
		out_text += f"{arr[0]}"
		rounds = None
		if len(arr) == 2:
			rounds = int(arr[1])
			out_text += f" {arr[1]}"
		if is_prime(int(arr[0]), rounds):
			out_text += " - возможно простое"
		else:
			out_text += " - составное"
		out_text += "\n"
	with open("out.txt", "w") as f:
		f.write(out_text)
	print(f"{RED_BACK} {NULL} Result written to {os.getcwd() + '/out.txt'}")


def server_input():
	with socket.socket() as s:
		try:
			s.bind((HOST, 0))
			_, port = s.getsockname()
			s.listen()
			print(f"{BLUE_BACK} {NULL} Server listening on {HOST}:{port}")
			conn, addr = s.accept()
			with conn:
				print(f"{BLUE_BACK} {NULL} Connected by {addr[0]}:{addr[1]}")
				while True:
					b_argc = conn.recv(4)
					if not b_argc:
						break
					argc = int.from_bytes(b_argc, "big")
					b_nbr_len = conn.recv(4)
					if not b_nbr_len:
						break
					nbr_len = int.from_bytes(b_nbr_len, "big")
					if argc == 2:
						b_rounds_len = conn.recv(4)
						if not b_rounds_len:
							break
						rounds_len = int.from_bytes(b_rounds_len, "big")
					b_nbr = conn.recv(nbr_len)
					if not b_nbr:
						break
					nbr = int.from_bytes(b_nbr, "big")
					rounds = None
					if argc == 2:
						b_rounds = conn.recv(rounds_len)
						if not b_rounds:
							break
						rounds = int.from_bytes(b_rounds, "big")
					print(f"Received ({nbr}, {rounds})")
					out = f"{nbr}"
					if rounds:
						out += f" {rounds}"
					if is_prime(nbr, rounds):
						out += " - вероятно простое"
					else:
						out += " - составное"
					conn.sendall(len(out).to_bytes(4, "big"))  # sending out's len
					conn.sendall(out.encode("utf-8"))
			print(f"{BLUE_BACK} {NULL} {addr[0]}:{addr[1]} disconnected")
		except KeyboardInterrupt:
			print()
		except Exception as ex:
			print(f"{RED}Error: {ex}{NULL}")


def main():
	print(f"{BOLD}∙ Main menu ∙{NULL}")
	mode = input("Enter mode (1 - 3): ").strip()
	if mode == "1":
		console_input()
	elif mode == "2":
		file_input()
	elif mode == "3":
		server_input()
	else:
		raise Exception("Invalid mode")


if __name__ == "__main__":
	server_input()
	# print(MODES_INFO)
	# while True:
	# 	try:
	# 		main()
	# 	except Exception as e:
	# 		print(f"{RED}Error: {e}{NULL}", file=sys.stderr)
	# 	except KeyboardInterrupt:
	# 		print()
	# 		exit(0)
	# 	print()
