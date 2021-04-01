import sys

import pygame
from random import randint

WINDOW_WIDTH = 1280
WINDOW_HEIGHT = 720


def get_random_color():
	return randint(0, 255), randint(0, 255), randint(0, 255)


def put_polygon(screen, nodes_count):
	points = []
	for i in range(nodes_count - 1):
		x = randint(1, WINDOW_WIDTH - 2)
		y = randint(1, WINDOW_HEIGHT - 2)
		points.append((x, y))
	points.append(points[0])
	prev_point = None
	for point in points:
		if prev_point:
			pygame.draw.line(screen, get_random_color(), prev_point, point)
		prev_point = point


def flood_fill(screen, point, background_color, color):
	queue = [point]

	while queue:
		try:
			if screen.get_at(queue[0]) == background_color:
				screen.set_at(queue[0], color)
				if screen.get_at((queue[0][0] + 1, queue[0][1])) == background_color:
					queue.append((queue[0][0] + 1, queue[0][1]))
				if screen.get_at((queue[0][0] - 1, queue[0][1])) == background_color:
					queue.append((queue[0][0] - 1, queue[0][1]))
				if screen.get_at((queue[0][0], queue[0][1] + 1)) == background_color:
					queue.append((queue[0][0], queue[0][1] + 1))
				if screen.get_at((queue[0][0], queue[0][1] - 1)) == background_color:
					queue.append((queue[0][0], queue[0][1] - 1))
		except IndexError:
			pass
		queue.pop(0)


def main():
	print()
	nodes_count = None
	if len(sys.argv) != 2:
		print("Usage: python3 {} n\nn - nodes count".format(sys.argv[0]))
		exit(1)
	try:
		nodes_count = int(sys.argv[1])
		if nodes_count < 3:
			raise Exception("Nodes count can't be less than 3")
	except Exception as e:
		print("Argument error: {}".format(e))
		exit(2)
	background_color = (255, 255, 255)
	pygame.init()
	pygame.display.set_caption("Ex04")
	screen = pygame.display.set_mode([WINDOW_WIDTH, WINDOW_HEIGHT])
	screen.fill(background_color)
	put_polygon(screen, nodes_count)
	running = True
	while running:
		for event in pygame.event.get():
			if event.type == pygame.QUIT:
				running = False
			if event.type == pygame.MOUSEBUTTONDOWN:
				flood_fill(screen, pygame.mouse.get_pos(), background_color, color=(0, 0, 255))
		pygame.display.flip()
	pygame.quit()


if __name__ == "__main__":
	main()
