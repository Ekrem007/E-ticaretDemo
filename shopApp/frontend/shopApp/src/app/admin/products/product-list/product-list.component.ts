import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit{

  constructor(private productService:ProductService,private router: Router){}

  products:Product[]=[]

  ngOnInit(): void {
    this.getProducts();
    
  }
  getProducts() {
    this.productService.getProducts().subscribe({
      next: (response) => {
        this.products = response.data; 
      },
      error: (err) => {
        console.error("Error fetching products: ", err);
      }
    });
  }
  deleteProduct(productId: number): void {
    if (confirm('Bu ürünü silmek istediğinize emin misiniz?')) {
      this.productService.deleteProduct(productId).subscribe({
        next: (response) => {
          console.log('Product deleted successfully:', response);
          this.getProducts();
          this.router.navigate(['/admin/main/products']);
        },
        error: (err) => {
          console.error('Error deleting product:', err);
        }
      });
    }
  }

}
