import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { finalize, forkJoin } from 'rxjs';
import { CustomDialogConfig } from '../../../../core/models/custom-dialog/custom-dialog-config';
import { Link } from '../../../../core/models/link';
import { Page } from '../../../../core/models/page';
import { CrawlerService } from '../../../../core/services/crawler/crawler.service';
import { CustomDialogService } from './../../../../core/services/custom-dialog/custom-dialog.service';

@Component({
  selector: 'app-link-table',
  templateUrl: './link-table.component.html',
  styleUrl: './link-table.component.scss',
})
export class LinkTableComponent {
  @Input() links?: Page<Link>;
  columns = ['URL', 'Verificado', 'Verificado em', 'Ações'];
  loading = false;

  constructor(
    private crawlerService: CrawlerService,
    private customDialogService: CustomDialogService
  ) {}

  public exploreLinkAgain(link: Link): void {
    this.loading = true;
    forkJoin([
      this.crawlerService.startExploration({
        links: [link.url!],
        reset: true,
        crawlers: 1000,
      }),
      this.crawlerService.verifyIsCrawlerRunning(),
    ])
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (response) => {
          const [_, isRunning] = response;
          let config = new CustomDialogConfig();
          config.header = 'Informação';
          config.data.text = isRunning
            ? 'O Link foi adicionado à fila, mas há outros links sendo verificados, então ele será verificado quando chegar sua vez.'
            : 'O Link foi adicionado à fila para a verificação';
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          this.customDialogService.showDialog(config);
        },
        error: (err: HttpErrorResponse) => {
          let config = new CustomDialogConfig();
          config.header = 'Ocorreu um erro';
          config.data.text = err?.message;
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          this.customDialogService.showDialog(config);
        },
      });
  }
}
