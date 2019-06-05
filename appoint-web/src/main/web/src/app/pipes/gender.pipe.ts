import { PipeTransform, Pipe } from '@angular/core';

@Pipe({
    name:"gender"
})
export class GenderPipe implements PipeTransform{
    transform(value: string) {
        return (value === "M")? "Male" : "Female";
    }
}