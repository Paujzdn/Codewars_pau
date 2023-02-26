def count_bits(n):
    return sum(x == 1 for x in [int(x) for x in str("{0:b}".format(n))])
print(count_bits(21))