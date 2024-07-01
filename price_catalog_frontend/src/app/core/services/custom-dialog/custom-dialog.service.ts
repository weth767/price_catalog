import { Injectable } from '@angular/core';
import { Dictionary } from 'lodash';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import * as UUID from 'uuid';
import { CustomDialogComponent } from '../../../shared/components/custom-dialog/custom-dialog.component';
import { CustomDialogConfig } from '../../models/custom-dialog/custom-dialog-config';

@Injectable({
  providedIn: 'root',
})
export class CustomDialogService {
  private dialogRefs: Dictionary<DynamicDialogRef> = {};

  constructor(private dialogService: DialogService) {}

  public showDialog(config: CustomDialogConfig): string {
    const uuid = UUID.v4();
    this.dialogRefs[uuid] = this.dialogService.open(CustomDialogComponent, {
      width: config.width,
      height: config.height,
      contentStyle: config.contentStyle,
      header: config.header,
      showHeader: config.showHeader,
      data: config.data,
      styleClass: config.styleClass,
      closable: config.closable,
    });
    return uuid;
  }

  public closeDialogByRef(ref: DynamicDialogRef): void {
    for (let uuid in this.dialogRefs) {
      if (this.dialogRefs[uuid] === ref) {
        this.dialogRefs[uuid].close();
        delete this.dialogRefs[uuid];
      }
    }
  }

  public closeDialog(uuid: string): void {
    if (Object.keys(this.dialogRefs).includes(uuid)) {
      this.dialogRefs[uuid].close();
      this.dialogRefs[uuid].destroy();
      delete this.dialogRefs[uuid];
    }
  }

  public closeAll(): void {
    for (let uuid in this.dialogRefs) {
      this.dialogRefs[uuid].close();
      this.dialogRefs[uuid].destroy();
      delete this.dialogRefs[uuid];
    }
    this.dialogRefs = {};
  }

  public get refs(): Dictionary<DynamicDialogRef> {
    return this.dialogRefs;
  }
}
