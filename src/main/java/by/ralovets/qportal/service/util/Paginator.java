package by.ralovets.qportal.service.util;

import java.util.List;

public class Paginator<T> {

    private final List<T> elements;

    public Paginator(List<T> elements) {
        this.elements = elements;
    }

    public List<T> getElements(int page, int pageSize) {
        int startIndex = page * pageSize - pageSize;
        if (startIndex >= elements.size()) return null;

        int endIndex = Math.min(startIndex + pageSize, elements.size());
        return elements.subList(startIndex, endIndex);
    }

    public List<T> getElements() {
        return elements;
    }

    public Integer getTotalPages(int pageSize) {
        return (int) Math.ceil(elements.size() / ((double) pageSize));
    }

    public Integer getTotalElements() {
        return elements.size();
    }
}
