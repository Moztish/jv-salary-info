package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data,
                                String dateFrom, String dateTo) {
        StringBuilder result = new StringBuilder();
        result.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append(System.lineSeparator());

        LocalDate periodStart = LocalDate.parse(dateFrom, DATE_FORMAT);
        LocalDate periodEnd = LocalDate.parse(dateTo, DATE_FORMAT);
        int[] salaries = new int[names.length];

        for (String record : data) {
            String[] parts = record.split("\\s+");
            LocalDate workDate = LocalDate.parse(parts[0], DATE_FORMAT);
            if (!workDate.isBefore(periodStart) && !workDate.isAfter(periodEnd)) {
                String employeeName = parts[1];
                int employeeIndex = -1;
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equals(employeeName)) {
                        employeeIndex = i;
                        break;
                    }
                }
                if (employeeIndex >= 0) {
                    int hours = Integer.parseInt(parts[2]);
                    int rate = Integer.parseInt(parts[3]);
                    salaries[employeeIndex] += hours * rate;
                }
            }
        }

        for (int i = 0; i < names.length; i++) {
            if (i > 0) {
                result.append(System.lineSeparator());
            }
            result.append(names[i])
                    .append(" - ")
                    .append(salaries[i]);
        }
        return result.toString();
    }
}
