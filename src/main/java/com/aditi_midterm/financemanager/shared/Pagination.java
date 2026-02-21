package com.aditi_midterm.financemanager.shared;

import java.util.List;

public record Pagination<T>(
     List<T> data,
     int page,
     int size,
     long totalElements,
     long totalPages,
     long last

) {

}
