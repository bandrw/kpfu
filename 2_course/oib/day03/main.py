#!/usr/bin/env python3

import random
from utils import quick_power_mod, is_prime

CHARS_IN_SEGMENT = 2


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
	d = 3
	tmp = extended_euclid(fi, e)[2]
	while True:
		if (d * e) % fi == 1 and (d % fi == tmp or fi - (d % fi) == -tmp):
			return d
		d += 1


def generate_prime(length):
	while True:
		n = random.getrandbits(length)
		if n > 0 and is_prime(n):
			return n
		if n > 0 and is_prime(n + 1):
			return n + 1


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


def main():
	public_key, private_key = keygen(length=10, min_n=(2 ** (CHARS_IN_SEGMENT * 8)))
	print(f"Public key: {public_key}, private key: {private_key}")

	string = "Hello, world!!!"
	segments = str_to_segments(string)
	print(segments)
	encrypted_segments = []
	for segment in segments:
		encrypted_segments.append(encrypt(segment, public_key))
	print(encrypted_segments)
	decrypted_segments = []
	for segment in encrypted_segments:
		decrypted_segments.append(decrypt(segment, private_key))
	print(decrypted_segments)
	print(segments_to_str(decrypted_segments))

	# nbr = 2 ** 16
	# encrypted_nbr = encrypt(nbr, public_key)
	# decrypted_nbr = decrypt(encrypted_nbr, private_key)
	# print(f"{nbr} -> {encrypted_nbr} -> {decrypted_nbr}")


if __name__ == "__main__":
	try:
		main()
	except KeyboardInterrupt:
		print()
