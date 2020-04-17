package com.yh.demo.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis的六种数据格式基本命令操作测试
 * RedisTemplate基础模板
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCommandsTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void StringCommands() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "stringKey".getBytes();
                byte[] valueBytes = "value".getBytes();
                long secondTimeout = 100l;//单位：秒
                long milliSecondTimeout = 100l;//单位：秒
                //key、value存储，类似Map
                connection.set(keyBytes, valueBytes);
                //参数：过期时间、先判断，Expiration，SetOption
                connection.set(keyBytes, valueBytes, Expiration.seconds(10), RedisStringCommands.SetOption.SET_IF_ABSENT);
                //参数：过期时间，单位秒，推荐用set方法+参数
                connection.setEx(keyBytes, secondTimeout, valueBytes);
                //只在键不存在才执行，等于RedisStringCommands.SetOption.SET_IF_ABSENT
                connection.setNX(keyBytes, valueBytes);
                //过期时间：毫秒单位
                connection.pSetEx(keyBytes, milliSecondTimeout, valueBytes);

                //设置过期时间
                connection.expire(keyBytes, secondTimeout);
                connection.pExpire(keyBytes, milliSecondTimeout);
                //取值，只限于字符串key的值
                connection.get(keyBytes);
                //先去旧值，并存储新值
                connection.getSet(keyBytes, valueBytes);

                //批量存取
                Map<byte[], byte[]> keyMap = new HashMap();
                keyMap.put(keyBytes, valueBytes);
                keyMap.put("key2".getBytes(), "value2".getBytes());
                connection.mSet(keyMap);
                connection.mSetNX(keyMap);//当且仅当所有key都不存在时才执行
                connection.mGet(keyBytes, "key2".getBytes());

                //获取key对应的value字符串长度
                connection.strLen(keyBytes);
                //key对应的value结尾追加新值
                connection.append(keyBytes, "newValue".getBytes());
                //在value值偏移5后替换为“rangeValue”，相当于substring+replace方法
                connection.setRange(keyBytes, "rangeValue".getBytes(), 5);
                //取[0,4]区级的字符，类似于substring该方法范围[0,4)
                connection.getRange(keyBytes, 0, 4);

                keyBytes = "crKey".getBytes();
                //todo:error 递增+1，value仅限于有符号整数，按十进制算法，数字在redis中以字符串显示
                connection.incr(keyBytes);
                connection.incrBy(keyBytes, 20l);//加20
                connection.decr(keyBytes);//减1
                connection.decrBy(keyBytes, 20);//减20

                return null;
            }
        });
    }

    @Test
    public void HashCommands() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "hashKey".getBytes();
                byte[] mapKeyBytes = "mapKey".getBytes();
                byte[] mapValueBytes = "mapValue".getBytes();
                long secondTimeout = 100l;//单位：秒
                long milliSecondTimeout = 100l;//单位：秒


                //存取哈希表Map
                connection.hSet(keyBytes, mapKeyBytes, mapValueBytes);//成功返回1，成功并覆盖操作返回0
                connection.hGet(keyBytes, mapKeyBytes);
                connection.hSetNX(keyBytes, mapKeyBytes, mapValueBytes);//先判断再操作
                //是否存在
                connection.hExists(keyBytes, mapKeyBytes);
                //删除Map中key，支持多个操作
                connection.hDel(keyBytes, mapKeyBytes, "mapKey2".getBytes());
                //Map的大小
                connection.hLen(keyBytes);
                //Map中key的值加20
                connection.hIncrBy(keyBytes, mapKeyBytes, 20);

                //批量存取
                Map<byte[], byte[]> mapKeyMap = new HashMap();
                mapKeyMap.put(keyBytes, mapValueBytes);
                mapKeyMap.put("mapKey2".getBytes(), "mapValue2".getBytes());
                connection.hMSet(keyBytes, mapKeyMap);
                connection.hMGet(keyBytes, mapKeyBytes);
                connection.hGetAll(keyBytes);//取整个Map
                //类似于Map的keySet，valueSet方法
                connection.hKeys(keyBytes);
                connection.hVals(keyBytes);
                return null;
            }
        });
    }

    @Test
    public void ListCommands() {
        redisTemplate.execute(new RedisCallback<Array>() {
            @Override
            public Array doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "listKey".getBytes();
                byte[] arrayValueBytes = "ListCommands".getBytes();
                long secondTimeout = 100l;//单位：秒
                long milliSecondTimeout = 100l;//单位：秒

                //支持push多个
                connection.lPush(keyBytes, arrayValueBytes, "ListCommands2".getBytes());
                //在左边push，即表头，等同于lPushX（仅一个参数）
                connection.lPush(keyBytes, arrayValueBytes);
                //在右边push，即表尾，等同于rPushX（仅一个参数）
                connection.rPush(keyBytes, arrayValueBytes, "ListCommands2".getBytes());
                //返回区级值数量的列表，-1表示最后一个
                connection.lRange(keyBytes, 0, 2);//返回3个

                //移除并返回
                connection.lPop(keyBytes);//表头
                connection.rPop(keyBytes);//表尾
                //key1的表尾移除加到key2的表头
                connection.lPush("listKey2".getBytes(), arrayValueBytes);
                connection.rPopLPush(keyBytes, "listKey2".getBytes());
                //移除value相同值的数据，数量为count,
                // count > 0 : 从表头开始向表尾搜索
                // count < 0 : 从表尾开始向表头搜索
                // count = 0 : 移除表中所有与 value 相等的值
                connection.lRem(keyBytes, 2/*count*/, arrayValueBytes);

                long index = 0;
                //获取列表大小
                connection.lLen(keyBytes);
                //返回指定下标的value
                connection.lIndex(keyBytes, index);
                //在value前或后插入某新值
                connection.lInsert(keyBytes, RedisListCommands.Position.BEFORE, arrayValueBytes, "insertValue".getBytes());
                //在指定下标位置存储值
                connection.lSet(keyBytes, index, arrayValueBytes);
                //裁剪列表指定区间的数据
                connection.lTrim(keyBytes, 0, 2);


                int timeout = 10;//秒，0表示无限阻塞直到执行
                //从左/右开始搜索执行存在key的列表，找到就移除返回，否则阻塞至超时,  返回2个元素的列表，一个为key一个为value
                List<byte[]> r1 = connection.bLPop(timeout, keyBytes, "key2".getBytes());
                return null;
            }
        });
    }

    @Test
    public void SetCommands() {
        redisTemplate.execute(new RedisCallback<Collection<Object>>() {
            @Override
            public Collection<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "setKey".getBytes();
                byte[] listValueBytes = "SetCommands".getBytes();

                //添加集合元素，支持多个
                connection.sAdd(keyBytes, listValueBytes, "SetCommands2".getBytes());
                //集合是否包含元素
                connection.sIsMember(keyBytes, listValueBytes);
                //移除随机元素
                connection.sPop(keyBytes);
                //返回随机元素
                connection.sRandMember(keyBytes);
                //返回随机count个元素，不等于0
                long count = 1;//>0，不会重复，小于0会有重复元素
                connection.sRandMember(keyBytes, count);
                //移除指定元素
                connection.sRem(keyBytes, listValueBytes, "SetCommands2".getBytes());
                //移动元素key到key2
                connection.sMove(keyBytes, "setKey2".getBytes(), listValueBytes);
                //统计集合大小
                connection.sCard(keyBytes);
                //返回所有元素
                connection.sMembers(keyBytes);
                //返回交集
                connection.sInter(keyBytes, "setKey2".getBytes(), "setKey3".getBytes());
                //交集存储在新key中
                connection.sInterStore(keyBytes, "setKey2".getBytes(), "newKey".getBytes());
                //并集
                connection.sUnion(keyBytes, "setKey2".getBytes(), "setKey3".getBytes());
                connection.sUnionStore(keyBytes, "setKey2".getBytes(), "newKey".getBytes());
                //差集（属于A集合不属于B集合）
                connection.sDiff(keyBytes, "setKey2".getBytes(), "setKey3".getBytes());
                connection.sDiffStore(keyBytes, "setKey2".getBytes(), "newKey".getBytes());
                return null;
            }
        });
    }

    @Test
    public void ZSetCommands() {
        redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "zSetKey".getBytes();
                byte[] listValueBytes = "ZSetCommands".getBytes();
                double score = 1.2d;
                double increScore = 10d;
                double minScore = 1d;
                double maxScore = 2d;
                long start = 0;//下标
                long stop = 10;//-1表示最后个成员

                //添加数据，一个value包含一个score值，可重写score值
                connection.zAdd(keyBytes, score, listValueBytes);
                //返回指定value的score
                connection.zScore(keyBytes, listValueBytes);
                //增加对应的score值
                connection.zIncrBy(keyBytes, increScore, listValueBytes);
                connection.zCard(keyBytes);//集合大小
                //筛选区间内score值的成员
                connection.zCount(keyBytes, minScore, maxScore);

                //返回区间下标的成员，从小到大排序
                connection.zRange(keyBytes, start, stop);
                connection.zRangeWithScores(keyBytes, start, stop);//包括score
                connection.zRangeByScore(keyBytes, minScore, maxScore);//按score区间[start,stop]返回，
                connection.zRangeByScoreWithScores(keyBytes, minScore, maxScore);
                connection.zRangeByScoreWithScores(keyBytes, RedisZSetCommands.Range.range().lt(maxScore).gt(minScore));//按rang区间返回
                connection.zRangeByLex(keyBytes);
                //按集合元素区间返回，限制先返回哪些
                connection.zRangeByLex(keyBytes, RedisZSetCommands.Range.range(), RedisZSetCommands.Limit.limit());
                //返回区间下标的成员，从大到小排序
                connection.zRevRange(keyBytes, start, stop);
                //.. 类似方法同上

                //返回指定value的排序，
                connection.zRank(keyBytes, listValueBytes);//从小到大，排序最小为0
                connection.zRevRank(keyBytes, listValueBytes);//从大到小，排序最小为0

                //移除并返回指定元素，支持多个
                connection.zRem(keyBytes, listValueBytes);
                connection.zRemRangeByScore(keyBytes, minScore, maxScore);//按score区间移除
                connection.zRemRange(keyBytes, start, stop);//按下标区间移除

                return null;
            }
        });
    }

    @Test
    public void HyperLogLogCommands() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "hyperLogLogKey".getBytes();
                byte[] valueBytes = "HyperLogLogCommands".getBytes();

                /**
                 * HyperLogLog数据结构：是用来做基数统计的算法
                 * 优点：即使输入元素的数量或者体积非常非常大，计算基数所需的空间总是固定的、并且是很小的。
                 * 基数：元素数量（不统计重复的，如[a,b,c,a,b]=3）
                 * 估算值：算法给出的基数并不是精确的，可能会比实际稍微多一些或者稍微少一些，但会控制在合理的范围之内。
                 * 应用：存储网站访问ip（若用字符串存储集合，耗内存）, 来统计网站的ip访问数量
                 */

                //存储，支持多个元素
                connection.pfAdd(keyBytes, valueBytes, "value".getBytes());
                //返回Log的基数估算值
                connection.pfCount(keyBytes);
                //合并多个Log，并集后再算出来基数估算值
                connection.pfMerge(keyBytes, "hyperLogLogKey2".getBytes(), "hyperLogLogKey3".getBytes());
                return null;
            }
        });
    }


    @Test
    public void KeyCommands() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "key".getBytes();
                byte[] valueBytes = "KeyCommands".getBytes();
                int dbIndex = 2;
                byte[] pattern = "pattern简单正则匹配".getBytes();

                connection.set(keyBytes, valueBytes);
                //是否存在
                connection.exists(keyBytes);
                //返回key的数据结构：string、list、set、zset、hash、none（不存在的key）
                connection.type(keyBytes);
                //重名key
                connection.rename(keyBytes, "newKey".getBytes());
//                connection.renameNX(keyBytes, "newKey".getBytes());
                //移动数据库
                connection.move(keyBytes, dbIndex);
                //删除key
                connection.del(keyBytes, "key2".getBytes());
                //随机返回key
                connection.randomKey();
                //数据库可以大小
                connection.dbSize();
                //按规则匹配key
                connection.keys(pattern);
                //增量式迭代，匹配返回key，大数据集合会阻塞redis（单线程）
                connection.scan(ScanOptions.scanOptions().match("*"/*pattern*/).build());
                //迭代集合键中的元素。
                connection.zScan(keyBytes, ScanOptions.scanOptions().match("*"/*pattern*/).build());
                //迭代有序集合中的元素（包括元素成员和元素分值）。
                connection.sScan(keyBytes, ScanOptions.scanOptions().match("*"/*pattern*/).build());
                //迭代哈希键中的键值对。
                connection.hScan(keyBytes, ScanOptions.scanOptions().match("*"/*pattern*/).build());

                //list数据结构排序，默认以为数字排序的列表
                connection.sort(keyBytes, new SortParameters() {
                    @Override
                    public Order getOrder() {
                        return null;
                    }

                    @Override
                    public Boolean isAlphabetic() {
                        //true 按字符排序
                        return null;
                    }

                    @Override
                    public byte[] getByPattern() {
                        return new byte[0];
                    }

                    @Override
                    public byte[][] getGetPattern() {
                        return new byte[0][];
                    }

                    @Override
                    public Range getLimit() {
                        return null;
                    }
                });

                //清除当前数据库key
                connection.flushDb();
                connection.flushAll();//清除redis中所有key
                //切换数据库
                connection.select(dbIndex);
                return null;
            }
        });
    }

    @Test
    public void KeyCommands2() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "key".getBytes();
                Long secondTimeout = 10l;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 10);
                long timeout = calendar.getTimeInMillis();
                connection.set(keyBytes, "KeyCommands2".getBytes());
                //设置过期，单位秒
                connection.expire(keyBytes, secondTimeout);
                connection.expireAt(keyBytes, timeout);//时间戳
                //以秒单位返回
                connection.ttl(keyBytes);
                connection.ttl(keyBytes, TimeUnit.HOURS);//转化单位
                //以毫秒单位返回
                connection.pTtl(keyBytes);
                connection.pTtl(keyBytes, TimeUnit.SECONDS);//转化单位
                return null;
            }
        });
    }

    @Test
    public void TxCommands() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = "txKey".getBytes();
                byte[] valueBytes = "TxCommands".getBytes();

                //标记事务开始
                connection.multi();
                connection.set(keyBytes, valueBytes);
                //事务执行，multi命令后到现在中间的所有命令
                connection.exec();

                //事务监听key，若可以在事务命令中，被其他客户端修改，事务失败
                connection.watch(keyBytes, keyBytes);

                //取消事务监听
                connection.unwatch();
                return null;
            }
        });
    }
}