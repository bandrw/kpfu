import pygame
from random import randint


def put_polygon(screen):
    pygame.draw.line(screen, (randint(0, 255), randint(0, 255), randint(0, 255)), (200, 200), (600, 200))
    pygame.draw.line(screen, (randint(0, 255), randint(0, 255), randint(0, 255)), (600, 200), (600, 600))
    pygame.draw.line(screen, (randint(0, 255), randint(0, 255), randint(0, 255)), (200, 600), (600, 600))
    pygame.draw.line(screen, (randint(0, 255), randint(0, 255), randint(0, 255)), (200, 200), (200, 600))
    pygame.draw.line(screen, (randint(0, 255), randint(0, 255), randint(0, 255)), (100, 100), (700, 700))


def flood_fill(screen, point):
    queue = [point]

    while queue:
        try:
            if screen.get_at(queue[0]) == (255, 255, 255, 255):
                screen.set_at(queue[0], (0, 0, 255))
                if screen.get_at((queue[0][0] + 1, queue[0][1])) == (255, 255, 255, 255):
                    queue.append((queue[0][0] + 1, queue[0][1]))
                if screen.get_at((queue[0][0] - 1, queue[0][1])) == (255, 255, 255, 255):
                    queue.append((queue[0][0] - 1, queue[0][1]))
                if screen.get_at((queue[0][0], queue[0][1] + 1)) == (255, 255, 255, 255):
                    queue.append((queue[0][0], queue[0][1] + 1))
                if screen.get_at((queue[0][0], queue[0][1] - 1)) == (255, 255, 255, 255):
                    queue.append((queue[0][0], queue[0][1] - 1))
        except IndexError:
            pass
        queue.pop(0)


def main():
    pygame.init()
    pygame.display.set_caption("Ex04")
    screen = pygame.display.set_mode([1280, 720])
    screen.fill((255, 255, 255))
    put_polygon(screen)
    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            if event.type == pygame.MOUSEBUTTONDOWN:
                flood_fill(screen, pygame.mouse.get_pos())
        pygame.display.flip()
    pygame.quit()


if __name__ == "__main__":
    main()
