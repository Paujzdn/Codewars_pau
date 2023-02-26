def is_triangle_number(number):
    y=1
    if not isinstance(number,int):
        return False
    if number:
        return False
    while number>0:
        number-=y
        y+=1
    if number==0:
        return True
    return False

print(is_triangle_number(True))

