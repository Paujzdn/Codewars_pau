function deleteNth(arr, n) {
    let cache = [];
    return arr.filter(v => (cache[v] = ~~cache[v] + 1) <= n);
}