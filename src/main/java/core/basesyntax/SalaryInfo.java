package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class SalaryInfo {
    private static final int DATE_INDEX = 0;
    private static final int EMPLOYEE_NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int RATE_INDEX = 3;
    private static final int EMPLOYEE_NOT_FOUND = -1;
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
        List<String> employeeNames = Arrays.asList(names);

        for (String record : data) {
            String[] parts = record.split("\\s+");
            LocalDate workDate = LocalDate.parse(parts[DATE_INDEX], DATE_FORMAT);
            if (!workDate.isBefore(periodStart) && !workDate.isAfter(periodEnd)) {
                String employeeName = parts[EMPLOYEE_NAME_INDEX];
                int employeeIndex = employeeNames.indexOf(employeeName);
                if (employeeIndex != EMPLOYEE_NOT_FOUND) {
                    int hours = Integer.parseInt(parts[HOURS_INDEX]);
                    int rate = Integer.parseInt(parts[RATE_INDEX]);
                    salaries[employeeIndex] += hours * rate;
                }
            }
        }

        boolean isFirstEmployee = true;
        for (int employeeIndex = 0; employeeIndex < names.length; employeeIndex++) {
            if (!isFirstEmployee) {
                result.append(System.lineSeparator());
            }
            result.append(names[employeeIndex])
                    .append(" - ")
                    .append(salaries[employeeIndex]);
            isFirstEmployee = false;
        }
        return result.toString();
    }
}
