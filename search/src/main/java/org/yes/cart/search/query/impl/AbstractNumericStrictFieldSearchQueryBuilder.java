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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.yes.cart.search.query.ProductSearchQueryBuilder;

import java.util.Collection;

/**
 * User: denispavlov
 * Date: 15/11/2014
 * Time: 23:16
 */
public abstract class AbstractNumericStrictFieldSearchQueryBuilder extends AbstractSearchQueryBuilderImpl implements ProductSearchQueryBuilder {

    private final String field;
    private final float boost;

    protected AbstractNumericStrictFieldSearchQueryBuilder(final String field) {
        this.field = field;
        this.boost = 1f;
    }

    protected AbstractNumericStrictFieldSearchQueryBuilder(final String field, final float boost) {
        this.field = field;
        this.boost = boost;
    }

    /**
     * {@inheritDoc}
     */
    public Query createStrictQuery(final long shopId, final long customerShopId, final String parameter, final Object value) {

        if (value instanceof Collection) {
            final Collection singleValues = (Collection) value;
            if (singleValues.size() > 1) {

                final BooleanQuery.Builder aggregatedQuery = new BooleanQuery.Builder();

                for (final Object singleValue : singleValues) {
                    final long number = toNumber(singleValue);
                    aggregatedQuery.add(createNumericQuery(field, number, boost), BooleanClause.Occur.SHOULD);
                }
                return aggregatedQuery.build();

            } else if (singleValues.size() == 1) {
                final long number = toNumber(singleValues.iterator().next());
                return createNumericQuery(field, number, boost);
            } else {
                return null;
            }

        }

        if (isEmptyValue(value)) {
            return null;
        }

        final long number = toNumber(value);
        return createNumericQuery(field, number, boost);
    }

    private Long toNumber(final Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return NumberUtils.toLong(String.valueOf(value));
    }

    /**
     * {@inheritDoc}
     */
    public Query createRelaxedQuery(final long shopId, final long customerShopId, final String parameter, final Object value) {

        return createStrictQuery(shopId, customerShopId, parameter, value);

    }
}
