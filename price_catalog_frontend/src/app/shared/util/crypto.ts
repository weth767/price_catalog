import CryptoJS from 'crypto-js';

export class Crypto {
  public static encryptAES(text: string, key: string, iv: number[]): string {
    const ivKey = CryptoJS.lib.WordArray.create(iv);
    const cipher = CryptoJS.AES.encrypt(text, CryptoJS.enc.Utf8.parse(key), {
      mode: CryptoJS.mode.CBC,
      iv: ivKey,
    });
    return cipher.toString();
  }

  public static decryptAES(
    cipherText: string,
    key: string,
    iv: number[]
  ): string {
    const ivKey = CryptoJS.lib.WordArray.create(iv);
    const cipher = CryptoJS.AES.decrypt(
      cipherText,
      CryptoJS.enc.Utf8.parse(key),
      {
        mode: CryptoJS.mode.CBC,
        iv: ivKey,
      }
    );
    return cipher.toString(CryptoJS.enc.Utf8);
  }

  public static encryptAESBase64(
    text: string,
    key: string,
    iv: number[]
  ): string {
    const ivKey = CryptoJS.lib.WordArray.create(iv);
    const cipher = CryptoJS.AES.encrypt(text, CryptoJS.enc.Utf8.parse(key), {
      mode: CryptoJS.mode.CBC,
      iv: ivKey,
    });
    const utf8Text = CryptoJS.enc.Utf8.parse(cipher.toString());
    const base64Text = CryptoJS.enc.Base64.stringify(utf8Text);
    return base64Text;
  }

  public static decryptBase64AES(
    cipherBase64: string,
    key: string,
    iv: number[]
  ): string {
    const ivKey = CryptoJS.lib.WordArray.create(iv);
    const base64 = CryptoJS.enc.Base64.parse(cipherBase64);
    const cipherText = CryptoJS.enc.Utf8.stringify(base64);
    const cipher = CryptoJS.AES.decrypt(
      cipherText,
      CryptoJS.enc.Utf8.parse(key),
      {
        mode: CryptoJS.mode.CBC,
        iv: ivKey,
      }
    );
    return cipher.toString(CryptoJS.enc.Utf8);
  }

  public static sha512(key: string): string {
    return CryptoJS.SHA512(key).toString();
  }
}
