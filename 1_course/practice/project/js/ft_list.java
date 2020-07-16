function ft_list_push_back(list, data)
{
	if (list)
	{
		while (list.next)
			list = list.next;
		list.next = data;
	}
	else
		list = data;
}
