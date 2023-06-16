//bool
let a : boolean = false
//number
let b : number = 123
//string
let c : string = "123"
//数组
let d : number[] = [1,2,3]
//元组
let f : [string,number]
f = ["a",1]
f = ["b",2]
console.log(f)


interface people {
    niuniu: boolean
}


function IsMan (man :people) {
    if(man.niuniu) {
        console.log("男")
    }
}

var a1: people = {niuniu:true}
IsMan(a1)


class user {
    name :string
    age : number

    constructor(name:string,age:number){
        this.name = name;
        this.age = age
    }

    getUser() {
        console.log("父类")   
    }
}

console.log(new user("asd",12))

class child extends user {
    
   
}

class child1 implements people{
    niuniu: boolean
}

console.log(new child("asd",12).getUser())
console.log(new child1().niuniu)