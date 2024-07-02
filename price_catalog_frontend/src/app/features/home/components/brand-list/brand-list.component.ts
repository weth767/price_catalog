import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/internal/operators/finalize';
import { CountBrand } from '../../../../core/models/count-brand';
import { CustomDialogConfig } from '../../../../core/models/custom-dialog/custom-dialog-config';
import { Page } from '../../../../core/models/page';
import { BrandService } from '../../../../core/services/brand/brand.service';
import { CustomDialogService } from '../../../../core/services/custom-dialog/custom-dialog.service';

@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrl: './brand-list.component.scss',
})
export class BrandListComponent implements OnInit {
  brands?: Page<CountBrand>;
  public loading = false;

  constructor(
    private brandService: BrandService,
    private customDialogService: CustomDialogService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.brandService
      .getCountedBrandsPage(0, 10)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (brand) => {
          this.brands = brand;
        },
        error: (error) => {
          let config = new CustomDialogConfig();
          config.header = 'Ocorreu um erro';
          config.data.text = error?.message;
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          this.customDialogService.showDialog(config);
        },
      });
  }
}
