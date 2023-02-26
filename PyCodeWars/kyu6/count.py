def count(string):
    return {c:string.count(c) for c in string}


from collections import Counter

def count(string):
    return Counter(string)
print(count('aba'))