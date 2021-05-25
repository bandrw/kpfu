#!/usr/bin/env python3
import datetime
import socket
import threading

from client import segments_to_str
from utils import BLUE_BACK, NULL, RED, receive_message, send_message

HOST = "127.0.0.1"

CLIENTS = {
	"Alice": {
		"conn": None,
		"addr": None,
		"public_key": [0, 0]
	},
	"Bob": {
		"conn": None,
		"addr": None,
		"public_key": [0, 0]
	}
}


def login(conn, addr):
	data = receive_message(conn)
	if not data:
		return False
	response = data.decode("utf-8")
	public_key = [0, 0]
	data = receive_message(conn)
	public_key[0] = int.from_bytes(data, "big")
	data = receive_message(conn)
	public_key[1] = int.from_bytes(data, "big")
	if response == "LOGIN ALICE":
		if CLIENTS["Alice"]["conn"] or not CLIENTS["Bob"]["conn"]:
			send_message(conn, "FAIL".encode())
			return False
		CLIENTS["Alice"]["conn"] = conn
		CLIENTS["Alice"]["addr"] = addr
		CLIENTS["Alice"]["public_key"] = public_key
		send_message(conn, "SUCCESS".encode())
		send_message(conn, CLIENTS["Bob"]["public_key"][0].to_bytes(128, "big"))
		send_message(conn, CLIENTS["Bob"]["public_key"][1].to_bytes(128, "big"))
		return True
	elif response == "LOGIN BOB":
		if CLIENTS["Bob"]["conn"]:
			send_message(conn, "FAIL".encode())
			return False
		CLIENTS["Bob"]["conn"] = conn
		CLIENTS["Bob"]["addr"] = addr
		CLIENTS["Bob"]["public_key"] = public_key
		send_message(conn, "SUCCESS".encode())
		return True
	return False


def client_handle(conn, addr):
	print(f"{BLUE_BACK} {NULL} Connected by {addr[0]}:{addr[1]}")
	with conn:
		if not login(conn, addr):
			print(f"{BLUE_BACK} {NULL} {addr[0]}:{addr[1]} disconnected")
			return
		while True:
			data = receive_message(conn)
			if not data:
				break
			send_message(CLIENTS["Bob"]["conn"], data)
			response = int.from_bytes(data, "big")
			text = segments_to_str([response], )
			print(f"[{datetime.datetime.now().strftime('%H:%M:%S')}] Received: {text}", end="")
			while int.from_bytes(data, "big") != 0:
				data = receive_message(conn)
				if not data:
					break
				send_message(CLIENTS["Bob"]["conn"], data)
				response = int.from_bytes(data, "big")
				text = segments_to_str([response])
				print(text, end="")
			print()
	print(f"{BLUE_BACK} {NULL} {addr[0]}:{addr[1]} disconnected")
	if CLIENTS["Alice"] and CLIENTS["Alice"]["addr"] == addr:
		CLIENTS["Alice"]["conn"] = None
	if CLIENTS["Bob"] and CLIENTS["Bob"]["addr"] == addr:
		CLIENTS["Bob"]["conn"] = None


def main():
	with socket.socket() as s:
		try:
			s.bind((HOST, 0))
			_, port = s.getsockname()
			s.listen()
			print(f"{BLUE_BACK} {NULL} Server listening on {HOST}:{port}")
			while True:
				conn, addr = s.accept()
				thread = threading.Thread(target=client_handle, args=(conn, addr))
				thread.start()
		except KeyboardInterrupt:
			print()
		except Exception as ex:
			print(f"{RED}Error: {ex}{NULL}")


if __name__ == "__main__":
	try:
		main()
	except KeyboardInterrupt:
		print()