import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { finalize } from 'rxjs';
import { CustomDialogConfig } from '../../../../core/models/custom-dialog/custom-dialog-config';
import { CrawlerService } from '../../../../core/services/crawler/crawler.service';
import { CustomDialogService } from './../../../../core/services/custom-dialog/custom-dialog.service';

@Component({
  selector: 'app-domains-to-explore-dialog',
  templateUrl: './domains-to-explore-dialog.component.html',
  styleUrl: './domains-to-explore-dialog.component.scss',
})
export class DomainsToExploreDialogComponent {
  private readonly dialogRef = inject(
    MatDialogRef<DomainsToExploreDialogComponent>
  );
  public readonly data = inject<any>(MAT_DIALOG_DATA);
  public readonly form = new FormGroup({
    domains: new FormControl(
      '',
      Validators.compose([
        Validators.required,
        Validators.minLength(1),
        Validators.pattern('^https?://[^s,]+$'),
      ])
    ),
    reset: new FormControl(false, Validators.required),
    crawlers: new FormControl(
      1,
      Validators.compose([
        Validators.required,
        Validators.max(1000),
        Validators.min(1),
      ])
    ),
  });
  public loading = false;

  constructor(
    private customDialogService: CustomDialogService,
    private crawlerService: CrawlerService
  ) {}

  public addDomainsToExplore(): void {
    if (this.form.invalid) {
      let config = new CustomDialogConfig();
      config.header = 'Ocorreu um erro';
      config.data.text = 'Campos obrigatórios não preenchidos';
      config.data.showCancelButton = false;
      config.data.confirmButtonText = 'OK';
      this.customDialogService.showDialog(config);
      return;
    }
    this.loading = true;
    this.crawlerService
      .startExploration({
        links:
          this.form.value.domains?.split(',')?.map((url) => url.trim()) ?? [],
        reset: this.form.value.reset == true,
        crawlers: this.form.value.crawlers ?? 1,
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (value) => {
          let config = new CustomDialogConfig();
          config.header = 'Sucesso';
          config.data.text = value.message;
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          let uuid = this.customDialogService.showDialog(config);
          this.customDialogService.refs[uuid].onClose.subscribe({
            next: () => {
              this.close();
            },
          });
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
  public close(update = false): void {
    this.dialogRef.close(update);
  }
}
