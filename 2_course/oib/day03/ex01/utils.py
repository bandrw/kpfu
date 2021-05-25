import math
import random
import socket

BLUE_BACK = "\033[44m"
RED_BACK = "\033[41m"
RED = "\033[0;31m"
NULL = "\033[0m"
BOLD = "\033[1m"


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


def receive_message(s: socket.socket):
	try:
		data = s.recv(4)
		bytes_to_receive = int.from_bytes(data, "big")
		data = s.recv(bytes_to_receive)
		return data
	except Exception as e:
		print(f"\n{RED}Error: {e}{NULL}")


def send_message(s: socket.socket, data: bytes):
	try:
		s.sendall(len(data).to_bytes(4, "big"))
		s.sendall(data)
	except Exception as e:
		print(f"\n{RED}Error: {e}{NULL}")
