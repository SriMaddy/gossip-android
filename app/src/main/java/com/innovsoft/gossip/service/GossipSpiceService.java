package com.innovsoft.gossip.service;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

/**
 * Created by SriMaddy on 3/27/2017.
 */

public class GossipSpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        InFileStringObjectPersister persister = new InFileStringObjectPersister(application);
        cacheManager.addPersister(persister);
        return cacheManager;
    }
}
