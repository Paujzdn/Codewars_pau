def move_zeros(array):
    for i in array:
        if i==0:
            del array[array.index(i)]
            array.append(0)
    return array

def move_zeros1(arr):
    l = [i for i in arr if isinstance(i, bool) or i!=0]
    return l+[0]*(len(arr)-len(l))

def move_zeros2(array):
    return sorted(array, key=lambda x: x==0 and type(x) is not bool)


def move_zeros(array):
    return [a for a in array if isinstance(a, bool) or a != 0] + [a for a in array if
                                                                  not isinstance(a, bool) and a == 0]
print(move_zeros([1, 0, 1, 2, 0, 1, 3]))