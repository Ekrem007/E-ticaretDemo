import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './shop/shop/shop.component';
import { NgModule } from '@angular/core';
import { CheckoutComponent } from './shop/checkout/checkout.component';
import { CartDetailComponent } from './shop/cart-detail/cart-detail.component';
import { OrderComponent } from './shop/order/order.component';



export const routes: Routes = [
    { path: "", pathMatch: "full", component: ShopComponent },
    { path: "shop", component: ShopComponent },
    { path: "cart", component: CartDetailComponent },
    { path: "checkout", component: CheckoutComponent },
    { path: "order", component: OrderComponent },
    { path: "admin",loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)},
    { path: "**", redirectTo: "/shop" }
];


@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule],
     
    
})
export class AppRoutingModule{}