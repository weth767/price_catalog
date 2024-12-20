import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { PaginatorState } from 'primeng/paginator';
import { finalize } from 'rxjs';
import { CustomDialogConfig } from '../../core/models/custom-dialog/custom-dialog-config';
import { Page } from '../../core/models/page';
import { Product } from '../../core/models/product';
import { CustomDialogService } from '../../core/services/custom-dialog/custom-dialog.service';
import { ProductService } from '../../core/services/product/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss',
})
export class ProductComponent implements OnInit {
  @BlockUI() blockUI!: NgBlockUI;
  public products?: Page<Product>;
  page = 0;
  pageSize = 20;
  first = 0;
  isLoading = false;

  constructor(
    private productService: ProductService,
    private customDialogService: CustomDialogService
  ) {}

  ngOnInit(): void {
    this.getData(this.page, this.pageSize);
  }

  public getData(page: number, pageSize: number): void {
    this.blockUI.start('Carregando');
    this.isLoading = true;
    this.productService
      .getProducts(page, pageSize)
      .pipe(
        finalize(() => {
          this.blockUI.stop();
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (products: Page<Product>) => {
          this.products = products;
        },
        error: (error: HttpErrorResponse) => {
          let config = new CustomDialogConfig();
          config.header = 'Ocorreu um erro';
          config.data.text = error?.message;
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          this.customDialogService.showDialog(config);
        },
      });
  }

  public onPageChange(event: PaginatorState): void {
    this.page = event.page ?? 0;
    this.first = event.first ?? 0;
    this.pageSize = event.rows ?? 20;
    this.getData(this.page, this.pageSize);
  }

  public get isEmpty(): boolean {
    return this.products?.isEmpty ?? true;
  }

  public get isNotEmpty(): boolean {
    return this.products?.isNotEmpty ?? false;
  }
}
