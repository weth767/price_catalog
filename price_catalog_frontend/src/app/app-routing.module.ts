import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./features/home/home.module').then((m) => m.HomeModule),
    canActivate: [authGuard],
  },
  {
    path: 'produtos',
    loadChildren: () =>
      import('./features/product/product.module').then((m) => m.ProductModule),
    canActivate: [authGuard],
  },
  {
    path: 'marcas',
    loadChildren: () =>
      import('./features/brand/brand.module').then((m) => m.BrandModule),
    canActivate: [authGuard],
  },
  {
    path: 'usuario',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'dominios',
    loadChildren: () =>
      import('./features/domain/domain.module').then((m) => m.DomainModule),
    canActivate: [authGuard],
  },
  {
    path: 'links',
    loadChildren: () =>
      import('./features/link/link.module').then((m) => m.LinkModule),
    canActivate: [authGuard],
  },
  {
    path: 'buscar',
    loadChildren: () =>
      import('./features/search/search.module').then((m) => m.SearchModule),
    canActivate: [authGuard],
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
