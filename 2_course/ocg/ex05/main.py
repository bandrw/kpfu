import math
import numpy as np

import pygame
from random import randint
from scanf import scanf

WINDOW_WIDTH = 720
WINDOW_HEIGHT = 480


class Triangle:
	a: [int, int] = [None, None]
	b: [int, int] = [None, None]
	c: [int, int] = [None, None]

	def __init__(self):
		self.a = [randint(0, WINDOW_WIDTH), randint(0, WINDOW_HEIGHT)]
		self.b = [randint(0, WINDOW_WIDTH), randint(0, WINDOW_HEIGHT)]
		self.c = [randint(0, WINDOW_WIDTH), randint(0, WINDOW_HEIGHT)]

	def scale(self, x, y, value):
		triangle = np.array(
			[
				[self.a[0] - x, self.a[1] - y],
				[self.b[0] - x, self.b[1] - y],
				[self.c[0] - x, self.c[1] - y]
			]
		)
		matrix = np.array(
			[
				[value, 0],
				[0, value]
			]
		)
		total = triangle.dot(matrix)
		self.a[0] = int(total[0][0]) + x
		self.a[1] = int(total[0][1]) + y
		self.b[0] = int(total[1][0]) + x
		self.b[1] = int(total[1][1]) + y
		self.c[0] = int(total[2][0]) + x
		self.c[1] = int(total[2][1]) + y

	def rotate(self, x, y, angle):
		triangle = np.array(
			[
				[self.a[0] - x, self.a[1] - y],
				[self.b[0] - x, self.b[1] - y],
				[self.c[0] - x, self.c[1] - y]
			]
		)
		matrix = np.array(
			[
				[math.cos(math.radians(angle)), -math.sin(math.radians(angle))],
				[math.sin(math.radians(angle)), math.cos(math.radians(angle))]
			]
		)
		total = triangle.dot(matrix)
		self.a[0] = int(total[0][0]) + x
		self.a[1] = int(total[0][1]) + y
		self.b[0] = int(total[1][0]) + x
		self.b[1] = int(total[1][1]) + y
		self.c[0] = int(total[2][0]) + x
		self.c[1] = int(total[2][1]) + y


def put_polygon(screen, triangle):
	points = [triangle.a, triangle.b, triangle.c, triangle.a]
	prev_point = None
	for point in points:
		if prev_point:
			pygame.draw.line(screen, (255, 0, 0), prev_point, point)
		prev_point = point


def main():
	print("Usage:\n\tex05> rotate <x> <y> <angle>\n\tex05> scale <x> <y> <value>")
	background_color = (255, 255, 255)
	pygame.init()
	pygame.display.set_caption("Ex05")
	screen = pygame.display.set_mode([WINDOW_WIDTH, WINDOW_HEIGHT])
	screen.fill(background_color)
	triangle = Triangle()
	running = True
	while running:
		screen.fill(background_color)
		put_polygon(screen, triangle)
		for event in pygame.event.get():
			if event.type == pygame.QUIT:
				running = False
		pygame.display.flip()
		try:
			command = input('ex05> ')
			res = scanf("rotate %d %d %d", command)
			if res:
				triangle.rotate(res[0], res[1], res[2])
				continue
			res = scanf("scale %d %d %f", command)
			if res:
				triangle.scale(res[0], res[1], res[2])
				continue
			print("Error: Unknown command")
		except KeyboardInterrupt:
			print()
			break
	pygame.quit()


if __name__ == "__main__":
	main()