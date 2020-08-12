package com.JCatan;

import junit.framework.TestCase;

public class BankTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testBank() {
        Bank bank = new Bank();
        boolean didThrow = false;
        for (int i = 0 ; i < Bank.NUM_OF_EACH_RESOURCE_CARD + 1; i++) {
            try {
                bank.takeResourceCard(ResourceType.BRICK);
            } catch(InsufficientResourceCardException e) {
                didThrow = true;
            }
        }
        
        assertEquals(0, bank.getNumberOfResourceCardsRemaining(ResourceType.BRICK));
        assertTrue("Should have thrown insufficient card exception", didThrow);
    }
    
    public void testBank2() {
        Bank bank = new Bank();
        boolean didThrow = false;
        for (int i = 0 ; i < Bank.NUM_OF_EACH_RESOURCE_CARD; i++) {
            try {
                bank.takeResourceCard(ResourceType.BRICK);
            } catch(InsufficientResourceCardException e) {
                didThrow = true;
            }
        }
        
        bank.giveResourceCard(new ResourceCard(ResourceType.BRICK));
        assertEquals(1, bank.getNumberOfResourceCardsRemaining(ResourceType.BRICK));
        try {
            bank.takeResourceCard(ResourceType.BRICK);
        } catch(InsufficientResourceCardException e) {
            didThrow = true;
        }
        assertEquals(0, bank.getNumberOfResourceCardsRemaining(ResourceType.BRICK));
        assertTrue("Should not throw  insufficient card exception", !didThrow);
    }
    
    public void testBank3() {
        Bank bank = new Bank();
        
        assertEquals(25, bank.getNumberOfDevelopmentCards());
        boolean didThrow = false;
        for (int i = 0 ; i < 25 + 1; i++) {
            try {
                bank.takeDevelopmentCard();
            } catch(OutOfDevelopmentCardsException e) {
                didThrow = true;
            }
        }
        
        assertTrue("Should have thrown out of dev cards exception", didThrow);
    }
 
}
