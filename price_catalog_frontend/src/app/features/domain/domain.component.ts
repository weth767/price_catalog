import { Component, OnInit } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { MenuItem } from 'primeng/api';
import { PaginatorState } from 'primeng/paginator';
import { finalize } from 'rxjs/internal/operators/finalize';
import { Domain } from '../../core/models/domain';
import { Page } from '../../core/models/page';
import { DomainService } from '../../core/services/domain/domain.service';
import { BreadcrumbUtils } from '../../shared/util/breadcrumb-utils';

@Component({
  selector: 'app-domain',
  templateUrl: './domain.component.html',
  styleUrl: './domain.component.scss',
})
export class DomainComponent implements OnInit {
  @BlockUI() blockUI!: NgBlockUI;
  domains?: Page<Domain>;
  page = 0;
  pageSize = 30;
  first = 0;
  isLoading = false;
  breadcrumbItems = new Array<MenuItem>();
  home!: MenuItem;

  constructor(private domainService: DomainService) {}

  ngOnInit(): void {
    this.initBreadCrumb();
    this.getDomainsPageabled(this.page, this.pageSize);
  }

  private initBreadCrumb(): void {
    const { items, home } = BreadcrumbUtils.initBreadcrumb([
      { label: 'Domínios', url: '/dominios' },
    ]);
    this.breadcrumbItems = items;
    this.home = home;
  }

  public getDomainsPageabled(page: number, pageSize: number): void {
    this.blockUI.start('Carregando');
    this.isLoading = true;
    this.domainService
      .getDomainsPageabled(page, pageSize)
      .pipe(
        finalize(() => {
          this.blockUI.stop();
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (domains) => {
          this.domains = domains;
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  public onPageChange(event: PaginatorState) {
    this.first = event.first ?? 0;
    this.page = event.page ?? 0;
    this.pageSize = event.rows ?? 30;
    this.getDomainsPageabled(this.page, this.pageSize);
  }

  public get isEmpty(): boolean {
    return this.domains?.isEmpty ?? true;
  }

  public get isNotEmpty(): boolean {
    return this.domains?.isNotEmpty ?? false;
  }
}
