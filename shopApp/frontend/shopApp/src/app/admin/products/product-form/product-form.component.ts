import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { ProductAddDto } from '../../../models/productAddDto';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {

  constructor(private activeRoute: ActivatedRoute, 
              private productService: ProductService,
              private router: Router,
              private categoryService:CategoryService) {
    this.editing = activeRoute.snapshot.params['mode'] === 'edit';
    if (this.editing) {
      const productId = activeRoute.snapshot.params['id'];
      this.getProductById(productId);
    }
  }
  
  categories: Category[] = [];
  editing: boolean = false;
  product: Product = {
    id: 0,
    name: '',
    price: 0,
    description: '',
    imageUrl: '',
    category: {
      categoryId: 0,
      name: ''
    }
  };

  ngOnInit(): void { 
    this.getCategories();
  }

  getProductById(id: number): void {
    this.productService.getProductsById(id).subscribe({
      next: (response) => {
        this.product = response.data;
      },
      error: (err) => {
        console.error('Error fetching product by ID:', err);
      }
    });
  }

  save(form: NgForm): void {
    if (form.invalid) {
        console.error('Form is invalid');
        return; 
    }

    const productToAdd: ProductAddDto = {
        id: this.product.id,
        name: this.product.name,
        price: this.product.price,
        description: this.product.description,
        imageUrl: this.product.imageUrl,
        categoryId: this.product.category.categoryId 
    };

    if (this.editing) {
        this.productService.updateProduct(productToAdd, this.product.id).subscribe({
            next: (response) => {
                console.log('Product updated successfully:', response);
                this.router.navigate(['/admin/main/products']);
            },
            error: (err) => {
                console.error('Error updating product:', err);
            }
        });
    } else {
        this.productService.addProduct(productToAdd).subscribe({
            next: (response) => {
                console.log('Product added successfully:', response);
                this.router.navigate(['/admin/main/products']);
            },
            error: (err) => {
                console.error('Error adding product:', err);
            }
        });
    }
}
  getCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (response) => {
        this.categories = response.data; 
      },
      error: (err) => {
        console.error('Error fetching categories:', err);
      }
    });
  }

}
