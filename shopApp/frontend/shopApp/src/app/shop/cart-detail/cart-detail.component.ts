import { CartItem } from './../../models/cartItem';
import { Product } from './../../models/product';

import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import {  RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { CartItemDto } from '../../models/cartItemDto';
import { ResponseModel } from '../../models/responseModel';
import { ToastrService } from 'ngx-toastr';
import { SingleResponseModel } from '../../models/singleResponseModel';

@Component({
  selector: 'app-cart-detail',
  standalone: true,
  imports: [NavbarComponent,CommonModule,RouterModule],
  templateUrl: './cart-detail.component.html',
  styleUrl: './cart-detail.component.css'
})
export class CartDetailComponent implements OnInit {
  constructor(private cartService:CartService,private toastr:ToastrService){}
  total: number |undefined; 
  userId: number = 1; 
  itemCount: number =0;
  products: Product[] = []; 
  cartItemDto:CartItemDto[]=[]



  ngOnInit(): void {
    this.getTotalPrice();
    this.getCart();
    
  }
  getTotalPrice(): void {
    this.cartService.getTotalPrice(this.userId).subscribe({
      next: (response) => {
        this.total = response.data; 
      },
      error: (error) => {
        console.error('Sepetleri alırken hata oluştu:', error);
      }
    });
  }
  getCart():void{
    this.cartService.getCart().subscribe({
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
                this.toastr.warning('Ürün sepetten çıkarıldı!', 'Başarılı');
                this.getCart();
                this.getTotalPrice();
            } 
        },
        error: (error: any) => {
            console.error('Ürün silinirken hata oluştu', error);
            alert('Ürün silinirken bir hata oluştu.');
        }
    });
}
makeOrder(): void {
  this.cartService.makeOrder().subscribe({
    next: (response: SingleResponseModel<string>) => {
      if (response.success) {
        console.log('Sipariş başarıyla oluşturuldu:', response.message);
        this.toastr.success('Sipariş sayfasından kontrol edebilirsiniz!', 'SİPARİŞ VERİLDİ!');
        
      } else {
        console.error('Sipariş oluşturulurken hata:', response.message);
      }
    },
    error: (error) => {
      console.error('Sipariş oluşturulurken hata oluştu:', error);
    }
  });
}


}



  



