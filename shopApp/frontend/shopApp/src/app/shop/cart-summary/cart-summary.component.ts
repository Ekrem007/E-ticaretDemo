import { Product } from './../../models/product';
import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { CommonModule } from '@angular/common';
import { SingleResponseModel } from '../../models/singleResponseModel';
import { ToastrService } from 'ngx-toastr';



@Component({
  selector: 'cart-summary',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-summary.component.html',
  styleUrls: ['./cart-summary.component.css']
})
export class CartSummaryComponent implements OnInit {
  total: number |undefined; 
  itemCount: number |undefined; 
  userId: number = 1; 


  constructor(private cartService: CartService,private toastr:ToastrService) {}

  ngOnInit(): void {
    this.getTotalQuantity();
    this.getTotalPrice();
    
  }

  getTotalQuantity(): void {
    this.cartService.getTotalQuantity(this.userId).subscribe({
      next: (response) => {
        this.itemCount = response.data;
        

        
      },
      error: (error) => {
        console.error('Toplam ürün sayısını alırken hata oluştu:', error);
      }
    });
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
  
  
  
  }
  
  

