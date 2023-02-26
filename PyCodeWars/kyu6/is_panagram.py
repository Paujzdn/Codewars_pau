import string


def is_pangram(str):
    alphabet = "abcdefghijklmnopqrstuvwxyz"
    for char in alphabet:
        if char not in str.lower():
            return False
    return True

print(is_pangram("The quick, brown fox jumps over the lazy dog!"))