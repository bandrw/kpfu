function ft_create_elem(data)
{
	let list;

	list = {
		data: data,
		next: null
	}
	return (list);
}

function ft_list_size(list)
{
	let i;

	i = 0;
	while (list)
	{
		i++;
		list = list.next;
	}
	return (i);
}

function ft_list_push_back(list, data)
{
	if (list)
	{
		while (list.next)
			list = list.next;
		list.next = ft_create_elem(data);
	}
}

function ft_sorted_list_insert(begin_list, data)
{
	let list;
	let prev;

	list = begin_list.next;
	while (list && (data.author > list.data.author))
	{
		prev = list;
		list = list.next;
	}
	if (prev)
	{
		prev.next = ft_create_elem(data);
		(prev.next).next = list;
	}
	else
	{
		prev = ft_create_elem(data);
		prev.next = list;
		begin_list.next = prev;
	}
}
