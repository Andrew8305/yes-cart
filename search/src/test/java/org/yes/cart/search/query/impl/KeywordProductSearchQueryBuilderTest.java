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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: denispavlov
 * Date: 16/11/2014
 * Time: 17:51
 */
public class KeywordProductSearchQueryBuilderTest {

    @Test
    public void testCreateStrictQueryNull() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createStrictQuery(10L, 1010L, "query", null);
        assertNull(query);

    }

    @Test
    public void testCreateStrictQueryBlank() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createStrictQuery(10L, 1010L, "query", "   ");
        assertNull(query);

    }

    @Test
    public void testCreateStrictQuerySingle() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createStrictQuery(10L, 1010L, "query", "SearchWord");
        assertNotNull(query);
        assertEquals("((name:searchword~1)^4.0 (displayName:searchword~2)^4.0 (brand:searchword)^5.0 (categoryName:searchword~2)^4.5 (type:searchword~2)^4.5 (code:searchword)^10.0 (manufacturerCode:searchword)^10.0 (sku.code:searchword)^10.0 (sku.manufacturerCode:searchword)^10.0 (attribute.attrvalsearchprimary:searchword~2)^10.0 (attribute.attrvalsearchphrase:searchword~2)^3.5)", query.toString());

    }

    @Test
    public void testCreateStrictQueryMulti() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createStrictQuery(10L, 1010L, "query", "Search, Word");
        assertNotNull(query);
        assertEquals("((name:search, word~1)^4.0 (displayName:search, word~2)^4.0 (brand:search, word)^5.0 (categoryName:search, word~2)^4.5 (type:search, word~2)^4.5 (code:search, word)^10.0 (manufacturerCode:search, word)^10.0 (sku.code:search, word)^10.0 (sku.manufacturerCode:search, word)^10.0 (attribute.attrvalsearchprimary:search, word~2)^10.0 (attribute.attrvalsearchphrase:search, word~2)^3.5) (+((name:search~2)^3.0 (displayName:search~2)^3.0 (brand:search)^5.0 (categoryName_stem:search~2)^2.5 (code:search)^4.0 (manufacturerCode:search)^4.0 (sku.code:search)^4.0 (sku.manufacturerCode:search)^4.0 (attribute.attrvalsearchprimary:search~2)^4.0 (attribute.attrvalsearchphrase:search~2)^2.0) +((name:word~1)^3.0 (displayName:word~1)^3.0 (brand:word)^5.0 (categoryName_stem:word~1)^2.5 (code:word)^4.0 (manufacturerCode:word)^4.0 (sku.code:word)^4.0 (sku.manufacturerCode:word)^4.0 (attribute.attrvalsearchprimary:word~1)^4.0 (attribute.attrvalsearchphrase:word~1)^2.0))", query.toString());

    }


    @Test
    public void testCreateRelaxedQueryNull() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "query", null);
        assertNull(query);

    }

    @Test
    public void testCreateRelaxedQueryBlank() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "query", "   ");
        assertNull(query);

    }


    @Test
    public void testCreateRelaxedQuerySingle() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "query", "SearchWord");
        assertNotNull(query);
        assertEquals("((name:searchword~2)^3.0 (name_stem:searchword~2)^2.5 (displayName:searchword~2)^3.0 (displayName_stem:searchword~2)^2.5 (brand:searchword~2)^3.5 (categoryName:searchword~2)^3.5 (categoryName_stem:searchword~2)^2.0 (type:searchword~2)^3.5 (type_stem:searchword~2)^2.0 (code:searchword~2)^4.0 (manufacturerCode:searchword~2)^4.0 (sku.code:searchword~2)^4.0 (sku.manufacturerCode:searchword~2)^4.0 (code_stem:searchword~2)^1.0 (manufacturerCode_stem:searchword~2)^1.0 (sku.code_stem:searchword~2)^1.0 (sku.manufacturerCode_stem:searchword~2)^1.0 (attribute.attrvalsearchprimary:searchword~2)^2.75 (attribute.attrvalsearch:searchword~2)^2.75)", query.toString());

    }

    @Test
    public void testCreateRelaxedQueryMulti() throws Exception {

        final Query query = new KeywordProductSearchQueryBuilder().createRelaxedQuery(10L, 1010L, "query", "Search, Word");
        assertNotNull(query);
        assertEquals("((name:search~2)^3.0 (name_stem:search~2)^2.5 (displayName:search~2)^3.0 (displayName_stem:search~2)^2.5 (brand:search~2)^3.5 (categoryName:search~2)^3.5 (categoryName_stem:search~2)^2.0 (type:search~2)^3.5 (type_stem:search~2)^2.0 (code:search~2)^4.0 (manufacturerCode:search~2)^4.0 (sku.code:search~2)^4.0 (sku.manufacturerCode:search~2)^4.0 (code_stem:search~2)^1.0 (manufacturerCode_stem:search~2)^1.0 (sku.code_stem:search~2)^1.0 (sku.manufacturerCode_stem:search~2)^1.0 (attribute.attrvalsearchprimary:search~2)^2.75 (attribute.attrvalsearch:search~2)^2.75) ((name:word~1)^3.0 (name_stem:word~1)^2.5 (displayName:word~1)^3.0 (displayName_stem:word~1)^2.5 (brand:word~1)^3.5 (categoryName:word~1)^3.5 (categoryName_stem:word~1)^2.0 (type:word~1)^3.5 (type_stem:word~1)^2.0 (code:word~1)^4.0 (manufacturerCode:word~1)^4.0 (sku.code:word~1)^4.0 (sku.manufacturerCode:word~1)^4.0 (code_stem:word~1)^1.0 (manufacturerCode_stem:word~1)^1.0 (sku.code_stem:word~1)^1.0 (sku.manufacturerCode_stem:word~1)^1.0 (attribute.attrvalsearchprimary:word~1)^2.75 (attribute.attrvalsearch:word~1)^2.75)", query.toString());

    }
}
