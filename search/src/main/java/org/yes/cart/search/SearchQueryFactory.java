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

package org.yes.cart.search;

import org.yes.cart.domain.dto.ProductSearchResultDTO;
import org.yes.cart.search.dto.NavigationContext;

import java.util.List;
import java.util.Map;

/**
 * User: denispavlov
 * Date: 27/03/2017
 * Time: 08:00
 */
public interface SearchQueryFactory<T> {

    /**
     * Get the combined from query chain query.
     * The current query will be last in this chain.
     * Chain will be truncated if <code>currentQuery</code> already present in
     * list
     *
     * @param navigationContext navigation context.
     * @param param        additional filter parameter
     * @param value        additional filter value(s)
     * @return combined from chain query
     */
    NavigationContext<T> getProductSnowBallQuery(NavigationContext<T> navigationContext, String param, Object value);

    /**
     * Get the combined from query chain query.
     * The current query will be last in this chain.
     * Chain will be truncated if <code>currentQuery</code> already present in
     * list
     *
     * @param navigationContext navigation context.
     * @param products          products for which to create sku navigation query
     * @return combined from chain query
     */
    NavigationContext<T> getSkuSnowBallQuery(NavigationContext<T> navigationContext, List<ProductSearchResultDTO> products);

    /**
     * Get the queries chain.
     * <p/>
     * Note about search: Search can be performed on entire shop or in particular category.
     * Entire search if size of categories is one and first category is 0
     * otherwise need to use all sub categories, that belong to given category list.
     *
     * @param shopId                the current shop id
     * @param customerShopId        the current customer shop id
     * @param categories            given category ids
     * @param searchSubCategories   if categories are specified this flag determines if this search should include all sub categories as well
     * @param requestParameters     web request parameters
     * @return ordered by cookie name list of cookies
     */
    NavigationContext<T> getFilteredNavigationQueryChain(long shopId, final long customerShopId, List<Long> categories, boolean searchSubCategories, Map<String, List> requestParameters);

}
