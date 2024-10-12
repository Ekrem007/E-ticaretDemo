import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent implements OnInit {

  categories: Category[] = [];
  editing: boolean = false;
  category: Category = {
      id: 0,
      name: ''
  };

  constructor(private activeRoute: ActivatedRoute, 
              private router: Router,
              private categoryService: CategoryService) {
    this.editing = activeRoute.snapshot.params['mode'] === 'edit';
    if (this.editing) {
      const categoryId = activeRoute.snapshot.params['id'];
      this.getCategoryById(categoryId);
    }
  }

  ngOnInit(): void {
  }

  getCategoryById(id: number): void {
    this.categoryService.getCategoryById(id).subscribe({
      next: (response) => {
        this.category = response.data;
      },
      error: (err) => {
        console.error('Error fetching category by ID:', err);
      }
    });
  }

  save(form: NgForm): void {
    if (form.invalid) {
      console.error('Form is invalid');
      return; 
    }

    const category: Category = {
      id: this.category.id,
      name: this.category.name,
    };

    if (this.editing) {
      this.categoryService.updateCategory(category, this.category.id).subscribe({
        next: (response) => {
          console.log('Category updated successfully:', response);
          this.router.navigate(['/admin/main/categories']);
        },
        error: (err) => {
          console.error('Error updating category:', err);
        }
      });
    } else {
      this.categoryService.addCategory(category).subscribe({
        next: (response) => {
          console.log('Category added successfully:', response);
          this.router.navigate(['/admin/main/categories']);
        },
        error: (err) => {
          console.error('Error adding category:', err);
        }
      });
    }
  }
}
