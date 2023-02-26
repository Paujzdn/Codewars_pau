var uniqueInOrder = function(iterable) {
    if (typeof iterable === "string")
        return iterable.split("").filter((v, i, arr) => arr[i] != arr[i + 1]);
    return iterable.filter((v, i, arr) => arr[i] != arr[i + 1]);
};