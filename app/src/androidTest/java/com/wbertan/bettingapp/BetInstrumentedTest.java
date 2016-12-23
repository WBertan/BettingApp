package com.wbertan.bettingapp;

import android.support.test.runner.AndroidJUnit4;

import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.model.Bet;
import com.wbertan.bettingapp.repository.RepositoryBet;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

/**
 * Created by william.bertan on 22/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public class BetInstrumentedTest {
//    Context appContext = InstrumentationRegistry.getTargetContext();
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private List<Bet> listBet;

    @Test
    public void testRepository() throws Exception {
        RepositoryBet.getInstance().addCallback(new ICallback<Bet>() {
            @Override
            public void onSuccess(int aRequestCode, Bet aObject) {
                fail("Error trying to get the bets from repository. Stucked in onSuccess for single object.");
                countDownLatch.countDown();
            }
            @Override
            public void onSuccess(int aRequestCode, List<Bet> aObject) {
                listBet = aObject;
                countDownLatch.countDown();
            }
            @Override
            public void onError(int aRequestCode, CallbackError aCallbackError) {
                fail("Error trying to get the bets from repository. " + aCallbackError.getMessage());
                countDownLatch.countDown();
            }
        });
        RepositoryBet.getInstance().load(10);

        countDownLatch.await(20000, TimeUnit.MILLISECONDS);

        assertNotNull(listBet);
        assertEquals(6, listBet.size());
    }

    /*
      Tried to write something with Mockito, but it wasn't possible, the Controller and Repository are Singleton,
        and Mockito don't allow Mock Singletons.
     */
}
