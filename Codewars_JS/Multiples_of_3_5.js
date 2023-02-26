function solution(number){
    number--;
    let tot=0;
    for(let x=number; x>=0;x--) if(x%3==0||x%5==0) tot+=x;
    return tot;
}