/*
 * Copyright 2009 Denys Pavlov, Igor Azarnyi
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.search.dto.impl;

import org.apache.lucene.search.Query;
import org.springframework.util.CollectionUtils;
import org.yes.cart.search.dto.NavigationContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: denispavlov
 * Date: 25/11/2014
 * Time: 22:25
 */
public class LuceneNavigationContextImpl implements NavigationContext<Query> {

    private final long shopId;
    private final long customerShopId;
    private final List<Long> categories;
    private final boolean includeSubCategories;
    private final Map<String, List<String>> navigationParameters;

    private final Query productQuery;
    private final Query productSkuQuery;

    public LuceneNavigationContextImpl(final long shopId,
                                       final long customerShopId,
                                       final List<Long> categories,
                                       final boolean includeSubCategories,
                                       final Map<String, List<String>> navigationParameters,
                                       final Query productQuery,
                                       final Query productSkuQuery) {
        this.shopId = shopId;
        this.customerShopId = customerShopId;
        this.categories = categories;
        this.includeSubCategories = includeSubCategories;
        if (navigationParameters == null) {
            this.navigationParameters = Collections.EMPTY_MAP;
        } else {
            this.navigationParameters = navigationParameters;
        }
        this.productSkuQuery = productSkuQuery;
        this.productQuery = productQuery;
    }

    /**
     * {@inheritDoc}
     */
    public long getShopId() {
        return shopId;
    }

    /**
     * {@inheritDoc}
     */
    public long getCustomerShopId() {
        return customerShopId;
    }

    /**
     * {@inheritDoc}
     */
    public List<Long> getCategories() {
        return categories;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isIncludeSubCategories() {
        return includeSubCategories;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isGlobal() {
        return CollectionUtils.isEmpty(categories);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isFilteredBy(final String attribute) {
        return navigationParameters.containsKey(attribute);
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, List<String>> getFilterParameters() {
        return Collections.unmodifiableMap(navigationParameters);
    }

    /**
     * {@inheritDoc}
     */
    public Query getProductQuery() {
        return productQuery;
    }

    /**
     * {@inheritDoc}
     */
    public Query getProductSkuQuery() {
        return productSkuQuery;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final LuceneNavigationContextImpl that = (LuceneNavigationContextImpl) o;

        if (shopId != that.shopId) return false;
        if (customerShopId != that.customerShopId) return false;
        if (includeSubCategories != that.includeSubCategories) return false;
        if (categories != null) {
            if (that.categories == null || categories.size() != that.categories.size()) return false;
            for (int i = 0; i < categories.size(); i++) {
                if (!categories.get(i).equals(that.categories.get(i))) return false;
            }
        } else if (that.categories != null) return false;

        if (navigationParameters.size() != that.navigationParameters.size()) return false;
        for (final Map.Entry<String, List<String>> entry : navigationParameters.entrySet()) {
            final List<String> thatValue = that.navigationParameters.get(entry.getKey());
            if (entry.getValue() != null) {
                if (thatValue == null || entry.getValue().size() != thatValue.size()) return false;
                for (int i = 0; i < thatValue.size(); i++) {
                    if (!entry.getValue().get(i).equals(thatValue.get(i))) return false;
                }
            } else if (thatValue != null) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (shopId ^ (shopId >>> 32));
        result = 31 * result + (int) (customerShopId ^ (customerShopId >>> 32));
        result = 31 * result + (includeSubCategories ? 1 : 0);
        if (categories != null) {
            for (final Long category : categories) {
                result = 31 * result + category.hashCode();
            }
        }
        for (final Map.Entry<String, List<String>> entry : navigationParameters.entrySet()) {
            result = 31 * result + entry.getKey().hashCode();
            if (entry.getValue() != null) {
                for (final String value : entry.getValue()) {
                    result = 31 * result + value.hashCode();
                }
            }
        }
        return result;
    }
}
