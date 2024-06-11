import { CustomDialogData } from './custom-dialog-data';

export class CustomDialogConfig {
  data: CustomDialogData = new CustomDialogData();
  header = '';
  showHeader = true;
  width?: string;
  height?: string;
  contentStyle = { overflow: 'auto' };
  styleClass = 'custom-dialog';
  closable = true;
}
