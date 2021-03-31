package by.ralovets.qportal.service.util;

import java.util.List;

/**
 * Allows you to get a specific page with a certain number of elements from the collection
 *
 * @param <T> - any type
 */
public class Paginator<T> {

    private final List<T> elements;

    public Paginator(List<T> elements) {
        this.elements = elements;
    }

    /**
     * @param page     - number greater than 0
     * @param pageSize - number greater than 0
     * @return list of elements from page
     */
    public List<T> getElements(int page, int pageSize) {
        if (page < 0 || pageSize < 0) return null;

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
