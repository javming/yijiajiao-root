package com.yijiajiao.root.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 操作redis工具类，dbNum为数据库编号，默认使用0库
 */
public class RedisUtil {
	private static final Logger log =LoggerFactory.getLogger(RedisUtil.class);
	private static JedisPool pool;
    /**
     * 初始化Redis连接池
     */
    private static void initJedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		// 最大空闲连接数, 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(Config.getInt("redis.maxIdle"));
		//最大连接数
		config.setMaxTotal(Config.getInt("redis.maxTotal"));
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
		config.setTestOnBorrow(true);
		pool = new JedisPool(config, Config.getString("redis.ip"),Config.getInt("redis.port"));
		log.info("jedis-pool init success!");
    }

	/**
	 * 获取jedis
	 */
    public static Jedis getJedis(){
		if(pool == null){
			initJedisPool();
		}
		return pool.getResource();
	}
    /** 
     * 返还到连接池
     */  
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }  
    }

	/**
	 * 回收broken的jedis
	 */
	public static void returnBrokenResource(Jedis jedis){
		if (jedis != null) {
			pool.returnBrokenResource(jedis);
		}
	}

    /**
     *  缓存token 加有效时间expire
	 */
	public static void putRedis(String openId, String token, int expire){
        Jedis jedis = null;
		try {
			log.info("key="+openId+",value="+token);
			jedis = getJedis();
			jedis.select(0);
			jedis.setex(openId, expire, token);
			Long ttl = jedis.ttl(openId);
			log.info("token缓存成功！剩余时间="+ttl+"秒");
		} catch (Exception e) {
			returnBrokenResource(jedis);
			e.printStackTrace();
		}finally{
			returnResource(jedis);
		}
	}
//********************************操作字符串***************************************
	/**
	 * 根据key取值
	 * @param dbNum 数据库编号
	 */
	public static String getValue(String key, int dbNum){
		Jedis jedis = null;
		if ("".equals(key) || key == null) {
			return null;
		}
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.get(key);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}

	/**
	 * 根据key获取值 默认0库
	 */
	public static String getValue(String key) {
		return getValue(key,0);
	}

	public static byte[] get(byte[] key,int dbNmu){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNmu);
			return jedis.get(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static byte[] get(byte[] key){
		return get(key,0);
	}
	/**
	 * 添加记录,如果记录已存在将覆盖原有的value
	 * @return 状态码
	 * */
	public static String set(String key, String value,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.set(key,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String set(String key, String value){
		return set(key,value,0);
	}
	public static String set(byte[] key, byte[] value,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.set(key,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String set(byte[] key,byte[] value){
		return set(key,value,0);
	}
	/**
	 * 添加有过期时间的记录
	 * @param seconds 过期时间，以秒为单位
	 */
	public static String setEx(String key, int seconds, String value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.setex(key,seconds,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String setEx(String key, int seconds, String value){
		return setEx(key,seconds,value,0);
	}
	public static String setEx(byte[] key, int seconds, byte[] value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.setex(key,seconds,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String setEx(byte[] key, int seconds, byte[] value){
		return setEx(key,seconds,value,0);
	}

	/**
	 * 添加一条记录，仅当给定的key不存在时才插入
	 * @return long 状态码，1插入成功且key不存在，0未插入，key存在
	 */
	public static long setNx(String key, String value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.setnx(key,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long setNx(String key, String value){
		return setNx(key,value,0);
	}

	/**
	 * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据
	 * 例:String str1="123456789";
	 * 对str1操作后setRange(key,4,0000)，str1="123400009";
	 * @param offset 起始位置
	 * @param  value
	 * @return long value的长度
	 * */
	public static long setRange(String key, long offset, String value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.setrange(key, offset, value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long setRange(String key, long offset, String value){
		return setRange(key,offset,value,0);
	}

	/**
	 * 在指定的key中追加value
	 * @return long 追加后value的长度
	 */
	public static long append(String key, String value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.append(key, value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long append(String key,String value){
		return append(key,value,0);
	}

	/**
	 * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
	 * @param number 要减去的值
	 * @return long 减指定值后的值
	 */
	public static long decrBy(String key, long number,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.decrBy(key, number);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long decrBy(String key, long number){
		return decrBy(key,number,0);
	}

	/**
	 * 可以作为获取唯一id的方法
	 * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
	 * @param number 要减去的值
	 * @return long 相加后的值
	 * */
	public static long incrBy(String key, long number,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.incrBy(key, number);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long incrBy(String key, long number){
		return incrBy(key,number,0);
	}

	/**
	 * 对指定key对应的value进行截取
	 * @param startOffset 开始位置(包含)
	 * @param endOffset 结束位置(包含)
	 * @return String 截取的值
	 * */
	public static String getRange(String key, long startOffset, long endOffset,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.getrange(key, startOffset, endOffset);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String getRange(String key, long startOffset, long endOffset){
		return getRange(key,startOffset,endOffset,0);
	}

	/**
	 * 批量存储记录
	 * @param keysvalues 例:keysvalues="key1","value1","key2","value2"(键值成对);
	 * @return String 状态码
	 * */
	public static String mset(int dbNum,String... keysvalues) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.mset(keysvalues);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String mset(String... keysvalues){
		return mset(0,keysvalues);
	}

	/**
	 * 获取key对应的值的长度
	 * @return value值得长度
	 * */
	public static long strlen(String key,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.strlen(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long strlen(String key){
		return strlen(key,0);
	}

	/**
	 * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
	 * @param  keys
	 * @return List<String> 值得集合
	 * */
	public static List<String> mget(int dbNum,String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.mget(keys);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static List<String> mget(String... keys){
		return mget(0,keys);
	}
//*********************************操作key**********************************
	/**
	 * 删除指定key 可以输入多个key
	 */
	public static long del(int dbNum,String... keys){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.del(keys);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	/**
	 * 删除指定key(指定库)
	 */
	public static long del(String... keys){
		return del(0,keys);
	}

	/**
	 * 为给定key设置生存时间。当key过期时，它会被自动删除
	 * @param seconds 有效时间（秒）
	 */
	public static long expire(String key, int seconds,int dbNum){
        Jedis jedis = null;
		try {
			jedis= getJedis();
			jedis.select(dbNum);
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long expire(String key, int expire){
		return expire(key,expire,0);
	}
	/**
	 *	查询key剩余时间
	 *@return 秒
	 */
	public static long ttl(String key,int dbNum){
        Jedis jedis = null;
		if("".equals(key) || key==null) return -1;
		try {
			jedis=getJedis();
			jedis.select(dbNum);
			return jedis.ttl(key);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long ttl(String key){
		return ttl(key,0);
	}
	/**
	 * 判断键是否存在，不存在返回false
	 */
	public static boolean exist(String key,int dbNum) {
		Jedis jedis = null;
		if ("".equals(key) || key == null) {
			return false;
		}
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.exists(key.getBytes());
		} catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static boolean exist(String key){
		return exist(key,0);
	}

	/**
	 * 修改键值
	 */
	public static String rename(String oldKey,String newKey,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.rename(oldKey,newKey);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String rename(String oldKey,String newKey){
		return rename(oldKey,newKey,0);
	}

	/**
	 * 返回指定key存储的类型
	 * @return String  string|list|set|zset|hash
	 */
	public static String type(String key,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.type(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String type(String key){
		return type(key,0);
	}

	/**
	 * 查找所有匹配给定的模式的键
	 * @param pattern key的表达式,*表示多个，？表示一个
	 */
	public static Set<String> keys(String pattern,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.keys(pattern);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static Set<String> keys(String pattern){
		return keys(pattern,0);
	}

//******************************操作 list**********************************************
	/**
	 * List长度
	 * @return 长度
	 * */
	public static long llen(String key,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.llen(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long llen(String key){
		return llen(key,0);
	}

	/**
	 * 覆盖操作,将覆盖List中指定位置的值
	 * @param  index 位置
	 * @return 状态码
	 * */
	public static String lset(String key, int index, String value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.lset(key, index, value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String lset(String key, int index, String value) {
		return lset(key,index,value);
	}

	/**
	 * 获取List中指定位置的值
	 * @param index 位置
	 * @return 值
	 */
	public static String lindex(String key, int index,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.lindex(key, index);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String lindex(String key, int index){
		return lindex(key,index,0);
	}

	/**
	 * 将List中左边第一条记录移出List
	 * @return 移出的记录
	 * */
	public static String lpop(String key,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.lpop(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String lpop(String key){
		return lpop(key,0);
	}

	/**
	 * 将List中右边第一条记录移出List
	 * @return 移出的记录
	 */
	public static String rpop(String key,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.rpop(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String rpop(String key){
		return rpop(key,0);
	}

	/**
	 * 向List左边追加记录  (lpush/rpush 的区别在于从哪边开始将数据压入堆栈)
	 * @return 记录总数
	 * */
	public static long lpush(String key, String value,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.lpush(key,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long lpush(String key, String value){
		return lpush(key,value,0);
	}

	/**
	 * 向list右边追加记录
	 * @return 记录总数
	 */
	public static long rpush(String key, String value,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.rpush(key,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long rpush(String key, String value){
		return rpush(key,value,0);
	}

	/**
	 * 获取指定范围的记录，可以做为分页使用
	 * @param start 起始位置
	 * @param end   结束位置 -1表示导出全部
	 * @return List
	 * */
	public List<String> lrange(String key, long start, long end,int dbNum) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.lrange(key, start, end);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public List<String> lrange(String key, long start, long end){
		return lrange(key,start,end,0);
	}

//*******************************操作set*********************************************

	/**
	 * set 中添加一条数据，重复的数据不添加
	 * @return
	 */
	public static Long sadd(String key, String value, int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.sadd(key,value);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static Long sadd(String key, String value){
		return sadd(key,value,0);
	}

	/**
	 * 获取set集合中的数据
	 */
	public static Set<String> smembers(String key,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.smembers(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static  Set<String> smembers(String key){
		return smembers(key,0);
	}

	/**
	 * 获取集合元素个数
	 */
	public static long scard(String key,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.scard(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static long scard(String key){
		return scard(key,0);
	}

	/**
	 * 从set中弹出信息
	 */
	public static String spop(String key,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.spop(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String spop(String key) {
		return spop(key, 0);
	}

//***************************操作map********************************************

	/**
	 * 添加map
	 */
	public static String hmset(String key, Map<String,String> map, int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.hmset(key,map);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static String hmset(String key, Map<String,String> map){
		return hmset(key,map,0);
	}

	/**
	 * 获取map中的参数 mapKeys可以为多个
	 * @param mapKeys
	 * @return
	 */
	public static List<String> hmget(int dbNum,String key,String... mapKeys){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.hmget(key,mapKeys);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static List<String> hmget(String key,String... mapKeys){
		return hmget(0,key,mapKeys);
	}

	/**
	 * 获取整个map的数据
	 * @param key
	 * @param dbNum
	 * @return
	 */
	public static Map<String,String> hgetAll(String key,int dbNum){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.hgetAll(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static Map<String,String> hgetAll(String key){
		return hgetAll(key,0);
	}

	/**
	 * 添加一条记录到map 存在就不添加
	 */
	public static Long hsetnx(int dbNum, String key, String mapKey, String mapValue){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.hsetnx(key,mapKey,mapValue);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static Long hsetnx(String key, String mapKey, String mapValue){
		return hsetnx(0,key,mapKey,mapValue);
	}

	/**
	 * 删除map中的指定项
	 */
	public static Long hdel(int dbNum, String key, String mapKey){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbNum);
			return jedis.hdel(key,mapKey);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw new RuntimeException(e);
		}finally{
			returnResource(jedis);
		}
	}
	public static Long hdel(String key, String mapKey){
		return hdel(0,key,mapKey);
	}


}
