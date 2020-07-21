package kr.codebrain.netty.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisController {
	
    private StringRedisTemplate template;
    
	public RedisController(StringRedisTemplate template) {
		super();
		this.template = template;
	}

	/*
	 * redis save
	 */
	public void setData(String key, String value){
		
		//Redis 에 저장
		ValueOperations<String, String> ops = this.template.opsForValue();
		ops.set(key, value);
		
	}
	
	/*
	 * redis delete
	 */
	public void delData(String key){
		
		this.template.delete(key);
		
	}
	
	/*
	 * redis getData(String key)
	 */
	public String getData(String key){
		
		return this.template.opsForValue().get(key);
		
	}
	
	/*
	 * redis keys *
	 */
	public Map<String, String> keysData(String key){
		
		ListOperations<String, String> listOps = this.template.opsForList();
		RedisOperations<String, String> redis = listOps.getOperations();
		  
		Set<String> keys = redis.keys(key);
		
		if(keys.size() > 0){

			Iterator<String> iter = keys.iterator();
			  
			Map<String, String> map = new HashMap<String,String>();
			  
			while(iter.hasNext()) {
				String key1 =  iter.next().toString();
				String value = this.getData(key1);
				
				map.put(key1, value);
				
			}
			
			return map;
		}
		
		return null;
		
	}
	
	
	/*
	 * 웹에서 제어 데이터를 입력받은 데이터 검색하여 마이크로닉으로 전달 
	 */
	public Map<String, String> requestControl(){
		String keys = "SM*";
		
		return this.keysData(keys);
		
	}
}
