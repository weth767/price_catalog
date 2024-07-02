import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  Component,
  OnInit,
  signal,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { PaginatorState } from 'primeng/paginator';
import { finalize } from 'rxjs/internal/operators/finalize';
import { CustomDialogConfig } from '../../core/models/custom-dialog/custom-dialog-config';
import { Page } from '../../core/models/page';
import { Product } from '../../core/models/product';
import { CustomDialogService } from '../../core/services/custom-dialog/custom-dialog.service';
import { ProductService } from '../../core/services/product/product.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchComponent implements OnInit {
  @BlockUI() blockUI!: NgBlockUI;
  public products?: Page<Product>;
  page = 0;
  pageSize = 20;
  first = 0;
  isLoading = signal(false);
  description = '';

  constructor(
    private productService: ProductService,
    private customDialogService: CustomDialogService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.description = this.route.snapshot.queryParamMap.get('descricao') ?? '';
    console.log(this.description);
    this.getData(this.description, this.page, this.pageSize);
  }

  public getData(description: string, page: number, pageSize: number): void {
    this.blockUI.start('Carregando');
    this.isLoading.set(true);
    this.productService
      .getProductsByDescription(description, page, pageSize)
      .pipe(
        finalize(() => {
          this.blockUI.stop();
          this.isLoading.set(false);
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
    this.getData(this.description, this.page, this.pageSize);
  }

  public get isEmpty(): boolean {
    return this.products?.isEmpty ?? true;
  }

  public get isNotEmpty(): boolean {
    return this.products?.isNotEmpty ?? false;
  }
}
