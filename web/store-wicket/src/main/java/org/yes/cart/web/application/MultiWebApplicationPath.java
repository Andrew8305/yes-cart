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

package org.yes.cart.web.application;

import org.apache.wicket.util.file.IResourcePath;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.UrlResourceStream;
import org.apache.wicket.util.string.StringList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 7/10/11
 * Time: 9:13 AM
 */
public class MultiWebApplicationPath   implements IResourcePath {

    private static final Logger LOG = LoggerFactory.getLogger(MultiWebApplicationPath.class);

    /** The list of urls in the path */
    private final List<String> webappPaths = new ArrayList<String>();

    /** The web apps servlet context */
    private final ServletContext servletContext;

    /**
     * Constructor
     *
     * @param servletContext
     *            The webapplication context where the resources must be loaded from
     */
    public MultiWebApplicationPath(final ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }

    /**
     * @param path
     *            add a path that is lookup through the servlet context
     */
    public void add(String path)
    {
        if (!path.startsWith("/"))
        {
            path = "/" + path;
        }
        if (!path.endsWith("/"))
        {
            path += "/";
        }
        webappPaths.add(path);
    }

    private static final String RESOURCE_EXTENSION = "properties.xml";

    /**
     *
     * @see org.apache.wicket.util.file.IResourceFinder#find(Class, String)
     */
    public IResourceStream find(final Class<?> clazz, final String pathname)
    {
        if (!webappPaths.isEmpty()) {

            for (final String path : webappPaths) {
                try {
                    final URL url = servletContext.getResource(path + pathname);
                    if (url != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Retrieving web resource: {}{}", path, pathname);
                        }

                        return new UrlResourceStream(url);
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Lookup resource: {}{}", path, pathname);
                    }
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("File couldn't be found: {}{}", path, pathname);
                    }
                }
            }
            if (pathname.endsWith(RESOURCE_EXTENSION)) {

                for (final String path : webappPaths) {
                    final String resourceName = getShopResourceFile(pathname);
                    try {
                        final URL url = servletContext.getResource(path + resourceName);
                        if (url != null) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Retrieving web resource: {}{}", path, resourceName);
                            }

                            return new UrlResourceStream(url);
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Lookup resource: {}{}", path, resourceName);
                        }
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("File couldn't be found: {}{}", path, resourceName);
                        }
                    }
                }
            }
        }

        return null;
    }

    /*
     *  Master resource file should be located in /markup directory of theme and should be named shop[_locale].properties.xml
     *  E.g. shop_en.properties.xml for English.
     */
    String getShopResourceFile(final String pathname) {
        int filenamePos = pathname.lastIndexOf('/');
        if (filenamePos == -1) {
            filenamePos = 0;
        }
        int localePos = pathname.substring(filenamePos).indexOf('_');
        if (localePos == -1) {
            localePos = pathname.length() - RESOURCE_EXTENSION.length() - 1;
        } else {
            localePos += filenamePos;
        }
        return "shop" + pathname.substring(localePos);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()  {
        return "[theme chain = " + StringList.valueOf(webappPaths) + "]";
    }

}