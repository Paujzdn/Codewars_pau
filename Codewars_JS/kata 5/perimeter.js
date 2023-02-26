const perimeter = (num) => {
    const arr = [1,1];
    let sum=0;
    for( let i=0; i<num-1;i++){
        n=arr[i]+arr[i+1], arr.push(n);
        sum+=n;
    }
    /*
    for(let i = 0 ; i < num-1 ; i++){
        n = arr[i] + arr[i+1];
        arr.push(n);
        sum += n;
    };

     */
    return sum * 4;
};
console.log(perimeter(12))