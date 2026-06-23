package server.api.common.helpers;

import org.springframework.data.domain.Sort;

public class SortingDbHelper {

  private final String sortBy;
  private final Sort.Direction orderSort;

  public SortingDbHelper(String sortBy, Sort.Direction orderBy) {
    this.sortBy = sortBy;
    this.orderSort = orderBy;
  }

  public Sort getSort() {
    return Sort.by(orderSort, sortBy);
  }
}
