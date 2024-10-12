import { CartItemDto } from './../../models/cartItemDto';
import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { ToastrService } from 'ngx-toastr';
import { ResponseModel } from '../../models/responseModel';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [NavbarComponent,CommonModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit {

  constructor(private cartService:CartService,private toastr:ToastrService){}
  cartItemDto: CartItemDto[]=[]


  ngOnInit(): void {
    this.getOrders();
    
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
                this.toastr.warning('Ürün Kaydı silindi!', 'Başarılı');
                
            } 
        },
        error: (error: any) => {
            console.error('Ürün silinirken hata oluştu', error);
            alert('Ürün silinirken bir hata oluştu.');
        }
    });
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

}
