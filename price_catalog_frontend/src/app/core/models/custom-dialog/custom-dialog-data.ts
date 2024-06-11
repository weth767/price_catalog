export class CustomDialogData {
  showCancelButton = false;
  cancelButtonText = 'Cancelar';
  cancelButtonStyle = 'abc-button-error';
  cancelButtonIcon = 'pi pi-times';
  cancelFunction = (): any => {};
  confirmButtonText = 'Confirmar';
  confirmButtonStyle = 'abc-button-success';
  confirmButtonIcon = 'pi pi-check';
  confirmFunction = (): any => {};
  text = '';
}
