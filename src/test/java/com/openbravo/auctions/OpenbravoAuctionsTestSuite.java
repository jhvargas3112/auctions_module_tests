package com.openbravo.auctions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EnglishAuctionCelebrationTest.class, DutchAuctionCelebrationTest.class,
    JapaneseAuctionCelebrationTest.class })
public class OpenbravoAuctionsTestSuite {

}
