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

import org.apache.lucene.search.Query;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: denispavlov
 * Date: 16/11/2014
 * Time: 19:03
 */
public class ProductIdSearchQueryBuilderTest {

    @Test
    public void testCreateStrictQueryNull() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createStrictQuery(10L, 1010L, "productId", null);
        assertNull(query);

    }

    @Test
    public void testCreateStrictQueryBlank() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createStrictQuery(10L, 1010L, "productId", "  ");
        assertNull(query);

    }

    @Test
    public void testCreateStrictQuerySingle() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createStrictQuery(10L, 1010L, "productId", "1");
        assertNotNull(query);
        assertEquals("(productId:1)^1.0", query.toString());

    }

    @Test
    public void testCreateStrictQueryMultiCollection() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createStrictQuery(10L, 1010L, "productId", Arrays.asList("1", "2", "3"));
        assertNotNull(query);
        assertEquals("(productId:1)^1.0 (productId:2)^1.0 (productId:3)^1.0", query.toString());

    }

    @Test
    public void testCreateStrictQueryMultiCollectionEmpty() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createStrictQuery(10L, 1010L, "productId", Collections.emptyList());
        assertNull(query);

    }

    @Test
    public void testCreateRelaxedQueryNull() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "productId", null);
        assertNull(query);

    }

    @Test
    public void testCreateRelaxedQueryBlank() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "productId", "  ");
        assertNull(query);

    }

    @Test
    public void testCreateRelaxedQuerySingle() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "productId", "1");
        assertNotNull(query);
        assertEquals("(productId:1)^1.0", query.toString());


    }

    @Test
    public void testCreateRelaxedQueryMultiCollection() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "productId", Arrays.asList("1", "2", "3"));
        assertNotNull(query);
        assertEquals("(productId:1)^1.0 (productId:2)^1.0 (productId:3)^1.0", query.toString());

    }

    @Test
    public void testCreateRelaxedQueryMultiCollectionEmpty() throws Exception {

        final Query query = new ProductIdSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "productId", Collections.emptyList());
        assertNull(query);

    }



}
