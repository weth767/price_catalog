import { environment } from '../../../environments/environment';
import { Crypto } from './crypto';

export class LocalStorage {
  private static encryptKey: string = environment.ENCRYPT_KEY ?? '';
  private static encryptIV: Array<number> =
    environment.ENCRYPT_IV?.split(',').map((element) => Number(element)) ??
    new Array<number>();

  public static setItem(key: string, data: any, encrypt = true): void {
    if (typeof window === 'undefined' || !window.localStorage) {
      return;
    }
    if (!data) {
      return;
    }
    if (encrypt) {
      const cipherText = Crypto.encryptAES(
        JSON.stringify(data),
        this.encryptKey,
        this.encryptIV
      );
      window.localStorage.setItem(key, cipherText);
      return;
    }
    const json = JSON.stringify(data);
    window.localStorage.setItem(key, json);
  }

  public static getItem(key: string, encrypted = true): unknown | undefined {
    if (typeof window === 'undefined' || !window.localStorage) {
      return undefined;
    }
    if (encrypted) {
      const item = window.localStorage.getItem(key);
      return item
        ? JSON.parse(Crypto.decryptAES(item, this.encryptKey, this.encryptIV))
        : undefined;
    }
    const item = window.localStorage.getItem(key);
    return item ? JSON.parse(item) : undefined;
  }

  public static getItemAsString(
    key: string,
    encrypted = true
  ): unknown | undefined {
    if (typeof window === 'undefined' || !window.localStorage) {
      return undefined;
    }
    const item = window.localStorage.getItem(key);
    if (!item) {
      return undefined;
    }
    if (encrypted) {
      return Crypto.decryptAES(item, this.encryptKey, this.encryptIV);
    }
    return item;
  }

  public static hasKey(key: string): boolean {
    if (typeof window === 'undefined' || !window.localStorage) {
      return false;
    }
    return key in window.localStorage;
  }

  public static removeItem(key: string): void {
    if (typeof window === 'undefined' || !window.localStorage) {
      return;
    }
    window.localStorage.removeItem(key);
  }

  public static clear(): void {
    if (typeof window === 'undefined' || !window.localStorage) {
      return;
    }
    window.localStorage.clear();
  }
}
