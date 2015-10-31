/**
 * 
 */
package org.springframework.data.aerospike.convert;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.aerospike.core.AerospikeTemplate;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value.StringValue;

/**
 *
 *
 * @author Peter Milne
 * @author Jean Mercier
 *
 */
public class AerospikeDataTest {

	/**
	 * 
	 */
	private static final String AEROSPIKE_KEY = "AerospikeKey";
	/**
	 * 
	 */
	private static final long AEROSPIKE_KEY_LONG = 100L;
	/**
	 * 
	 */
	private static final String AEROSPIKE_SET_NAME = "AerospikeSetName";
	/**
	 * 
	 */
	private static final String AEROSPIKE_NAME_SPACE = "AerospikeNameSpace";
	@Mock AerospikeTemplate mockTtemplate;
	@Mock AerospikeClient mockClient;
	@Mock AerospikeData mockAerospikeData;
	
	
	//@Mock Key key;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#forRead(com.aerospike.client.Key, java.lang.String[])}.
	 */
	@Test
	public void testForRead() {
		
		Key key = new Key(AerospikeDataTest.AEROSPIKE_NAME_SPACE, AerospikeDataTest.AEROSPIKE_SET_NAME, AerospikeDataTest.AEROSPIKE_KEY);
		AerospikeData data = AerospikeData.forRead(key, null);
		assertEquals(key,  data.getKey());
		assertNull(data.getRecord());
		assertEquals(AerospikeDataTest.AEROSPIKE_NAME_SPACE, data.getNamespace());
		assertEquals(Collections.<Bin>emptyList(), data.getBins());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#forWrite(java.lang.String)}.
	 */
	@Test
	public void testForWrite() {
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		assertNull(data.getKey());
		assertNull(data.getRecord());
		assertEquals(AerospikeDataTest.AEROSPIKE_NAME_SPACE, data.getNamespace());
		assertEquals( new ArrayList<Bin>(), data.getBins());
		
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getKey()}.
	 */
	@Test
	public void testGetKey() {
		Key key = new Key(AerospikeDataTest.AEROSPIKE_NAME_SPACE, AerospikeDataTest.AEROSPIKE_SET_NAME, AerospikeDataTest.AEROSPIKE_KEY);
		AerospikeData data = AerospikeData.forRead(key, null);
		assertEquals(key,  data.getKey());
		data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		assertNull(data.getKey());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setID(byte[])}.
	 */
	@Test
	public void testSetIDByteArray() {
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setID(AEROSPIKE_KEY.getBytes());
		Key key = new Key(data.getNamespace(), data.getSetName(), AEROSPIKE_KEY.getBytes());
		assertEquals(key, data.getKey());
		
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setID(java.lang.String)}.
	 */
	@Test
	public void testSetIDString() {
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setID(AEROSPIKE_KEY);
		Key key = new Key(data.getNamespace(), data.getSetName(), AEROSPIKE_KEY);
		assertEquals(key, data.getKey());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setID(long)}.
	 */
	@Test
	public void testSetIDLong() {
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setID(AEROSPIKE_KEY_LONG);
		Key key = new Key(data.getNamespace(), data.getSetName(), AEROSPIKE_KEY_LONG);
		assertEquals(key, data.getKey());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setID(int)}.
	 */
	@Test
	public void testSetIDInt() {
		int ID = 100;
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setID(ID);
		Key key = new Key(data.getNamespace(), data.getSetName(), ID);
		assertEquals(key, data.getKey());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setID(com.aerospike.client.Value)}.
	 */
	@Test
	public void testSetIDValue() {
		StringValue ID = new StringValue(AEROSPIKE_KEY);
		
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setID(ID);
		Key key = new Key(data.getNamespace(), data.getSetName(), ID);
		assertEquals(key, data.getKey());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setID(java.io.Serializable)}.
	 */
	@Test
	public void testSetIDSerializable() {
		Serializable ID = AEROSPIKE_KEY;
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setID(ID);
		Key key = new Key(data.getNamespace(), data.getSetName(), (String) ID);
		assertEquals(key, data.getKey());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getRecord()}.
	 */
	@Test
	public void testGetRecord() {
		
		int expiration = 200;
		int generation = 200;

		Map<String, Object> bins = new HashMap<String, Object>() {
			{
				put("lastname", "Watney");
				put("firstname", "Mark");
			}
		};
		Record record = new Record(bins, generation, expiration);

		when(mockAerospikeData.getRecord()).thenReturn(record);
		
		assertEquals(record, mockAerospikeData.getRecord());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getNamespace()}.
	 */
	@Test
	public void testGetNamespace() {
		when(mockAerospikeData.getNamespace()).thenReturn(AEROSPIKE_NAME_SPACE);
		assertEquals(AEROSPIKE_NAME_SPACE, mockAerospikeData.getNamespace());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getSetName()}.
	 */
	@Test
	public void testGetSetName() {
		when(mockAerospikeData.getSetName()).thenReturn(AEROSPIKE_SET_NAME);
		assertEquals(AEROSPIKE_SET_NAME, mockAerospikeData.getSetName());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getBins()}.
	 */
	@Test
	public void testGetBins() {
		
		List<Bin> bins = new ArrayList<Bin>(){{
			add(new Bin("lastname", "Weiver"));
			add(new Bin("firstname", "Sigourney "));
			add(new Bin("profession", "Actor"));
		}};
		
		when(mockAerospikeData.getBins()).thenReturn(bins);
		assertEquals(bins, mockAerospikeData.getBins());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getBinsAsArray()}.
	 */
	@Test
	public void testGetBinsAsArray() {
		List<Bin> bins = new ArrayList<Bin>(){{
			add(new Bin("lastname", "Weiver"));
			add(new Bin("firstname", "Sigourney "));
			add(new Bin("profession", "Actor"));
		}};
		
		when(mockAerospikeData.getBinsAsArray()).thenReturn(bins.toArray(new Bin[bins.size()]));
		
		assertArrayEquals(bins.toArray(new Bin[bins.size()]), mockAerospikeData.getBinsAsArray());
		
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#add(java.util.List)}.
	 */
	@Test
	public void testAddListOfBin() {
		
		List<Bin> bins = new ArrayList<Bin>(){{
			add(new Bin("lastname", "Weiver"));
			add(new Bin("firstname", "Sigourney "));
			add(new Bin("profession", "Actor"));
		}};

		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.add(bins);
	
		assertEquals(bins, data.getBins());


	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#add(com.aerospike.client.Bin)}.
	 */
	@Test
	public void testAddBin() {
		List<Bin> bins = new ArrayList<Bin>(){{
			add(new Bin("lastname", "Weiver"));
			add(new Bin("firstname", "Sigourney "));
			add(new Bin("profession", "Actor"));
		}};

		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		for (Iterator iterator = bins.iterator(); iterator.hasNext();) {
			Bin bin = (Bin) iterator.next();
			data.add(bin);			
		}
		
		assertEquals(bins, data.getBins());

	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getBinNames()}.
	 */
	@Test
	public void testGetBinNames() {
		int i = 0;
		List<Bin> bins = new ArrayList<Bin>(){{
			add(new Bin("lastname", "Weiver"));
			add(new Bin("firstname", "Sigourney "));
			add(new Bin("profession", "Actor"));
		}};
		String[] binNames =  new String[bins.size()];

		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		for (Iterator iterator = bins.iterator(); iterator.hasNext();) {
			Bin bin = (Bin) iterator.next();
			data.add(bin);	
			binNames[i++]= bin.name;
		}
		assertNotNull(binNames);
		assertArrayEquals(binNames, data.getBinNames());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setRecord(com.aerospike.client.Record)}.
	 */
	@Test
	public void testSetRecord() {
		int expiration = 200;
		int generation = 200;

		Map<String, Object> bins = new HashMap<String, Object>() {
			{
				put("lastname", "Watney");
				put("firstname", "Mark");
			}
		};
		Record record = new Record(bins, generation, expiration);
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setRecord(record);
		assertEquals(record, data.getRecord());
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#setSetName(java.lang.String)}.
	 */
	@Test
	public void testSetSetName() {
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		Serializable ID = AEROSPIKE_KEY;
		data.setID(AEROSPIKE_KEY);		
		data.setSetName(AEROSPIKE_SET_NAME);
		assertEquals(AEROSPIKE_SET_NAME, data.getKey().setName);
		assertEquals(AEROSPIKE_KEY, data.getKey().userKey.toString());
		assertEquals(AEROSPIKE_NAME_SPACE, data.getKey().namespace);
	}

	/**
	 * Test method for {@link org.springframework.data.aerospike.convert.AerospikeData#getSpringId()}.
	 */
	@Test
	public void testGetSpringId() {
		int expiration = 200;
		int generation = 200;

		Map<String, Object> bins = new HashMap<String, Object>() {
			{
				put("lastname", "Watney");
				put("firstname", "Mark");
				put(AerospikeData.SPRING_ID_BIN, "10000");
			}
		};
		Record record = new Record(bins, generation, expiration);
		AerospikeData data = AerospikeData.forWrite(AEROSPIKE_NAME_SPACE);
		data.setRecord(record);
		assertEquals("10000", data.getRecord().getValue(AerospikeData.SPRING_ID_BIN));
	}

}
