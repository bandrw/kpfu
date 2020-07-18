function library_book_rm()
{
	let begin_list;
	let list;
	let prev;

	if (!localStorage.getItem("library"))
	{
		console.error("Can't get library in library_book_rm");
		return;
	}
	list = JSON.parse(localStorage.getItem("library"));
	begin_list = list;
	while (list)
	{
		if (list.data.id == this.value)
		{
			if (prev)
				prev.next = list.next;
			else
				begin_list = list.next;
			list = 0;
		}
		prev = list;
		list = list.next;
	}
	localStorage.removeItem("library");
	localStorage.setItem("library", JSON.stringify(begin_list));
	this.parentElement.remove();
}

function list_compare_id(ref, elem)
{
	if (elem && elem.id == ref)
		return (0);
	else
		return (1);
}

function library_book_decrem()
{
	let id;
	let begin_list;

	begin_list = JSON.parse(localStorage.getItem("library"));
	id = this.parentElement.parentElement.value;
	elem = ft_list_find(begin_list, id, list_compare_id);
	if (elem.data.count > 0)
	{
		elem.data.count--;
		this.parentElement.parentElement.children[5].innerHTML = elem.data.count;
		localStorage.setItem("library", JSON.stringify(begin_list));
	}
}

function library_book_increm()
{
	let id;
	let begin_list;

	begin_list = JSON.parse(localStorage.getItem("library"));
	id = this.parentElement.parentElement.value;
	elem = ft_list_find(begin_list, id, list_compare_id);
	elem.data.count++;
	this.parentElement.parentElement.children[5].innerHTML = elem.data.count;
	localStorage.setItem("library", JSON.stringify(begin_list));
}

function create_library_book(data)
{
	let div;
	let span;
	let elem;

	div = document.createElement("div");
	div.value = data.id;
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
	elem = document.createElement("div");
	elem.className = "lib_book_control";
	span = document.createElement("span");
	span.onclick = library_book_decrem;
	span.className = "lib_book_control_dec";
	elem.appendChild(span);
	span = document.createElement("span");
	span.onclick = library_book_increm;
	span.className = "lib_book_control_inc";
	elem.appendChild(span);
	div.appendChild(elem);
	return (div);
}

function get_library_book_data()
{
	let data;

	data = {
		author: document.getElementById("add_book_author").value,
		name: document.getElementById("add_book_name").value,
		country: document.getElementById("add_book_country").value,
		year: document.getElementById("add_book_year").value,
		count: document.getElementById("add_book_count").value,
		id: ft_list_size(JSON.parse(localStorage.getItem("library")))
	}
	if (data.author == "" || data.name == "" || data.country == ""
		|| data.year == "" || data.count == "")
	{
		data = null;
	}
	else
	{
		document.getElementById("add_book_author").value = "";
		document.getElementById("add_book_name").value = "";
		document.getElementById("add_book_country").value = "";
		document.getElementById("add_book_year").value = "";
		document.getElementById("add_book_count").value = "";
	}
	return (data);
}

function add_library_book()
{
	let library_books;
	let data;
	let i;

	data = get_library_book_data();
	if (data == null)
		return;
	addBook([data.author, data.name, data.country, data.year, data.count, data.id]);
	document.getElementById("add_book_layer1").style.display = "flex";
	document.getElementById("add_book_layer2").style.display = "none";
	library_books = document.getElementsByClassName("library_book");
	i = 1;
	while (library_books[i] && library_books[i].children[1].innerHTML < data.author)
		i++;
	if (library_books[i])
		library_books[i].before(create_library_book(data));
	else
		document.getElementById("library_content").appendChild(create_library_book(data));
}

function show_library_book_input()
{
	document.getElementById("add_book_layer1").style.display = "none";
	document.getElementById("add_book_layer2").style.display = "block";
	document.getElementById("lib_book_add_button").onclick = add_library_book;
}

function show_library()
{
	let list;
	let div;

	if (!localStorage.getItem("library"))
	{
		console.error("Can't get library in show_library");
		return;
	}
	list = JSON.parse(localStorage.getItem("library"));
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
	let library_list;

	library_list = JSON.parse(localStorage.getItem("library"));
	book = {
		author: book_info[0],
		name: book_info[1],
		country: book_info[2],
		year: book_info[3],
		count: book_info[4] >= 0 ? book_info[4] : Math.floor(Math.random() * 100),
		id: ft_list_size(library_list)
	};
	library_list = ft_sorted_list_insert(library_list, book);
	localStorage.setItem("library", JSON.stringify(library_list));
}

function library_restore_books()
{
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
}

if (!localStorage.getItem("library"))
	library_restore_books();
show_library();
document.getElementById("add_book_layer1").onclick = show_library_book_input;
document.getElementById("library_books_restore").onclick = function(){
	localStorage.removeItem("library");
	library_restore_books();
	while (document.getElementsByClassName("library_book").length > 1)
		document.getElementsByClassName("library_book")[1].remove();
	show_library();
};
