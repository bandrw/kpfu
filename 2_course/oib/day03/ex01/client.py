#!/usr/bin/env python3 -u

import datetime
import random
import socket
import sys

from utils import quick_power_mod, is_prime, BLUE_BACK, NULL, RED_BACK, RED, send_message, receive_message

CHARS_IN_SEGMENT = 4


def get_public_exp(fi):
	while True:
		n = random.randint(1, fi - 1)
		if fi % n != 0 and is_prime(n):
			return n


def extended_euclid(a, b):
	x0, y0 = 1, 0
	x1, y1 = 0, 1
	while b != 0:
		q, r = divmod(a, b)
		a, b = b, r
		x0, x1 = x1, x0 - q * x1
		y0, y1 = y1, y0 - q * y1
	return a, x0, y0


def get_private_exp(e, fi):
	tmp = extended_euclid(fi, e)[2]
	d = tmp
	while True:
		if d > 0 and (d * e) % fi == 1 and (d % fi == tmp or fi - (d % fi) == -tmp):
			return d
		d += 1


def generate_prime(length):
	while True:
		n = random.getrandbits(length)
		if n & 1 == 0:
			n += 1
		if n > 0 and is_prime(n):
			return n


def keygen(length, min_n):
	p = None
	q = None
	n = -1
	while n < min_n:
		p = generate_prime(length)
		while p < 3:
			p = generate_prime(length)
		q = generate_prime(length)
		while q < 3:
			q = generate_prime(length)
		n = p * q
	fi = (p - 1) * (q - 1)
	e = get_public_exp(fi)
	if extended_euclid(fi, e)[2] < 0:
		return keygen(length, min_n)
	d = get_private_exp(e, fi)
	# print(f"p = {p}, q = {q}, fi = {fi}, e = {e}, d = {d}")
	return (e, n), (d, n)


def encrypt(nbr, public_key):
	e, n = public_key
	return quick_power_mod(nbr, e, n)


def decrypt(nbr, private_key):
	d, n = private_key
	return quick_power_mod(nbr, d, n)


def str_to_segments(string):
	segments = []
	i = 0
	while i < len(string):
		tmp = string[i : i + CHARS_IN_SEGMENT]
		j = 0
		segment = 0
		while j < CHARS_IN_SEGMENT:
			if j < len(tmp):
				segment += ord(tmp[j])
			if j < CHARS_IN_SEGMENT - 1:
				segment <<= 8
			j += 1
		segments.append(segment)
		i += CHARS_IN_SEGMENT
	return segments


def segments_to_str(segments):
	string = ""
	for segment in segments:
		tmp = ""
		for _ in range(CHARS_IN_SEGMENT):
			if segment & 0xFF:
				tmp += chr(segment & 0xFF)
			segment >>= 8
		string += tmp[::-1]
	return string


def login(s, nickname, public_key):
	send_message(s, f"LOGIN {nickname}".encode("utf-8"))
	send_message(s, public_key[0].to_bytes(128, "big"))
	send_message(s, public_key[1].to_bytes(128, "big"))
	data = receive_message(s)
	response = data.decode("utf-8")
	if response != "SUCCESS":
		print(f"{RED}Login failed{NULL}")
		return False
	print(f"{BLUE_BACK} {NULL} Logged in")
	return True


def alice_handle(s, public_key):
	if not login(s, "ALICE", public_key):
		exit(1)
	bobs_key = int.from_bytes(receive_message(s), "big"), int.from_bytes(receive_message(s), "big")
	while True:
		text = input("Enter message: ")
		segments = str_to_segments(text)
		encrypted_segments = []
		for segment in segments:
			encrypted_segments.append(encrypt(segment, bobs_key))
		for segment in encrypted_segments:
			send_message(s, segment.to_bytes(64, "big"))
		send_message(s, (0).to_bytes(4, "big"))


def bob_handle(s, public_key, private_key):
	if not login(s, "BOB", public_key):
		exit(1)
	while True:
		data = receive_message(s)
		response = int.from_bytes(data, "big")
		text = segments_to_str([decrypt(response, private_key)])
		print(f"[{datetime.datetime.now().strftime('%H:%M:%S')}] New message: {text}", end="")
		while int.from_bytes(data, "big") != 0:
			data = receive_message(s)
			response = int.from_bytes(data, "big")
			text = segments_to_str([decrypt(response, private_key)])
			print(text, end="")
		print()


def main():
	global CHARS_IN_SEGMENT
	host = ""
	port = 0
	key_len = 32
	try:
		if len(sys.argv) == 2 or len(sys.argv) == 3:
			arr = sys.argv[1].split(':')
			host = arr[0]
			port = int(arr[1])
			if len(sys.argv) == 3:
				key_len = int(sys.argv[2])
				CHARS_IN_SEGMENT = key_len // 8
		else:
			raise Exception("Usage error")
	except:
		print(f"Usage: ./{sys.argv[0].strip('./')} <host:port> [key length]")
		exit(1)
	public_key, private_key = keygen(length=key_len, min_n=(2 ** (CHARS_IN_SEGMENT * 8)))
	print(f"Public key: {public_key}, private key: {private_key}")
	with socket.socket() as s:
		s.connect((host, port))
		print(f"{BLUE_BACK} {NULL} Connected to {host}:{port}")
		nickname = input("Enter your nickname (Bob / Alice): ").strip()
		if nickname == "Alice":
			alice_handle(s, public_key)
		elif nickname == "Bob":
			bob_handle(s, public_key, private_key)


if __name__ == "__main__":
	try:
		main()
	except KeyboardInterrupt:
		print()
	except Exception as e:
		print(f"{RED}Error: {e}{NULL}")
