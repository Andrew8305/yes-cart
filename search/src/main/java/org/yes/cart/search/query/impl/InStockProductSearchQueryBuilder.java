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

package org.yes.cart.search.query.impl;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.search.Query;
import org.yes.cart.search.query.ProductSearchQueryBuilder;

/**
 * User: denispavlov
 * Date: 17/07/2017
 * Time: 23:16
 */
public class InStockProductSearchQueryBuilder extends AbstractSearchQueryBuilderImpl implements ProductSearchQueryBuilder {

    /**
     * {@inheritDoc}
     */
    public Query createStrictQuery(final long shopId, final long customerShopId, final String parameter, final Object value) {
        return createNumericQuery(PRODUCT_SHOP_INSTOCK_FLAG_FIELD + ("0".equals(value) ? "0" : "1"), customerShopId);
    }

    /**
     * {@inheritDoc}
     */
    public Query createRelaxedQuery(final long shopId, final long customerShopId, final String parameter, final Object value) {
        return createStrictQuery(shopId, customerShopId, parameter, value);
    }
}
