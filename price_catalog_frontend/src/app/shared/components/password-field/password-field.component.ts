import {
  AfterViewInit,
  Component,
  EventEmitter,
  INJECTOR,
  Inject,
  Injector,
  Input,
  Output,
  forwardRef,
  signal,
} from '@angular/core';
import {
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR,
  NgControl,
} from '@angular/forms';

@Component({
  selector: 'app-password-field',
  templateUrl: './password-field.component.html',
  styleUrl: './password-field.component.scss',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => PasswordFieldComponent),
    },
  ],
})
export class PasswordFieldComponent
  implements ControlValueAccessor, AfterViewInit
{
  @Input() label = 'Senha';
  @Input() value = '';
  @Input() disabled = false;
  @Input() required = false;
  @Output() onValueChanged = new EventEmitter<string>();
  onChanged = (value: string) => {};
  onTouched = () => {};
  type = signal<'text' | 'password'>('password');
  _control?: FormControl;

  constructor(@Inject(INJECTOR) private injector: Injector) {}

  ngAfterViewInit(): void {
    this._control = this.injector.get(NgControl)?.control as FormControl;
  }

  public get icon(): string {
    return this.type() === 'password' ? 'visibility' : 'visibility_off';
  }

  public changeType(): void {
    this.type.set(this.type() == 'password' ? 'text' : 'password');
  }

  writeValue(value: string): void {
    this.value = value;
    this.onChanged(value);
    this.onValueChanged.emit(value);
  }
  registerOnChange(fn: (value: string) => void): void {
    this.onChanged = fn;
  }
  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  public onChangeValue(): void {
    this.onValueChanged.emit(this.value);
    this.onChanged(this.value);
    this.onTouched();
  }
}
