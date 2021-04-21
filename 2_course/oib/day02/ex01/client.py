#!/usr/bin/env python3

import socket
import sys
import math


RED_BACK = "\033[41m"
BLUE_BACK = "\033[44m"
RED = "\033[0;31m"
NULL = "\033[0m"

def usage_error():
	print(f"Usage: ./{sys.argv[0].strip('./')} <host:port>")
	exit(1)


def bytes_len(nbr):
	if nbr == 0:
		return 1
	return math.ceil(nbr.bit_length() / 8)


def main():
	if len(sys.argv) != 2:
		usage_error()
	arr = sys.argv[1].split(':')
	if len(arr) != 2:
		usage_error()
	host = arr[0]
	port = int(arr[1])
	with socket.socket() as s:
		s.connect((host, port))
		print(f"{BLUE_BACK} {NULL} Connected to {host}:{port}")
		while True:
			try:
				nbr = int(input("Enter natural number: ").strip())
				if nbr <= 0:
					raise Exception("Can't check negative number")
				line = input("Enter rounds count (enter to skip): ").strip()
				if line == "":
					rounds = None
				else:
					rounds = int(line)
					if rounds < 0:
						raise Exception("Invalid rounds count")
				argc = 1
				if rounds:
					argc = 2
				s.sendall(argc.to_bytes(4, "big"))  # sending numbers count
				s.sendall(bytes_len(nbr).to_bytes(4, "big"))  # sending first number's length
				if rounds:
					s.sendall(bytes_len(rounds).to_bytes(4, "big"))  # sending second number's length
				s.sendall(nbr.to_bytes(bytes_len(nbr), "big"))
				if rounds:
					s.sendall(rounds.to_bytes(bytes_len(rounds), "big"))
				b_response_len = s.recv(4)
				if not b_response_len:
					break
				response_len = int.from_bytes(b_response_len, "big")
				b_response = s.recv(response_len)
				if not b_response:
					break
				response = str(b_response, "utf-8")
				print(f"{RED_BACK} {NULL} {response}")
			except Exception as ex:
				print(f"{RED}Error: {ex}{NULL}")


if __name__ == "__main__":
	try:
		main()
	except Exception as e:
		print(f"{RED}Error: {e}{NULL}", file=sys.stderr)
	except KeyboardInterrupt:
		print()
		exit(0)
