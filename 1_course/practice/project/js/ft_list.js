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
