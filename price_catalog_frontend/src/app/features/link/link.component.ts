import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { MenuItem } from 'primeng/api';
import { PaginatorState } from 'primeng/paginator';
import { finalize } from 'rxjs/internal/operators/finalize';
import { CustomDialogConfig } from '../../core/models/custom-dialog/custom-dialog-config';
import { Link } from '../../core/models/link';
import { Page } from '../../core/models/page';
import { CustomDialogService } from '../../core/services/custom-dialog/custom-dialog.service';
import { LinkService } from '../../core/services/link/link.service';
import { BreadcrumbUtils } from '../../shared/util/breadcrumb-utils';

@Component({
  selector: 'app-link',
  templateUrl: './link.component.html',
  styleUrl: './link.component.scss',
})
export class LinkComponent implements OnInit {
  @BlockUI() blockUI!: NgBlockUI;
  links?: Page<Link>;
  page = 0;
  pageSize = 30;
  first = 0;
  isLoading = false;
  domain: string | null;
  breadcrumbItems = new Array<MenuItem>();
  home!: MenuItem;

  constructor(
    private route: ActivatedRoute,
    private linkService: LinkService,
    private customDialogService: CustomDialogService
  ) {
    this.domain = this.route.snapshot.queryParamMap.get('dominio');
  }

  ngOnInit(): void {
    this.initBreadCrumb();
    this.getLinkPageabled(this.page, this.pageSize);
  }

  private initBreadCrumb(): void {
    let { items, home } = BreadcrumbUtils.initBreadcrumb([
      { label: 'Links', url: '/links' },
    ]);
    this.breadcrumbItems = items;
    this.home = home;
    if (this.domain != null) {
      items.push({ label: this.domain, url: `/links?dominio=${this.domain}` });
    }
    this.breadcrumbItems = items;
    this.home = home;
  }

  public getLinkPageabled(page: number, pageSize: number): void {
    this.blockUI.start('Carregando');
    this.isLoading = true;
    this.linkService
      .getDomainsPageabled(page, pageSize, this.domain)
      .pipe(
        finalize(() => {
          this.blockUI.stop();
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (links) => {
          this.links = links;
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

  public onPageChange(event: PaginatorState) {
    this.first = event.first ?? 0;
    this.page = event.page ?? 0;
    this.pageSize = event.rows ?? 30;
    this.getLinkPageabled(this.page, this.pageSize);
  }

  public get isEmpty(): boolean {
    return this.links?.isEmpty ?? true;
  }

  public get isNotEmpty(): boolean {
    return this.links?.isNotEmpty ?? false;
  }
}
