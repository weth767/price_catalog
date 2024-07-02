import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { PaginatorState } from 'primeng/paginator';
import { finalize } from 'rxjs/internal/operators/finalize';
import { Brand } from '../../core/models/brand';
import { CustomDialogConfig } from '../../core/models/custom-dialog/custom-dialog-config';
import { Page } from '../../core/models/page';
import { BrandService } from '../../core/services/brand/brand.service';
import { CustomDialogService } from '../../core/services/custom-dialog/custom-dialog.service';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrl: './brand.component.scss',
})
export class BrandComponent implements OnInit {
  @BlockUI() blockUI!: NgBlockUI;
  public brands?: Page<Brand>;
  page = 0;
  pageSize = 30;
  first = 0;
  isLoading = false;

  constructor(
    private brandService: BrandService,
    private customDialogService: CustomDialogService
  ) {}

  ngOnInit(): void {
    this.getData(this.page, this.pageSize);
  }

  public getData(page: number, pageSize: number): void {
    this.blockUI.start('Carregando');
    this.isLoading = true;
    this.brandService
      .getCountedBrandsPage(page, pageSize)
      .pipe(
        finalize(() => {
          this.blockUI.stop();
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (brands) => {
          this.brands = brands;
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

  public onPageChange(event: PaginatorState) {
    this.first = event.first ?? 0;
    this.page = event.page ?? 0;
    this.pageSize = event.rows ?? 30;
    this.getData(this.page, this.pageSize);
  }

  public get isEmpty(): boolean {
    return this.brands?.isEmpty ?? true;
  }

  public get isNotEmpty(): boolean {
    return this.brands?.isNotEmpty ?? false;
  }
}
