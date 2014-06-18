//
//  ========================================================================
//  Copyright (c) 1995-2014 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.http;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jetty.util.BufferUtil;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class HttpFieldTest
{

    @Test
    public void testContainsSimple() throws Exception
    {
        HttpField field = new HttpField("name","somevalue");
        assertTrue(field.contains("somevalue"));
        assertFalse(field.contains("other"));
        assertFalse(field.contains("some"));
        assertFalse(field.contains("value"));
        assertFalse(field.contains("v"));
        assertFalse(field.contains(""));
        assertFalse(field.contains(null));
    }
    
    @Test
    public void testContainsList() throws Exception
    {
        HttpField field = new HttpField("name",",aaa,bbb,ccc, ddd , e e, \"\\\"f,f\\\"\", ");
        assertTrue(field.contains("aaa"));
        assertTrue(field.contains("bbb"));
        assertTrue(field.contains("ccc"));
        assertTrue(field.contains("ddd"));
        assertTrue(field.contains("e e"));
        assertTrue(field.contains("\"f,f\""));
        assertFalse(field.contains(""));
        assertFalse(field.contains("aa"));
        assertFalse(field.contains("bb"));
        assertFalse(field.contains("cc"));
        assertFalse(field.contains(null));
    }
    
    
    
    
    
    @Test
    public void testValues()
    {
        String[] values = new HttpField("name","value").getValues();
        assertEquals(1,values.length);
        assertEquals("value",values[0]);
        

        values = new HttpField("name","a,b,c").getValues();
        assertEquals(3,values.length);
        assertEquals("a",values[0]);
        assertEquals("b",values[1]);
        assertEquals("c",values[2]);

        values = new HttpField("name","a,\"x,y,z\",c").getValues();
        assertEquals(3,values.length);
        assertEquals("a",values[0]);
        assertEquals("x,y,z",values[1]);
        assertEquals("c",values[2]);
        
        values = new HttpField("name","a,\"x,\\\"p,q\\\",z\",c").getValues();
        assertEquals(3,values.length);
        assertEquals("a",values[0]);
        assertEquals("x,\"p,q\",z",values[1]);
        assertEquals("c",values[2]);
        
    }
}