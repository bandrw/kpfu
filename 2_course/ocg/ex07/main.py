#!/usr/bin/env python3

import turtle
import sys

class MyTurtle(turtle.Turtle):
	def draw_koch_segment(self, length, segments):
		if segments > 1:
			self.draw_koch_segment(length / 3, segments - 1)
			self.left(60)
			self.draw_koch_segment(length / 3, segments - 1)
			self.right(120)
			self.draw_koch_segment(length / 3, segments - 1)
			self.left(60)
			self.draw_koch_segment(length / 3, segments - 1)
		else:
			self.forward(length)
			self.left(60)
			self.forward(length)
			self.right(120)
			self.forward(length)
			self.left(60)
			self.forward(length)


def main():
	if len(sys.argv) != 2:
		print(f"Usage: ./{sys.argv[0].strip('./')} <segments>", file=sys.stderr)
		exit(1)
	segments = int(sys.argv[1])
	turtle.Screen().setup(500, 500)
	length = 100
	my_turtle = MyTurtle()
	my_turtle.hideturtle()
	my_turtle.penup()
	my_turtle.setposition(my_turtle.position()[0] - length * 1.6, my_turtle.position()[1] - length / 2.5)
	my_turtle.pendown()
	my_turtle.speed(0)
	my_turtle.pensize(1 / (segments * segments))
	my_turtle.draw_koch_segment(length, segments)
	my_turtle.getscreen().getcanvas().postscript(file="out.eps")
	print("Result saved to out.eps")
	turtle.done()


if __name__ == "__main__":
	main()
