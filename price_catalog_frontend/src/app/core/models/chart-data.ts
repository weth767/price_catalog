export class ChartData {
  labels?: string[];
  datasets?: {
    type?: string;
    data: any[];
    label: string;
    fill: boolean;
    borderColor: string;
    tension?: number;
  }[];
}
