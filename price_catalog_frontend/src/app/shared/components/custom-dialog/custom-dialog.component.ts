import { Component } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { twMerge } from 'tailwind-merge';
import { CustomDialogData } from '../../../core/models/custom-dialog/custom-dialog-data';

@Component({
  selector: 'app-custom-dialog',
  templateUrl: './custom-dialog.component.html',
  styleUrls: ['./custom-dialog.component.scss'],
})
export class CustomDialogComponent {
  visible = true;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig<CustomDialogData>
  ) {}

  public confirm(): void {
    this.config.data?.confirmFunction();
    this.ref.close();
  }

  public cancel(): void {
    this.config.data?.cancelFunction();
    this.ref.close();
  }

  public get wrapperClass(): string {
    return twMerge('flex flex-col w-full gap-4', this.config.styleClass);
  }

  public get textClass(): string {
    return twMerge('w-full text-base text-blue-gray-700 grow');
  }

  public get buttonsClass(): string {
    return twMerge(
      `flex-none w-full flex flex-row ${
        this.config.data?.showCancelButton ? 'justify-end' : 'justify-center'
      } align-items-center gap-1`
    );
  }
}
