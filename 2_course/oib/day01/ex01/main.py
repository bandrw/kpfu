def is_alpha(ch):
	return ord('a') <= ord(ch) <= ord('z') or ord('A') <= ord(ch) <= ord('Z')


def decrypt_char(ch, shift):
	upper = False
	if ch.isupper():
		upper = True
		ch = chr(ord(ch) + ord('a') - ord('A'))
	replacement = ord(ch) - shift
	while replacement < ord('a'):
		replacement += ord('z') - ord('a') + 1
	if upper:
		replacement -= (ord('a') - ord('A'))
	return chr(replacement)


def decrypt(text, shift):
	res = ""
	for ch in text:
		if is_alpha(ch):
			res += decrypt_char(ch, shift)
		else:
			res += ch
	return res


def encrypt_char(ch, shift):
	upper = False
	if ch.isupper():
		upper = True
		ch = ch.lower()
	replacement = ord(ch) + shift
	while replacement > ord('z'):
		replacement -= ord('z') - ord('a') + 1
	if upper:
		replacement -= (ord('a') - ord('A'))
	return chr(replacement)


def encrypt(text, shift):
	res = ""
	for ch in text:
		if is_alpha(ch):
			res += encrypt_char(ch, shift)
		else:
			res += ch
	return res


def brute_force(text):
	for i in range(26):
		print("[Shift: {}]\t\"{}\"".format(i, decrypt(text[0:20], i)))
	print("Введите сдвиг: ")
	shift = int(input("> "))
	if shift < 0 or shift > 25:
		raise Exception("invalid shift")
	print("Result:\n{}".format(decrypt(text, shift)))


def main():
	shift = 0
	print("Для шифрования введите 1, для расшифрования - 2, для перебора - 3")
	mode = input("> ").strip()
	if mode != "1" and mode != "2" and mode != "3":
		raise Exception("invalid mode")
	if mode != "3":
		print("Введите сдвиг: ")
		shift = int(input("> "))
		if shift <= 0:
			raise Exception("invalid shift")
		shift = shift % 26
	print("Введите текст:")
	text = input("> ")
	if mode == "1":
		print("Result:\n{}".format(encrypt(text, shift)))
	elif mode == "2":
		print("Result:\n{}".format(decrypt(text, shift)))
	else:
		brute_force(text)


if __name__ == "__main__":
	while True:
		try:
			main()
		except Exception as e:
			print("Error: {}".format(e))
		except KeyboardInterrupt:
			print()
			exit(0)
		print()
