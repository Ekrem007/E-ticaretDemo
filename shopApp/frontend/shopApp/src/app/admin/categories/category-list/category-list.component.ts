import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../services/category.service';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit {

  constructor(private categoryService:CategoryService,private router: Router){}

  categories:Category[]=[]
  ngOnInit(): void {
    this.getCategories();
  }
  deleteCategory(categoryId: number): void {
    if (confirm('Bu kategoriyi silmek istediğinize emin misiniz?')) {
      this.categoryService.deleteCategory(categoryId).subscribe({
        next: (response) => {
          console.log('category deleted successfully:', response);
          this.getCategories();
          this.router.navigate(['/admin/main/categories']);
        },
        error: (err) => {
          console.error('Error deleting category:', err);
        }
      });
    }
  }
  getCategories(){
    this.categoryService.getCategories().subscribe({
      next: (response) => {
        this.categories = response.data; 
      },
      error: (err) => {
        console.error("Error fetching categories: ", err);
      }
    });

    }


  }


