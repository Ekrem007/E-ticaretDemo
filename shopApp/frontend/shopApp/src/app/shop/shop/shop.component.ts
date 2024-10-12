import { Category } from './../../models/category';
import { Product } from './../../models/product';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { CategoryService } from './../../services/category.service';
import { NavbarComponent } from "../navbar/navbar.component";
import { CommonModule } from '@angular/common';
import { CartSummaryComponent } from '../cart-summary/cart-summary.component';
import { CartService } from '../../services/cart.service'; 
import { CartItem } from '../../models/cartItem';
import { RouterModule } from '@angular/router';
import{ToastrModule, ToastrService} from "ngx-toastr"
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OrderStatus } from '../../models/orderStatus';


@Component({
  selector: 'shop', 
  standalone: true,
  imports: [FormsModule, NavbarComponent, CommonModule, CartSummaryComponent,RouterModule,ToastrModule], 
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css'] 
})
export class ShopComponent implements OnInit {
  @ViewChild(CartSummaryComponent) cartSummaryComponent!: CartSummaryComponent;
  constructor(
    private categoryService: CategoryService, 
    private productService: ProductService,
    private cartService: CartService,
    private toastr:ToastrService  ) {}

  products: Product[] = []; 
  displayedProducts: Product[] = []; 
  categories: Category[] = [];
  productsPerPage = 3;
  selectedPage = 1;
  selectedCategory: number | null = null; 
  totalProducts: number = 0; 
  userId: number = 1; 
  selectedProduct: Product | null = null;

  ngOnInit(): void {
    this.getCategories();
    this.getProducts(); 
  }

  getProducts() {
    this.productService.getProducts().subscribe({
      next: (response) => {
        this.products = response.data; 
        this.totalProducts = this.products.length; 
        this.updateProducts(); 
      },
      error: (err) => {
        console.error("Error fetching products: ", err);
      }
    });
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(response => {
      this.categories = response.data;
    });
  }

  getByCategory(categoryId: number) {
    this.selectedPage = 1; 
    this.selectedCategory = categoryId; 
    this.productService.getProductsByCategory(categoryId).subscribe({
      next: (response) => {
        this.products = response.data || []; 
        this.totalProducts = this.products.length; 
        this.updateProducts(); 
      },
      error: (error) => {
        console.error("Error fetching products by category: ", error);
      }
    });
  }
  
  changePage(page: number) {
    this.selectedPage = page; 
    this.updateProducts(); 
  }

  updateProducts() {
    const index = (this.selectedPage - 1) * this.productsPerPage; 
    const endIndex = index + this.productsPerPage;
    this.displayedProducts = this.products.slice(index, endIndex); 
  }
  
  get pageNumbers(): number[] {
    return Array(Math.ceil(this.totalProducts / this.productsPerPage))
      .fill(0)
      .map((_, i) => i + 1);
  }

  showAllProducts() {
    this.selectedCategory = null; 
    this.selectedPage = 1; 
    this.getProducts(); 
  }

  addToCart(productId: number): void {
    const cartItem: CartItem = {
      userId: this.userId,
      productId: productId,
      quantity: 1,
      status: OrderStatus.WAITING 
    };
  
    this.cartService.addToCart(cartItem).subscribe({
      next: (response) => {
        console.log('Ürün sepete eklendi:', response);
        this.toastr.success('Ürün sepete eklendi!', 'Başarılı');
        this.cartSummaryComponent.getTotalQuantity();
        this.cartSummaryComponent.getTotalPrice();
      },
      error: (error) => {
        console.error('Sepete eklerken hata oluştu:', error);
      }
    });
  }
  displayDetails(product:Product){
    this.selectedProduct=product

  }
  hideDetails(){
    this.selectedProduct=null
  }
}
