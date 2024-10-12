import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CartItemDto } from '../../../models/cartItemDto';
import { CartService } from '../../../services/cart.service';
import { ResponseModel } from '../../../models/responseModel';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.css'
})
export class OrderListComponent implements OnInit {

  constructor(private cartService:CartService){}

  cartItemDto: CartItemDto[]=[]

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders():void{
    this.cartService.getOrders().subscribe({
      next:(response) => {
        this.cartItemDto=response.data;
      },
      error: (error) => {
        console.error('Sepetleri alırken hata oluştu:', error);
      }
    });

}
deleteCartItem(cartItemId: number | undefined): void {
  if (cartItemId === undefined) {
      console.error('Sepet ürünü ID\'si tanımsız');
      return; 
  }

  this.cartService.deleteCartItem(cartItemId).subscribe({
      next: (response: ResponseModel) => {
          if (response.success) {
              this.cartItemDto = this.cartItemDto.filter(item => item.productId !== cartItemId);
              this.getOrders();
          
              
          } 
      },
      error: (error: any) => {
          console.error('Ürün silinirken hata oluştu', error);
          alert('Ürün silinirken bir hata oluştu.');
      }
  });
}
makeOrder(cartId: number): void {
  this.cartService.sendOrder(cartId).subscribe({
    next: (response) => {
      if (response.success) {
        alert('Siparişiniz başarıyla gönderildi!');
        this.getOrders(); 
      } else {
        alert('Sipariş gönderilirken hata oluştu. Lütfen tekrar deneyin.');
      }
    },
    error: (error) => {
      console.error('Sipariş gönderilirken hata oluştu', error);
      alert('Sipariş gönderilirken bir hata oluştu.');
    }
  });
}
}

