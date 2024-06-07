export class ChartOptions {
  maintainAspectRatio?: boolean;
  aspectRatio?: number;
  plugins?: {
    legend: {
      labels: {
        color: string;
      };
    };
  };
  scales?: {
    x: {
      ticks: {
        color: string;
      };
      grid: {
        color: string;
        drawBorder: boolean;
      };
    };
    y: {
      ticks: {
        color: string;
      };
      grid: {
        color: string;
        drawBorder: boolean;
      };
    };
  };
}
