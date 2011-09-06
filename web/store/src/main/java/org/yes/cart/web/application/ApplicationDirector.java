package org.yes.cart.web.application;

import com.google.common.collect.MapMaker;
import org.yes.cart.domain.entity.Shop;
import org.yes.cart.service.domain.ShopService;
import org.yes.cart.shoppingcart.ShoppingCart;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Storefornt director class responsible for data caching,
 * common used operations, etc.
 * <p/>
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 6/15/11
 * Time: 7:11 PM
 */

public class ApplicationDirector {

    private ShopService shopService;

    private static ThreadLocal<Shop> shopThreadLocal =  new ThreadLocal<Shop>();
    private static ThreadLocal<ShoppingCart> shoppingCartThreadLocal =  new ThreadLocal<ShoppingCart>();


    private final ConcurrentMap<String, Shop> urlShopCache;
    private final ConcurrentMap<Long, Shop> idShopCache;

    /**
     * Construct application director.
     *
     * @param shopService shop service to use.
     */
    public ApplicationDirector(final ShopService shopService) {
        this.shopService = shopService;
        urlShopCache = new MapMaker().concurrencyLevel(16).softValues().expiration(3, TimeUnit.MINUTES).makeMap();
        idShopCache = new MapMaker().concurrencyLevel(16).softValues().expiration(3, TimeUnit.MINUTES).makeMap();
    }

    private ConcurrentMap<String, Shop> getUrlShopCache() {
        return urlShopCache;
    }


    private ConcurrentMap<Long, Shop> getIdShopCache() {
        return idShopCache;
    }

    /**
     * Get {@link Shop} from cache by his id.
     *
     * @param shopId given shop id
     * @return {@link Shop}
     */
    public Shop getShopById(final Long shopId) {
        Shop shop = getIdShopCache().get(shopId);
        if (shop == null) {
            shop = shopService.findById(shopId);
            if (shop != null) {
                getIdShopCache().put(shopId, shop);
            }
        }
        return shop;
    }


    /**
     * Get {@link Shop} from cache by given domain address.
     *
     * @param serverDomainName given given domain address.
     * @return {@link Shop}
     */
    public Shop getShopByDomainName(final String serverDomainName) {
        Shop shop = getUrlShopCache().get(serverDomainName);
        if (shop == null) {
            shop = shopService.getShopByDomainName(serverDomainName);
            if (shop != null) {
                getUrlShopCache().put(serverDomainName, shop);
                getIdShopCache().put(shop.getId(), shop);
            }
        }
        return shop;
    }

    /**
     * Get current shop from local thread.
     * @return {@link Shop} instance
     */
    public static Shop getCurrentShop() {
        return shopThreadLocal.get();
    }

    /**
     * Set {@link Shop} instance to current thread.
     * @param currentShop current shop.
     */
    public static void setCurrentShop(final Shop currentShop) {
        shopThreadLocal.set(currentShop);
    }

    /**
     * Get shopping cart from local thread storage.
     * @return {@link ShoppingCart}
     */
    public static ShoppingCart getShoppingCart() {
        return shoppingCartThreadLocal.get();
    }

    /**
     * Set shopping cart to storage.
     * @param shoppingCart  current cart.
     */
    public static void setShoppingCart(final ShoppingCart shoppingCart) {
        shoppingCartThreadLocal.set(shoppingCart);
    }
}