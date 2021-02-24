from ex01.main import is_alpha
from ex02.main import generate_key, encrypt, parse_key


def get_frequency(text):
	count = 0
	frequency = {}
	for i in range(ord('a'), ord('z') + 1):
		frequency[chr(i)] = 0
	for ch in text:
		if is_alpha(ch):
			frequency[ch.lower()] += 1
			count += 1
	for i in range(ord('a'), ord('z') + 1):
		frequency[chr(i)] /= count
	return frequency


def sort_frequency(dictionary):
	array = []
	for i in range(ord('a'), ord('z') + 1):
		array.append(chr(i))
	for i in range(0, len(array)):
		for j in range(i + 1, len(array)):
			if dictionary[array[i]] < dictionary[array[j]]:
				array[i], array[j] = array[j], array[i]
	return array


def get_key(ref_text, encrypted_text):
	key = {}
	ref_sorted = sort_frequency(get_frequency(ref_text))
	src_sorted = sort_frequency(get_frequency(encrypted_text))
	for i in range(0, len(ref_sorted)):
		key[src_sorted[i]] = ref_sorted[i]
	return key


def compare_keys(original_key, tmp_key):
	count = 0
	for i in range(ord('a'), ord('z') + 1):
		if chr(i) == tmp_key[original_key[chr(i)]]:
			count += 1
	return count / 26


def correct(text, key):
	while True:
		print("Введите два символа через пробел или exit для выхода:")
		line = input("> ").strip()
		if line == "exit":
			print("Таблица шифрования:")
			for i in range(ord('a'), ord('z') + 1):
				print("{} -> {}".format(chr(i), key[chr(i)]))
			print("Result:\n{}".format(encrypt(text, key)))
			return
		arr = line.split(" ")
		if len(arr) == 2:
			for i in range(ord('a'), ord('z') + 1):
				if key[chr(i)] == arr[0]:
					key[chr(i)] = arr[1]
				elif key[chr(i)] == arr[1]:
					key[chr(i)] = arr[0]
		print("Result: {}".format(encrypt(text[0:250], key)))


def main():
	print("Для исследования введите 1, для взлома - 2")
	mode = input("> ").strip()
	if mode != "1" and mode != "2":
		raise Exception("invalid mode")
	print("Введите путь к большому текстовому файлу:")
	file_name = input("> ").strip()
	file = open(file_name, "r")
	big_text = file.read()
	file.close()
	if mode == "1":
		generate_key("key.txt")
		original_key = parse_key("key.txt")
		encrypted_big_text = encrypt(big_text, original_key)
		file = open("statistic.csv", "w")
		for i in range(int(len(encrypted_big_text) / 20), len(encrypted_big_text), int(len(encrypted_big_text) / 20)):
			tmp_key = get_key(big_text, encrypted_big_text[0:i])
			cmp = int(compare_keys(original_key, tmp_key) * 100)
			print("{} symbols: {}% correct".format(round(i, -3), cmp))
			file.write("{};{}\n".format(round(i, -3), cmp))
		file.close()
	else:
		print("Введите путь к зашифрованному текстовому файлу:")
		file = open(input("> ").strip(), "r")
		src_text = file.read()
		file.close()
		key = get_key(big_text, src_text)
		print("Result: {}".format(encrypt(src_text, key)[0:500]))
		correct(src_text, key)


if __name__ == "__main__":
	while True:
		try:
			main()
		except KeyboardInterrupt:
			print()
			exit(0)
		except Exception as e:
			print("Error: {}".format(e))
		print()
