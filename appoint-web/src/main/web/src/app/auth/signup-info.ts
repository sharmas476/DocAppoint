import { typeWithParameters } from '@angular/compiler/src/render3/util';

export class SignUpInfo {
    name: string;
    phone: string;
    email: string;
    role: string[];
    password: string;
    age:string;
    gender:string;

    constructor(name: string, phone: string, email: string, password: string, age: string, gender: string) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.role = ['user'];
    }
}
