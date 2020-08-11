package com.JCatan;

import junit.framework.TestCase;

public class ResourceCardTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void test() {
    	ResourceCard rc = new ResourceCard(ResourceType.BRICK);
    	assertEquals(ResourceType.BRICK, rc.getResourceType());
    }
    
    

}
