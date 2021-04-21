let POPULATION = 1000
let RADIUS = 3
let infoTitle = document.getElementById("info-title")
let stage = document.getElementById("stage");
stage.width = window.innerWidth - 50
stage.height = window.innerHeight - 200
let context = stage.getContext("2d")
let persons = []

function reset()
{
	for (let i = 1; i < persons.length; i++)
		persons[i].isInfected = false
}

function personsAreClose(person1, person2)
{
	return Math.sqrt((person1.x - person2.x) * (person1.x - person2.x) +
		(person1.y - person2.y) * (person1.y - person2.y)) < RADIUS / 2
}

class Person
{
	constructor()
	{
		this.x = Math.random() * (stage.width - RADIUS * 2) + RADIUS
		this.y = Math.random() * (stage.height - RADIUS * 2) + RADIUS
		this.direction = Math.random() * Math.PI * 2
		this.speed = Math.random() * 2 + 1
		this.isInfected = false
	}

	render()
	{
		if (this.x - RADIUS <= 0 || this.x + RADIUS >= stage.width)
			this.direction = (this.direction + Math.PI) * -1
		if (this.y - RADIUS <= 0 || this.y + RADIUS >= stage.height)
			this.direction *= -1
		this.x += Math.cos(this.direction) * this.speed
		this.y += Math.sin(this.direction) * this.speed
		context.beginPath()
		context.arc(this.x, this.y, RADIUS, 0, Math.PI * 2)
		if (this.isInfected)
			context.fillStyle = "#00a500"
		else
			context.fillStyle = "#6b0000"
		context.fill()
		for (let i = 0; i < persons.length; i++)
		{
			if (this.isInfected && !persons[i].isInfected && personsAreClose(this, persons[i]))
				persons[i].isInfected = true
		}
	}
}

for (let i = 0; i < POPULATION; i++)
	persons.push(new Person())
persons[0].isInfected = true
setInterval(function () {
	let infectedCount = 0
	context.clearRect(0, 0, stage.width, stage.height)
	for (let i = 0; i < POPULATION; i++)
	{
		persons[i].render()
		if (persons[i].isInfected)
			infectedCount++
	}
	infoTitle.innerText = "Total: " + POPULATION + ", infected: " + infectedCount
	document.getElementById("graph-fill").style.width = 100 * infectedCount / POPULATION + "%";
}, 1000 / 60)
