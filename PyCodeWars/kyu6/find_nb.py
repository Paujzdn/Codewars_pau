def find_nb(m):
    x=0
    y=0
    while x<=m:
       x+=(y*3)
       ++y
    return y
print(str(find_nb(402)))