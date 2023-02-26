function twoSum(numbers, target) {
    u=numbers.length;
    for(let i=0;i<u;i++){
        for(let j=0;j<u;j++){
            if((numbers[i]+numbers[j]==target)&&i!=j) return [i,j];
        }
    }
}
console.log(twoSum([2,5,6,8,1,4,2,3],          12));