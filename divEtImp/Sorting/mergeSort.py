#!/usr/bin/python
import random

def mergeSort(array, start, end):
	if start == end:
		return
	mid = (start + end) / 2
	mergeSort(array, start, mid)
	mergeSort(array, mid + 1, end)
	merge(array, start, end)

def merge(array, start, end):
	aux = [0] * 100
	mid = (start + end) / 2
	i = start
	j = mid + 1
	k = 0
	while i < mid and j < end:
		if array[i] < array[j]:
			aux[k] = array[i]
			k += 1
			i += 1
		else:
			aux[k] = array[j]
			k += 1
			j += 1
	while i < mid:
		aux[k] = array[i]
		k += 1
		i += 1
	while j < end:
		aux[k] = array[j]
		k += 1
		j += 1

	j = 0
	while j < k - 1:
		array[j] = aux[j]
		j += 1

def main():
	array = []
	for i in range(10):
		array.append(random.randrange(1,100))
	print(array)
	mergeSort(array, 0, 10)
	print("Sorted: ")
	print(array)

if __name__ == "__main__":
    main()