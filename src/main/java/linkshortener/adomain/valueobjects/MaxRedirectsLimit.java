package linkshortener.adomain.valueobjects;

public class MaxRedirectsLimit {

    private final Integer limit;

    public MaxRedirectsLimit(Integer limit) {
        if (!isValidLimit(limit)) {
            throw new IllegalArgumentException("Некорректное значение лимита: " + limit);
        }
        this.limit = limit;
    }

    public MaxRedirectsLimit(String limit) {
        try {
            int parsedLimit = Integer.parseInt(limit);
            if (!isValidLimit(parsedLimit)) {
                throw new IllegalArgumentException("Некорректное значение лимита: " + limit);
            }
            this.limit = parsedLimit;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Лимит должен быть целым числом: " + limit, e);
        }
    }

    public Integer getLimit() {
        return limit;
    }

    private boolean isValidLimit(Integer limit) {
        // Лимит должен быть положительным числом и не превышать определенного значения (например, 1000)
        return limit != null && limit > 0 && limit <= 1000;
    }

    @Override
    public String toString() {
        return String.valueOf(limit);
    }

}
