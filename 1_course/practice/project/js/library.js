let g_library;

g_library = {};

function library_book_rm()
{
	let list;
	let prev;

	list = g_library.next;
	while (list)
	{
		if (list.data.id == this.value)
		{
			if (prev)
				prev.next = list.next;
			else
				g_library.next = list.next;
		}
		prev = list;
		list = list.next;
	}
	this.parentElement.remove();
}

function create_library_book(data)
{
	let div;
	let span;
	let elem;

	div = document.createElement("div");
	elem = document.createElement("div");
	elem.className = "library_book_rm";
	elem.value = data.id;
	elem.onclick = library_book_rm;
	div.appendChild(elem);
	div.className = "library_book";
	span = document.createElement("span");
	span.className = "lib_book_author";
	span.innerHTML = data.author;
	div.appendChild(span);
	span = document.createElement("span");
	span.className = "lib_book_name";
	span.innerHTML = data.name;
	div.appendChild(span);
	span = document.createElement("span");
	span.className = "lib_book_country";
	span.innerHTML = data.country;
	div.appendChild(span);
	span = document.createElement("span");
	span.className = "lib_book_year";
	span.innerHTML = data.year;
	div.appendChild(span);
	span = document.createElement("span");
	span.className = "lib_book_count";
	span.innerHTML = data.count;
	div.appendChild(span);
	return (div);
}

function get_library_book_data()
{
	let data;

	data = {
		author: "А.Б. Test",
		name: "test",
		country: "test",
		year: 2000,
		count: 120,
		id: ft_list_size(g_library)
	}
	return (data);
}

function add_library_book()
{
	let library_books;
	let i;
	let data;

	data = get_library_book_data();
	addBook([data.author, data.name, data.country, data.year, data.count, data.id]);
	library_books = document.getElementsByClassName("library_book");
	i = 1;
	while (library_books[i] && library_books[i].children[1].innerHTML < data.author)
	{
		console.log(library_books[i].firstChild.innerHTML);
		i++;
	}
	if (library_books[i])
		library_books[i].before(create_library_book(data));
	else
		document.getElementById("library_content").appendChild(create_library_book(data));
}

function show_library() // выводит имеющиеся книги из g_library в #library_content
{
	let list;
	let div;

	list = g_library.next;
	while (list)
	{
		div = create_library_book(list.data);
		document.getElementById("library_content").appendChild(div);
		list = list.next;
	}
}

function addBook(book_info)
{
	let book;

	book = {
		author: book_info[0],
		name: book_info[1],
		country: book_info[2],
		year: book_info[3],
		count: book_info[4] >= 0 ? book_info[4] : Math.floor(Math.random() * 100) + 1,
		id: ft_list_size(g_library)
	}
	ft_sorted_list_insert(g_library, book);
}

addBook(["А.С. Пушкин", "Дубровский", "Россия", 1842]);
addBook(["А.С. Пушкин", "Капитанская дочка", "Россия", 1836]);
addBook(["А.С. Пушкин", "Евгений Онегин", "Россия", 1837]);
addBook(["М.А. Булгаков", "Собачье сердце", "Россия", 1925]);
addBook(["М.А. Булгаков", "Мастер и Маргарита", "Россия", 1940]);
addBook(["И.С. Тургенев", "Отцы и дети", "Россия", 1861]);
addBook(["И.С. Тургенев", "Муму", "Россия", 1852]);
addBook(["Л.Н. Толстой", "Воскресение", "Россия", 1866]);
addBook(["Л.Н. Толстой", "Война и мир", "Россия", 1868]);
addBook(["Л.Н. Толстой", "Анна Каренина", "Россия", 1877]);
addBook(["Л.Н. Толстой", "Детство. Отрочество. Юность", "Россия", 1857]);
addBook(["Н.В. Гоголь", "Ревизор", "Россия", 1836]);
addBook(["Н.В. Гоголь", "Тарас и Бульба", "Россия", 1842]);
addBook(["Максим Горький", "Старуха Изергиль", "Россия", 1894]);
addBook(["Ф.М. Достоевский", "Преступление и наказание", "Россия", 1866]);
addBook(["Ф.М. Достоевский", "Игрок", "Россия", 1866]);
addBook(["А.С. Грибоедов", "Горе от ума", "Россия", 1828]);
addBook(["М.Ю. Лермонтов", "Герой нашего времени", "Россия", 1840]);
addBook(["Даниель Дефо", "Робинзон Крузо", "Англия", 1719]);
addBook(["А.П. Чехов", "Рассказы", "Россия", 1885]);
addBook(["А.П. Чехов", "Чайка", "Россия", 1896]);
addBook(["А.П. Чехов", "Три сестры", "Россия", 1900]);
addBook(["Александр Дюма", "Три мушкетёра", "Франция", 1844]);
addBook(["И.А. Гончаров", "Обломов", "Россия", 1858]);
addBook(["М.А. Шолохов", "Тихий Дон", "Россия", 1940]);
addBook(["Р.Д. Брэдбери", "451 градус по Фаренгейту", "Англия", 1953]);
document.getElementById("library_add_book").onclick = add_library_book;
show_library();
