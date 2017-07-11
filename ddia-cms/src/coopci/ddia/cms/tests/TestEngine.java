package coopci.ddia.cms.tests;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.bson.Document;
import org.junit.Test;

import coopci.ddia.results.DictResult;
import coopci.ddia.cms.Engine;

public class TestEngine extends Engine {
	
	
	
	@Test
	public void testCreateItemNullContent() throws Exception {
		
		Engine engine = new Engine();
		engine.init();
		Long uid = 26L;
		DictResult result = engine.createItem(uid, null);
		
		assertEquals(200, result.code);
		assertEquals((Long)result.data.get(Engine.FIELD_NAME_OWNER_ID), uid);
		
	}
	

	
	@Test
	public void testCreateItem() throws Exception {
		
		Engine engine = new Engine();
		engine.init();
		Long uid = 26L;
		
		HashMap<String, Object> content = new HashMap<String, Object>();
		String randomValue = UUID.randomUUID().toString();
		content.put("random", randomValue);
		
		DictResult result = engine.createItem(uid, content);
		
		assertEquals(200, result.code);
		assertEquals((Long)result.data.get(Engine.FIELD_NAME_OWNER_ID), uid);
		assertEquals((String)result.data.get("random"), randomValue);
	}
	
	@Test
	public void testSaveItem() throws Exception {
		
		Engine engine = new Engine();
		engine.init();
		Long uid = 26L;
		
		Document item = engine.getOneItem(uid);
		
		long longvalue = 0;
		if (item.containsKey("long1")) {
			longvalue = item.getLong("long1");
		}
		String item_id = item.getObjectId("_id").toHexString();
		
		HashMap<String, Object> content = new HashMap<String, Object>();
		
		
		
		String randomValue = UUID.randomUUID().toString();
		content.put("set__random", randomValue);
		
		content.put("incrby__long1", "1");
		
				
		DictResult saveResult = engine.saveItem(uid, item_id, content);
			
		
		Document doc = engine.getItem(item_id);
		
		
		assertEquals(200, saveResult.code);
		
		
		assertEquals(randomValue, doc.getString("random"));	
		assertEquals(longvalue + 1L, doc.getLong("long1").longValue());
	}
	
	

	@Test
	public void testGetItem() throws Exception {
		
		Engine engine = new Engine();
		engine.init();
		Long uid = 26L;
		
		Document item = engine.getOneItem(uid);
		
		
		HashSet<String> fields = new HashSet<String>(); 
		for (String k : item.keySet()) {
			fields.add(k);
		}
		String item_id = item.getObjectId("_id").toHexString();
		
		
		
		DictResult getRes = engine.getItem(uid, item_id, fields);
		
		assertEquals(200, getRes.code);
		
		
		assertEquals(getRes.data.get("random"), item.getString("random"));	
		assertEquals(getRes.data.get("long1"), item.getLong("long1"));
	}
	
	
	
	@Test
	public void testAppendToContainer() throws Exception {
		
		Engine engine = new Engine();
		engine.init();
		Long uid = 26L;
		DictResult result;
		
		result = engine.createItem(uid, null);
		String container_id = (String) result.data.get("id");
		
		
		
		result = engine.createItem(uid, null);
		String member_id = (String) result.data.get("id");
		
		
		long order = -1;
		engine.setContainer(uid, member_id, container_id, order);
		
		
		
		result = engine.createItem(uid, null);
		String member2_id = (String) result.data.get("id");
		engine.setContainer(uid, member2_id, container_id, order);
		
		
		
		
		System.out.println("container_id: " + container_id);
		System.out.println("member_id: " + member_id);
		System.out.println("member2_id: " + member2_id);
		
	}
	
	
	
	

	
	// 测试插到中间的位置。
	@Test
	public void testInsertToContainer() throws Exception {
		long order = -1;
		Engine engine = new Engine();
		engine.init();
		Long uid = 26L;
		DictResult result;
		
		result = engine.createItem(uid, null);
		String container_id = (String) result.data.get("id");
		
		
		
		 
		result = engine.createItem(uid, null);
		String member_id = (String) result.data.get("id");
		engine.setContainer(uid, member_id, container_id, order);
		
		
		
		result = engine.createItem(uid, null);
		String member2_id = (String) result.data.get("id");
		engine.setContainer(uid, member2_id, container_id, order);
		

		result = engine.createItem(uid, null);
		String member3_id = (String) result.data.get("id");
		engine.setContainer(uid, member3_id, container_id, 1L);
		
		
		
		System.out.println("container_id: " + container_id);
		System.out.println("member_id: " + member_id);
		System.out.println("member2_id: " + member2_id);
		System.out.println("member3_id: " + member3_id);
		
	}
	
	
	
}
