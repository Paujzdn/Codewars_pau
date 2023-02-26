function findNb(m) {
    let x=0,t=0;
    while(x<m){
        t++;
        x+=t**3;
    }
    if(x==m) return t;
    else return -1
}
console.log(findNb(24723578342962));