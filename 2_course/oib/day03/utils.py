import math
import random


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
