import moment from 'moment';

export class DateUtil {
  public static getLastMonths(
    amount: number,
    includeCurrentMonth: boolean
  ): string[] {
    DateUtil.configureBrazilianLocale();
    var monthNames: string[] = [];
    const start = moment().startOf('month');
    if (includeCurrentMonth) {
      monthNames.push(start.format('MMMM YYYY'));
    }
    for (let i = 0; i < amount; i++) {
      monthNames.push(start.subtract(1, 'month').format('MMMM YYYY'));
    }
    return monthNames;
  }

  public static get configureBrazilianLocale(): typeof moment {
    moment.locale('pt');
    moment.updateLocale('pt', {
      months: [
        'Janeiro',
        'Fevereiro',
        'Março',
        'Abril',
        'Maio',
        'Junho',
        'Julho',
        'Agosto',
        'Setembro',
        'Outubro',
        'Novembro',
        'Dezembro',
      ],
    });
    return moment;
  }

  public static getMonthsNames(): string[] {
    return [
      'Janeiro',
      'Fevereiro',
      'Março',
      'Abril',
      'Maio',
      'Junho',
      'Julho',
      'Agosto',
      'Setembro',
      'Outubro',
      'Novembro',
      'Dezembro',
    ];
  }
}
