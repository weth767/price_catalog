import { registerLocaleData } from '@angular/common';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import localePt from '@angular/common/locales/pt';
import { DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { BlockUIModule } from 'ng-block-ui';
import { DialogService } from 'primeng/dynamicdialog';
import { AngularMaterialModule } from './angular-material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { httpErrorInterceptorInterceptor } from './core/interceptors/http-error/http-error-interceptor.interceptor';
import { httpRequestInterceptor } from './core/interceptors/http-request.interceptor';
import { CustomDialogService } from './core/services/custom-dialog/custom-dialog.service';
import { SharedModule } from './shared/shared.module';

registerLocaleData(localePt);

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    AngularMaterialModule,
    BlockUIModule.forRoot(),
  ],
  providers: [
    provideAnimationsAsync(),
    provideHttpClient(
      withInterceptors([
        httpErrorInterceptorInterceptor,
        httpRequestInterceptor,
      ])
    ),
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    { provide: DEFAULT_CURRENCY_CODE, useValue: 'BRL' },
    DialogService,
    CustomDialogService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
