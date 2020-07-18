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

function ft_sorted_list_insert(list, data)
{
	let prev;
	let begin_list;

	begin_list = list;
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
		begin_list = prev;
	}
	return (begin_list);
}
