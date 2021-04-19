import math
import random

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
RESULT_STR = f"{RED_BACK} Result {NULL} "

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
		print(f"{RESULT_STR} {nbr} - вероятно простое")
	else:
		print(f"{RESULT_STR} {nbr} - составное")


def main():
	print(f"{BOLD}∙ Main menu ∙{NULL}")
	mode = input("Enter mode (1 - 3): ").strip()
	if mode == "1":
		console_input()
	elif mode == "2":
		pass
	elif mode == "3":
		pass
	else:
		raise Exception("Invalid mode")


if __name__ == "__main__":
	print(MODES_INFO)
	while True:
		try:
			main()
		except Exception as e:
			print(f"{RED}Error: {e}{NULL}")
		except KeyboardInterrupt:
			print()
			exit(0)
		print()
