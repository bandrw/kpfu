import random


def generate_key(file_name):
	file = open(file_name, "w")
	alphabet = []
	for i in range(ord('a'), ord('z') + 1):
		alphabet.append(chr(i))
	for i in range(ord('A'), ord('Z') + 1):
		alphabet.append(chr(i))
	shuffled = alphabet.copy()
	random.shuffle(shuffled)
	for i in range(0, 52):
		file.write("{}:{}\n".format(alphabet[i], shuffled[i]))
	file.close()
	print("{} updated".format(file_name))


def parse_key(file_name):
	key = {}
	file = open(file_name, "r")
	lines = file.readlines()
	file.close()
	if len(lines) != 52:
		raise Exception("Invalid file ({})".format(file_name))
	for line in lines:
		arr = line.strip("\n").split(":")
		if len(arr) != 2:
			raise Exception("Invalid file ({})".format(file_name))
		key[arr[0]] = arr[1]
	return key


def encrypt(text, key):
	res = ""
	for ch in text:
		tmp = key.get(ch)
		if tmp is not None:
			res += tmp
		else:
			res += ch
	return res


def decrypt(text, key):
	decrypt_key = {}
	for i in key:
		decrypt_key[key.get(i)] = i
	res = ""
	for ch in text:
		tmp = decrypt_key.get(ch)
		if tmp is not None:
			res += tmp
		else:
			res += ch
	return res


def main(file_name):
	print("Для шифрования введите 1, для расшифрования - 2, для создания таблицы - 3")
	mode = input("> ").strip()
	if mode != "1" and mode != "2" and mode != "3":
		raise Exception("invalid mode")
	if mode == "1" or mode == "2":
		print("Введите путь к файлу:")
		file = open(input("> "), "r")
		text = file.read()
		file.close()
		key = parse_key(file_name)
		if mode == "1":
			result = encrypt(text, key)
		else:
			result = decrypt(text, key)
		file = open("out.txt", "w")
		file.write(result)
		file.close()
		print("Result output in out.txt")
	else:
		generate_key(file_name)


if __name__ == "__main__":
	key_file = "key.txt"
	while True:
		try:
			main(key_file)
		except KeyboardInterrupt:
			print()
			exit(0)
		except Exception as e:
			print("Error: {}".format(e))
		print()
