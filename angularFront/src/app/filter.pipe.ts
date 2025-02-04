import { Pipe, PipeTransform } from '@angular/core';
import {product} from "./model/product.model";

@Pipe({
  name: 'filter',
  standalone: true
})
export class FilterPipe implements PipeTransform {

  transform(items: product[], searchText: string): product[] {
    if (!items) {
      return [];
    }
    if (!searchText) {
      return items;
    }
    searchText = searchText.toLocaleLowerCase();

    return items.filter(it => {
      return (it.idProduct.toString().includes(searchText) ||it.name.includes(searchText)||it.description.includes(searchText)||it.price.toString().includes(searchText)||it.quantity.toString().includes(searchText));
    });
  }

}
