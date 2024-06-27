import { MenuItem } from 'primeng/api';

export class BreadcrumbUtils {
  public static initBreadcrumb(currentUrls: { label: string; url: string }[]): {
    items: MenuItem[];
    home: MenuItem;
  } {
    let breadcrumbItems = new Array<MenuItem>();
    currentUrls?.forEach((url) => {
      breadcrumbItems.push({ label: url.label, routerLink: url.url });
    });
    const home = { icon: 'pi pi-home', routerLink: '/', label: 'Início' };
    return { items: breadcrumbItems, home };
  }
}
