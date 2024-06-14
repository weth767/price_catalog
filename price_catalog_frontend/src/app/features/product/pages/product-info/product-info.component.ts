import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import lodash from 'lodash';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs';
import { ChartData } from '../../../../core/models/chart-data';
import { ChartOptions } from '../../../../core/models/chart-options';
import { Product } from '../../../../core/models/product';
import { ProductPrice } from '../../../../core/models/product-price';
import { ProductService } from '../../../../core/services/product/product.service';
import { DateUtil } from '../../../../shared/util/date-util';

@Component({
  templateUrl: './product-info.component.html',
  styleUrl: './product-info.component.scss',
})
export class ProductInfoComponent implements OnInit {
  productId?: number;
  @BlockUI() blockUI!: NgBlockUI;
  product?: Product;
  data?: ChartData;
  options?: ChartOptions;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id') ?? 0);
    this.blockUI.start();
    this.productService
      .getProductById(this.productId)
      .pipe(finalize(() => this.blockUI.stop()))
      .subscribe({
        next: (product) => {
          this.product = product;
          this.initChart();
        },
        error: (error: HttpErrorResponse) => {
          console.log(error);
          alert(error.message);
        },
      });
  }

  public get lessPrice(): ProductPrice | undefined {
    if (
      !this.product?.productPrices ||
      this.product.productPrices.length == 0
    ) {
      return undefined;
    }
    return this.product.productPrices.sort(
      (a, b) => (a.price ?? 0) - (b.price ?? 0)
    )[0];
  }

  public getDomainName(url?: string): string {
    if (!url || url.length == 0) {
      return 'Não disponível';
    }
    return new URL(url).hostname;
  }

  public getProductImage(imageUrl?: string): string {
    if (!imageUrl || imageUrl.length == 0) {
      return 'assets/box.png';
    }
    return imageUrl;
  }

  public get productPricesSorted(): ProductPrice[] {
    if (
      !this.product?.productPrices ||
      this.product.productPrices.length == 0
    ) {
      return [];
    }
    return this.product?.productPrices?.sort(
      (a, b) =>
        DateUtil.configureBrazilianLocale(a.dateTime).valueOf() -
        DateUtil.configureBrazilianLocale(b.dateTime).valueOf()
    );
  }

  public initChart(): void {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue(
      '--text-color-secondary'
    );
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    let dates = this.productPricesSorted?.map((productPrices) => {
      var date = DateUtil.configureBrazilianLocale(productPrices.dateTime);
      return date;
    });
    let labels = Object.keys(
      lodash.groupBy(dates, (item) => {
        return `${item.format('DD')} ${item.format('MMMM')} ${item.format(
          'YYYY'
        )}`;
      })
    );

    this.data = {
      labels: labels,
      datasets: [
        {
          label: 'Preços',
          data: this.productPricesSorted.map(
            (productPrice) => productPrice.price ?? 0
          ),
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          tension: 0.4,
        },
      ],
    };

    this.options = {
      maintainAspectRatio: false,
      aspectRatio: 0.6,
      plugins: {
        legend: {
          labels: {
            color: textColor,
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
        y: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
      },
    };
  }
}
